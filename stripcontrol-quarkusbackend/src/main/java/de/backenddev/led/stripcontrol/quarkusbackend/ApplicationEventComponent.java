package de.backenddev.led.stripcontrol.quarkusbackend;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EventType;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.LEDEventHandler;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.repository.LEDStripRepository;

@Component
public class ApplicationEventComponent
{
	private static final Logger LOG = LoggerFactory.getLogger( ApplicationEventComponent.class );
	@Autowired
	LEDStripRepository repo;

	@Autowired
	LEDEventHandler registry;

	// @Autowired ( required = false )
	// private TelegramBot telegramBot;

	// @EventListener ( ApplicationReadyEvent.class )
	@PostConstruct
	public void initStripsOnStartup( )
	{
		LOG.info( "Startup event" );
		final Iterable<LEDStrip> allStrips = this.repo.findAll( );
		/* initialize all strips on startup */
		for ( final LEDStrip strip : allStrips )
		{
			LOG.info( "Setting up strip " + strip.getName( ) );
			this.registry.handleStripEvent( new StripEvent( this, EventType.SAVE, strip, strip.getId( ) ) );
		}
	}

	@PreDestroy
	public void disableStripsOnShutdown( )
	{
		LOG.info( "Shutdown event" );
		final Iterable<LEDStrip> allStrips = this.repo.findAll( );
		/* shutdown all strips on shutdown */
		for ( final LEDStrip strip : allStrips )
		{
			this.registry.handleStripEvent( new StripEvent( this, EventType.DELETE, null, strip.getId( ) ) );
		}
	}

	@PreDestroy
	public void disableBot( )
	{
		// if ( this.telegramBot != null )
		// {
		// this.telegramBot.removeGetUpdatesListener( );
		// }
	}

}

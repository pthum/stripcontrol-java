package de.backenddev.led.stripcontrol.quarkusbackend;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.backenddev.led.stripcontrol.ledhandling.EventType;
import de.backenddev.led.stripcontrol.model.LEDStrip;
import de.backenddev.led.stripcontrol.quarkusbackend.aqmp.LEDSender;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.repository.LEDStripRepository;

@ApplicationScoped
public class ApplicationEventComponent
{
	private static final Logger LOG = LoggerFactory.getLogger( ApplicationEventComponent.class );
	@Inject
	LEDStripRepository repo;

	@Inject
	LEDSender registry;

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
			LOG.info( "Setting up strip {}", strip.getName( ) );
			this.registry.send( new StripEvent( EventType.SAVE, strip, strip.getId( ) ) );
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
			this.registry.send( new StripEvent( EventType.DELETE, null, strip.getId( ) ) );
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

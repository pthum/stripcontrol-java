package de.backenddev.led.stripcontrol.javastripbackend;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EventType;
import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.StripRegistry;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.repository.LEDStripRepository;

@Component
public class ApplicationEventComponent
{
	private static final Logger LOG = LoggerFactory.getLogger( ApplicationEventComponent.class );
	@Autowired
	private LEDStripRepository repo;

	@Autowired
	private StripRegistry registry;

	@Autowired
	private TelegramBot telegramBot;

	@EventListener ( ApplicationReadyEvent.class )
	public void initStripsOnStartup( )
	{
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
		this.telegramBot.removeGetUpdatesListener( );
	}
}

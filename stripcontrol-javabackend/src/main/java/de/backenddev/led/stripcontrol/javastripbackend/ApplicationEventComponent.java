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
import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.LEDEventHandler;
import de.backenddev.led.stripcontrol.javastripbackend.repository.LEDStripRepository;
import de.backenddev.led.stripcontrol.model.LEDStrip;

@Component
public class ApplicationEventComponent
{
	private static final Logger LOG = LoggerFactory.getLogger( ApplicationEventComponent.class );
	@Autowired
	private LEDStripRepository repo;

	@Autowired
	private LEDEventHandler stripEventHandler;

	@Autowired ( required = false )
	private TelegramBot telegramBot;

	@EventListener ( ApplicationReadyEvent.class )
	public void initStripsOnStartup( )
	{
		final Iterable<LEDStrip> allStrips = this.repo.findAll( );
		/* initialize all strips on startup */
		for ( final LEDStrip strip : allStrips )
		{
			LOG.info( "Setting up strip " + strip.getName( ) );
			this.stripEventHandler.handleStripEvent( new StripEvent( this, EventType.SAVE, strip, strip.getId( ) ) );
		}
	}

	@PreDestroy
	public void disableStripsOnShutdown( )
	{
		final Iterable<LEDStrip> allStrips = this.repo.findAll( );
		/* shutdown all strips on shutdown */
		for ( final LEDStrip strip : allStrips )
		{
			this.stripEventHandler.handleStripEvent( new StripEvent( this, EventType.DELETE, null, strip.getId( ) ) );
		}
	}

	@PreDestroy
	public void disableBot( )
	{
		if ( this.telegramBot != null )
		{
			this.telegramBot.removeGetUpdatesListener( );
		}
	}
}

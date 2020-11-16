package de.backenddev.led.stripcontrol.springbackend;

import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;

import de.backenddev.led.stripcontrol.ledhandling.EventType;
import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;
import de.backenddev.led.stripcontrol.springbackend.ledhandling.LEDEventHandler;
import de.backenddev.led.stripcontrol.springbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.springbackend.repository.ColorProfileRepository;
import de.backenddev.led.stripcontrol.springbackend.repository.LEDStripRepository;

@Component
public class ApplicationEventComponent
{
	private static final Logger LOG = LoggerFactory.getLogger( ApplicationEventComponent.class );
	@Autowired
	private LEDStripRepository ledRepo;

	@Autowired
	private ColorProfileRepository profileRepo;

	@Autowired
	private LEDEventHandler stripEventHandler;

	@Autowired
	private StripConfig config;

	@Autowired ( required = false )
	private TelegramBot telegramBot;

	@EventListener ( ApplicationReadyEvent.class )
	public void initStripsOnStartup( )
	{
		/* store predefined profiles and strips to db */
		initColorProfiles( );
		initLEDStrips( );

		final Iterable<LEDStrip> allStrips = this.ledRepo.findAll( );
		/* initialize all strips on startup */
		for ( final LEDStrip strip : allStrips )
		{
			LOG.info( "Setting up strip " + strip.getName( ) );
			this.stripEventHandler.handleStripEvent( new StripEvent( this, EventType.SAVE, strip, strip.getId( ) ) );
		}
	}

	private void initColorProfiles( )
	{
		final List<ColorProfile> profiles = this.config.getPredefinedProfiles( );
		if ( profiles == null )
		{
			return;
		}
		for ( final ColorProfile profile : profiles )
		{
			if ( this.profileRepo.findByRedAndGreenAndBlueAndBrightness( profile.getRed( ), profile.getGreen( ),
					profile.getBlue( ), profile.getBrightness( ) ).isEmpty( ) )
			{
				this.profileRepo.save( profile );
				LOG.info( "Created profile {}", profile );
			}
		}
	}

	private void initLEDStrips( )
	{
		final List<LEDStrip> strips = this.config.getPredefinedStrips( );
		if ( strips == null )
		{
			return;
		}
		for ( final LEDStrip strip : strips )
		{
			final List<LEDStrip> dbStrips = this.ledRepo.findByMisoPinAndSclkPin( strip.getMisoPin( ),
					strip.getSclkPin( ) );
			if ( dbStrips.isEmpty( ) )
			{
				this.ledRepo.save( strip );
				LOG.info( "Created strip {}", strip );
			}
		}
	}

	@PreDestroy
	public void disableStripsOnShutdown( )
	{
		final Iterable<LEDStrip> allStrips = this.ledRepo.findAll( );
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

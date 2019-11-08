package de.backenddev.led.stripcontrol.javastripbackend;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.APA102Helper;
import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.Apa102Factory;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.repository.LEDStripRepository;

@Component
public class ApplicationEventComponent
{
	@Autowired
	private LEDStripRepository repo;

	@Value ( "${strips.enabled}" )
	private boolean stripsEnabled;

	@EventListener ( ApplicationReadyEvent.class )
	public void doSomethingAfterStartup( )
	{
		List<LEDStrip> enabledStrips = repo.findByEnabled( true );
		for ( LEDStrip strip : enabledStrips )
		{
			try
			{
				System.out.println( "Setting up strip " + strip.getName( ) );
				APA102Control control = Apa102Factory.createControl( strip, !stripsEnabled );
				ColorProfile profile = strip.getProfile( );
				if ( profile != null )
				{
					APA102Helper.setStripColor( control, profile.getRed( ), profile.getGreen( ), profile.getBlue( ),
							profile.getBrightness( ) );
				}
			} catch ( IOException e )
			{
				// TODO
				e.printStackTrace( );
			}
		}
	}
}

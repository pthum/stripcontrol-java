package de.backenddev.led.stripcontrol.ledhandling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.APA102Strip;
import de.backenddev.led.apa102.ColorConfig;
import de.backenddev.led.apa102.NoOpAPA102Control;
import de.backenddev.led.stripcontrol.model.LEDStrip;

public class Apa102Factory
{
	private final static Logger LOGGER = LoggerFactory.getLogger( Apa102Factory.class );

	public static APA102Control createControl( final LEDStrip strip, final boolean dummyImpl ) throws IOException
	{
		APA102Control apaStrip = new NoOpAPA102Control( );
		final int misoPin = strip.getMisoPin( );
		final int sclkPin = strip.getSclkPin( );
		final int speed = strip.getSpeedHz( );
		try
		{
			if ( dummyImpl == false )
			{
				apaStrip = new APA102Control( misoPin, sclkPin, speed );
			}
		}
		catch ( final IOException e )
		{
			LOGGER.error( "Could not initialize APA102Control ", e );
		}
		catch ( final UnsatisfiedLinkError ule )
		{
			/*
			 * occurs, when wiringpi is not available (f.i. on a dev system which is not a
			 * raspi) at the moment, just log this
			 */
			LOGGER.error(
					"Could not initialize APA102Control. Seems that this is running somewhere, where the GPIO Pins are not available. If this is intended and you're running on a dev system, you should set strips.enabled to false in your application.yml ",
					ule );
		}
		return apaStrip;
	}

	public static APA102Strip createStrip( final LEDStrip strip )
	{
		final int numLEDs = strip.getNumLeds( );
		/*
		 * always set global brightness to max value, as we change the brightness for
		 * each pixel individually (as percent)
		 */
		final int globalBrightness = 100;
		return new APA102Strip( numLEDs, globalBrightness, ColorConfig.RGB );
	}
}

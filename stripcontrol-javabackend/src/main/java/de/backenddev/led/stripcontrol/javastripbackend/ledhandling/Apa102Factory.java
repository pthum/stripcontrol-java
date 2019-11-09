package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import java.io.IOException;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.ColorConfig;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

public class Apa102Factory
{
	public static APA102Control createControl( final LEDStrip strip, final boolean dummyImpl ) throws IOException
	{
		APA102Control apaStrip = null;
		final ColorProfile profile = strip.getProfile( );
		final int numLEDs = strip.getNumLeds( );
		final int brightness = profile != null ? profile.getBrightness( ) : 100;
		final int misoPin = strip.getMisoPin( );
		final int sclkPin = strip.getSclkPin( );
		final int speed = strip.getSpeedHz( );
		try
		{
			apaStrip = dummyImpl ? new NoOpApa102Control( numLEDs )
					: new APA102Control( numLEDs, brightness, ColorConfig.RGB, misoPin, sclkPin, speed );
		}
		catch ( final IOException e )
		{
			// TODO
			e.printStackTrace( );
		}
		catch ( final UnsatisfiedLinkError ule )
		{
			/*
			 * occurs, when wiringpi is not available (f.i. on a dev system which is not a
			 * raspi) at the moment, just log this -> TODO
			 */
			ule.printStackTrace( );
		}
		return apaStrip;
	}
}

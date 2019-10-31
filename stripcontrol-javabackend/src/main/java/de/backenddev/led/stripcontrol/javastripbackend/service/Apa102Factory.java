package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.io.IOException;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.ColorConfig;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

public class Apa102Factory
{
	public static APA102Control createControl(LEDStrip strip, boolean dummyImpl ) throws IOException
	{
		APA102Control apaStrip = null;
		ColorProfile profile = strip.getProfile( );
		int numLEDs = strip.getNumLeds( );
		int brightness = profile != null ? profile.getBrightness( ) : 100;
		int misoPin = strip.getMisoPin( );
		int sclkPin = strip.getSclkPin( );
		int speed = strip.getSpeedHz( );
		try
		{
			apaStrip = dummyImpl
					? new NoOpApa102Control( numLEDs, brightness, ColorConfig.RGB, misoPin, sclkPin, speed )
					: new APA102Control( numLEDs, brightness, ColorConfig.RGB, misoPin, sclkPin, speed );
		} catch ( IOException e )
		{
			// TODO
			e.printStackTrace( );
		} catch ( UnsatisfiedLinkError ule )
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

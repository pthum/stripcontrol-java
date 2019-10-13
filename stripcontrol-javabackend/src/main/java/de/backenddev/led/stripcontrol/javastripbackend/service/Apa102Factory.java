package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.io.IOException;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.ColorConfig;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

public class Apa102Factory
{
	public static APA102Control createControl(LEDStrip strip ) throws IOException
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
			apaStrip = new APA102Control( numLEDs, brightness, ColorConfig.RGB, misoPin, sclkPin, speed );
		} catch ( IOException e )
		{
			// TODO
			e.printStackTrace( );
		}
		return apaStrip;
	}
}

package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.io.IOException;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.ColorConfig;

public class NoOpApa102Control extends APA102Control
{

	public NoOpApa102Control( int numLed, int globalBrightness, ColorConfig cfg, int mosi, int sclk, int maxSpeedHz )
			throws IOException
	{
		super( numLed, cfg, 100, new byte[100], 100, null );
	}

	@Override
	public void setPixel(int ledNum, int red, int green, int blue, double brightnessPercent )
	{
	}

	@Override
	public void show( ) throws IOException
	{
	}

	@Override
	public void clearStrip( ) throws IOException
	{
	}
}

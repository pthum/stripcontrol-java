package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import java.io.IOException;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.ColorConfig;

public class NoOpApa102Control extends APA102Control
{
	int red = 0;
	int green = 0;
	int blue = 0;
	double brightnessPercent = 0;

	public NoOpApa102Control( final int numLed ) throws IOException
	{
		super( numLed, ColorConfig.RGB, 100, new byte[100], 100, null );
	}

	@Override
	public void setPixel( final int ledNum, final int red, final int green, final int blue,
			final double brightnessPercent )
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.brightnessPercent = brightnessPercent;
	}

	@Override
	public void show( ) throws IOException
	{
	}

	@Override
	public void clearStrip( ) throws IOException
	{
		this.red = 0;
		this.green = 0;
		this.blue = 0;
		this.brightnessPercent = 0;
	}

	/**
	 * @return the red
	 */
	public int getRed( )
	{
		return this.red;
	}

	/**
	 * @return the green
	 */
	public int getGreen( )
	{
		return this.green;
	}

	/**
	 * @return the blue
	 */
	public int getBlue( )
	{
		return this.blue;
	}

	/**
	 * @return the brightnessPercent
	 */
	public double getBrightnessPercent( )
	{
		return this.brightnessPercent;
	}

}

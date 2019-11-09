package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.APA102Helper;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

public class Apa102Meta
{
	private static final Logger LOG = LoggerFactory.getLogger( Apa102Meta.class );
	boolean enabled;
	int r;
	int g;
	int b;
	int brightness;
	Long profileId;
	APA102Control control;

	public Apa102Meta( final LEDStrip strip, final boolean useNoOp ) throws IOException
	{
		this.control = Apa102Factory.createControl( strip, useNoOp );
		update( strip );
	}

	public boolean isEnabled( )
	{
		return this.enabled;
	}

	public boolean hasNoColor( )
	{
		return this.r == 0 && this.g == 0 && this.b == 0;
	}

	public boolean hasNoBrightness( )
	{
		return this.brightness == 0;
	}

	public boolean isInClearedState( )
	{
		return isEnabled( ) == false || hasNoColor( ) || hasNoBrightness( );
	}

	/**
	 * @return the r
	 */
	public int getR( )
	{
		return this.r;
	}

	/**
	 * @return the g
	 */
	public int getG( )
	{
		return this.g;
	}

	/**
	 * @return the b
	 */
	public int getB( )
	{
		return this.b;
	}

	/**
	 * @return the brightness
	 */
	public int getBrightness( )
	{
		return this.brightness;
	}

	/**
	 * @return the profileId
	 */
	public Long getProfileId( )
	{
		return this.profileId;
	}

	public void update( final LEDStrip strip ) throws IOException
	{
		if ( isStateChange( strip ) )
		{
			changeStripState( strip.getProfile( ) );
		}
		setValues( strip );
	}

	public void update( final ColorProfile profile ) throws IOException
	{
		if ( isColorChange( profile ) || isBrightnessChange( profile ) )
		{
			changeStripState( profile );
		}
		setValues( profile );
	}

	public void shutdown( ) throws IOException
	{
		if ( isInClearedState( ) == false )
		{
			this.control.clearStrip( );
		}
		this.r = 0;
		this.g = 0;
		this.b = 0;
		this.brightness = 0;
		this.enabled = false;
	}

	private void changeStripState( final ColorProfile profile ) throws IOException
	{
		int pr = 0;
		int pg = 0;
		int pb = 0;
		int pbrght = 0;
		if ( profile != null )
		{
			pr = profile.getRed( );
			pg = profile.getGreen( );
			pb = profile.getBlue( );
			pbrght = profile.getBrightness( );
		}
		APA102Helper.setStripColor( this.control, pr, pg, pb, pbrght );
		this.control.show( );
	}

	private boolean isStateChange( final LEDStrip strip )
	{
		final boolean result = isEnableStateChange( strip ) || isColorChange( strip.getProfile( ) )
				|| isBrightnessChange( strip.getProfile( ) );
		LOG.debug( "Is state change? " + result + " for " + this.toString( ) + " and strip: " + strip );
		return result;
	}

	private boolean isEnableStateChange( final LEDStrip strip )
	{
		return this.enabled != strip.isEnabled( );
	}

	private boolean isColorChange( final ColorProfile profile )
	{
		if ( profile == null )
		{
			/*
			 * if no profile and this has no color -> no change
			 * if no profile and this has a color -> change
			 */
			return this.hasNoColor( );
		}
		return this.r != profile.getRed( ) || this.g != profile.getGreen( ) || this.b != profile.getBlue( );
	}

	private boolean isBrightnessChange( final ColorProfile profile )
	{
		if ( profile == null )
		{
			/*
			 * if no profile and this has no brightness -> no change
			 * if no profile and this has a brightness -> change
			 */
			return this.hasNoBrightness( );
		}
		return profile.getBrightness( ) != this.brightness;
	}

	private void setValues( final LEDStrip strip )
	{
		this.enabled = strip.isEnabled( );
		setValues( strip.getProfile( ) );
	}

	private void setValues( final ColorProfile profile )
	{
		this.r = profile != null ? profile.getRed( ) : 0;
		this.g = profile != null ? profile.getGreen( ) : 0;
		this.b = profile != null ? profile.getBlue( ) : 0;
		this.brightness = profile != null ? profile.getBrightness( ) : 0;
		this.profileId = profile != null ? profile.getId( ) : null;
	}

	@Override
	public String toString( )
	{
		return "Apa102Meta [enabled=" + this.enabled + ", r=" + this.r + ", g=" + this.g + ", b=" + this.b
				+ ", brightness=" + this.brightness + ", profileId=" + this.profileId + "]";
	}

}
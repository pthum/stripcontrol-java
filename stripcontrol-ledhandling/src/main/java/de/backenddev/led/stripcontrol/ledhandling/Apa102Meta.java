package de.backenddev.led.stripcontrol.ledhandling;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.APA102Strip;
import de.backenddev.led.apa102.LEDEffects;
import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;

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
	APA102Strip strip;
	private final int effectTime;

	public Apa102Meta( final LEDStrip strip, final boolean useNoOp, final int effectTime ) throws IOException
	{
		this.control = Apa102Factory.createControl( strip, useNoOp );
		this.strip = Apa102Factory.createStrip( strip );
		this.effectTime = effectTime;
		setValues( strip.isEnabled( ), strip.getProfile( ) );
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

	public void update( final Boolean isStripEnabled, final ColorProfile profile ) throws IOException
	{
		if ( ( isEnableStateChange( isStripEnabled ) || isEnabled( ) )
				|| ( isEnabled( ) && ( isColorChange( profile ) || isBrightnessChange( profile ) ) ) )
		{
			changeStripState( isStripEnabled == null ? profile : isStripEnabled ? profile : null );
		}
		setValues( isStripEnabled, profile );
	}

	public void shutdown( ) throws IOException
	{
		if ( isInClearedState( ) == false )
		{
			this.control.clearStrip( this.strip );
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
		final EffectType type = EffectType.CHASE;
		if ( profile != null )
		{
			pr = profile.getRed( );
			pg = profile.getGreen( );
			pb = profile.getBlue( );
			pbrght = profile.getBrightness( );
		}
		doEffect( pr, pg, pb, pbrght, type, this.effectTime );
	}

	private void doEffect( final int r, final int g, final int b, final int brightness, final EffectType effect,
			final int stepPauseMs ) throws IOException
	{
		LOG.trace( "Doing effect {} with steps of {} ms", effect, stepPauseMs );
		final APA102Strip newStrip = new APA102Strip( this.strip.getNumLed( ), this.strip.getGlobalBrightness( ),
				this.strip.getColorConfig( ) );
		newStrip.applyToAllPixels( ( i, p ) -> p.setPixel( r, g, b, brightness ) );
		try
		{
			switch ( effect )
			{
				case CHASE:
					LEDEffects.chaselight( this.control, this.strip, newStrip, stepPauseMs );
					break;

				case FADE:
					LEDEffects.fadeBrightness( this.control, newStrip, stepPauseMs, this.brightness, brightness, 2.0d );
					break;
				case LIGHT_UP:
					LEDEffects.lightUp( this.control, newStrip, stepPauseMs );
					break;
				default:
					this.strip.applyToAllPixels( ( i, p ) -> p.setPixel( r, g, b, brightness ) );
					this.control.show( this.strip );
					break;
			}
		}
		catch ( final InterruptedException e )
		{
			LOG.error( "Interrupted effect", e );
		}

	}

	private boolean isEnableStateChange( final Boolean isStripEnabled )
	{
		return isStripEnabled == null ? false : this.enabled != isStripEnabled;
	}

	private boolean isColorChange( final ColorProfile profile )
	{
		if ( profile == null )
		{
			/*
			 * if no profile and this has no color -> no change
			 * if no profile and this has a color -> change
			 */
			return this.hasNoColor( ) == false;
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
			return this.hasNoBrightness( ) == false;
		}
		return profile.getBrightness( ) != this.brightness;
	}

	private void setValues( final Boolean isStripEnabled, final ColorProfile profile )
	{
		if ( isStripEnabled != null )
		{
			this.enabled = isStripEnabled;
		}
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

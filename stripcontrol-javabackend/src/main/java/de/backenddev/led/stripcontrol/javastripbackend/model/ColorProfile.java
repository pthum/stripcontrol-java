package de.backenddev.led.stripcontrol.javastripbackend.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The color profile
 * 
 * @author thum
 */
@Entity
public class ColorProfile extends AbstractModel
{
	@Min ( 0 )
	@Max ( 255 )
	private int red;
	@Min ( 0 )
	@Max ( 255 )
	private int green;
	@Min ( 0 )
	@Max ( 255 )
	private int blue;
	@Min ( 0 )
	@Max ( 100 )
	private int brightness;

	@ManyToOne
	@JoinColumn ( name = "oneffect_id" )
	@JsonIgnore
	private EffectConfiguration onEffect;

	@ManyToOne
	@JoinColumn ( name = "offeffect_id" )
	@JsonIgnore
	private EffectConfiguration offEffect;

	/**
	 * Default constructor
	 */
	public ColorProfile( )
	{
		super( );
	}

	/**
	 * Constructor using fields
	 * 
	 * @param red
	 *            red value, between 0 and 255
	 * @param green
	 *            green value, between 0 and 255
	 * @param blue
	 *            blue value, between 0 and 255
	 * @param brightness
	 *            brightness, between 0 and 100
	 */
	public ColorProfile( final int red, final int green, final int blue, final int brightness )
	{
		super( );
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.brightness = brightness;
	}

	/**
	 * @return the red
	 */
	public int getRed( )
	{
		return this.red;
	}

	/**
	 * @param red
	 *            the red to set
	 */
	public void setRed( final int red )
	{
		this.red = red;
	}

	/**
	 * @return the green
	 */
	public int getGreen( )
	{
		return this.green;
	}

	/**
	 * @param green
	 *            the green to set
	 */
	public void setGreen( final int green )
	{
		this.green = green;
	}

	/**
	 * @return the blue
	 */
	public int getBlue( )
	{
		return this.blue;
	}

	/**
	 * @param blue
	 *            the blue to set
	 */
	public void setBlue( final int blue )
	{
		this.blue = blue;
	}

	/**
	 * @return the brightness
	 */
	public int getBrightness( )
	{
		return this.brightness;
	}

	/**
	 * @param brightness
	 *            the brightness to set
	 */
	public void setBrightness( final int brightness )
	{
		this.brightness = brightness;
	}

	/**
	 * @return the onEffect
	 */
	public EffectConfiguration getOnEffect( )
	{
		return this.onEffect;
	}

	/**
	 * @param onEffect
	 *            the onEffect to set
	 */
	public void setOnEffect( final EffectConfiguration onEffect )
	{
		this.onEffect = onEffect;
	}

	@JsonProperty
	public boolean hasOnEffect( )
	{
		return this.onEffect != null;
	}

	@JsonIgnore
	public void setHasOnEffect( final boolean unused )
	{
		// empty setter to prevent jackson errors
	}

	@JsonProperty
	public Long onEffectId( )
	{
		return this.onEffect == null ? null : this.onEffect.getId( );
	}

	/**
	 * @return the offEffect
	 */
	public EffectConfiguration getOffEffect( )
	{
		return this.offEffect;
	}

	/**
	 * @param onEffect
	 *            the offEffect to set
	 */
	public void setOffEffect( final EffectConfiguration offEffect )
	{
		this.offEffect = offEffect;
	}

	@JsonProperty
	public boolean hasOffEffect( )
	{
		return this.offEffect != null;
	}

	@JsonIgnore
	public void setHasOffEffect( final boolean unused )
	{
		// empty setter to prevent jackson errors
	}

	@JsonProperty
	public Long offEffectId( )
	{
		return this.offEffect == null ? null : this.offEffect.getId( );
	}

	@Override
	public String toString( )
	{
		return "ColorProfile [id=" + this.getId( ) + ", red=" + this.red + ", green=" + this.green + ", blue="
				+ this.blue + ", brightness=" + this.brightness + "]";
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = super.hashCode( );
		result = prime * result
				+ Objects.hash( this.blue, this.brightness, this.green, this.offEffect, this.onEffect, this.red );
		return result;
	}

	@Override
	public boolean equals( final Object obj )
	{
		if ( this == obj )
			return true;
		if ( !super.equals( obj ) )
			return false;
		if ( getClass( ) != obj.getClass( ) )
			return false;
		final ColorProfile other = (ColorProfile) obj;
		return this.blue == other.blue && this.brightness == other.brightness && this.green == other.green
				&& Objects.equals( this.offEffect, other.offEffect ) && Objects.equals( this.onEffect, other.onEffect )
				&& this.red == other.red;
	}

}

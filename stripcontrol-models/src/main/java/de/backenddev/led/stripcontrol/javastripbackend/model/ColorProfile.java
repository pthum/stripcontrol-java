package de.backenddev.led.stripcontrol.javastripbackend.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
		result = prime * result + Objects.hash( this.blue, this.brightness, this.green, this.red );
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
				&& this.red == other.red;
	}

}

package de.backenddev.led.stripcontrol.javastripbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * The color profile
 * 
 * @author thum
 */
@Entity
public class ColorProfile
{
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Long id;

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
	 * @return the id
	 */
	public Long getId( )
	{
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId( final Long id )
	{
		this.id = id;
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
		return "ColorProfile [id=" + this.id + ", red=" + this.red + ", green=" + this.green + ", blue=" + this.blue
				+ ", brightness=" + this.brightness + "]";
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.blue;
		result = prime * result + this.brightness;
		result = prime * result + this.green;
		result = prime * result + (int) ( this.id ^ ( this.id >>> 32 ) );
		result = prime * result + this.red;
		return result;
	}

	@Override
	public boolean equals( final Object obj )
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass( ) != obj.getClass( ) )
			return false;
		final ColorProfile other = (ColorProfile) obj;
		if ( this.blue != other.blue )
			return false;
		if ( this.brightness != other.brightness )
			return false;
		if ( this.green != other.green )
			return false;
		if ( this.id != other.id )
			return false;
		if ( this.red != other.red )
			return false;
		return true;
	}

}

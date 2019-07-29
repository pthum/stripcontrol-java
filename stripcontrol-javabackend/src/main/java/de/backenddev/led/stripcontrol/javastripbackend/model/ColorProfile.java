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
	private long id;

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
	public ColorProfile( int red, int green, int blue, int brightness )
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
	public long getId( )
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id )
	{
		this.id = id;
	}

	/**
	 * @return the red
	 */
	public int getRed( )
	{
		return red;
	}

	/**
	 * @param red
	 *            the red to set
	 */
	public void setRed(int red )
	{
		this.red = red;
	}

	/**
	 * @return the green
	 */
	public int getGreen( )
	{
		return green;
	}

	/**
	 * @param green
	 *            the green to set
	 */
	public void setGreen(int green )
	{
		this.green = green;
	}

	/**
	 * @return the blue
	 */
	public int getBlue( )
	{
		return blue;
	}

	/**
	 * @param blue
	 *            the blue to set
	 */
	public void setBlue(int blue )
	{
		this.blue = blue;
	}

	/**
	 * @return the brightness
	 */
	public int getBrightness( )
	{
		return brightness;
	}

	/**
	 * @param brightness
	 *            the brightness to set
	 */
	public void setBrightness(int brightness )
	{
		this.brightness = brightness;
	}

	@Override
	public String toString( )
	{
		return "ColorProfile [id=" + id + ", red=" + red + ", green=" + green + ", blue=" + blue + ", brightness="
				+ brightness + "]";
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + blue;
		result = prime * result + brightness;
		result = prime * result + green;
		result = prime * result + (int) ( id ^ ( id >>> 32 ) );
		result = prime * result + red;
		return result;
	}

	@Override
	public boolean equals(Object obj )
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass( ) != obj.getClass( ) )
			return false;
		ColorProfile other = (ColorProfile) obj;
		if ( blue != other.blue )
			return false;
		if ( brightness != other.brightness )
			return false;
		if ( green != other.green )
			return false;
		if ( id != other.id )
			return false;
		if ( red != other.red )
			return false;
		return true;
	}

}

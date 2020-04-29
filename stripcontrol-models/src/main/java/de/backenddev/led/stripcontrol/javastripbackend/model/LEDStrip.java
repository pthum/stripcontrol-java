package de.backenddev.led.stripcontrol.javastripbackend.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An LED Strip
 * 
 * @author thum
 *
 */
@Entity
public class LEDStrip extends AbstractModel
{
	private String name;

	private String description;

	private int misoPin;

	private int sclkPin;

	private int numLeds;

	private int speedHz;

	private boolean enabled;

	@ManyToOne
	@JoinColumn ( name = "profile_id" )
	@JsonIgnore
	private ColorProfile profile;

	/**
	 * Default constructor
	 */
	public LEDStrip( )
	{
		super( );
	}

	/**
	 * @param name
	 * @param description
	 * @param misoPin
	 * @param sclkPin
	 * @param numLeds
	 * @param speedHz
	 */
	public LEDStrip( final String name, final String description, final int misoPin, final int sclkPin,
			final int numLeds, final int speedHz )
	{
		super( );
		this.name = name;
		this.description = description;
		this.misoPin = misoPin;
		this.sclkPin = sclkPin;
		this.numLeds = numLeds;
		this.speedHz = speedHz;
	}

	/**
	 * @return the name
	 */
	public String getName( )
	{
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName( final String name )
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription( )
	{
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription( final String description )
	{
		this.description = description;
	}

	/**
	 * @return the misoPin
	 */
	public int getMisoPin( )
	{
		return this.misoPin;
	}

	/**
	 * @param misoPin
	 *            the misoPin to set
	 */
	public void setMisoPin( final int misoPin )
	{
		this.misoPin = misoPin;
	}

	/**
	 * @return the sclkPin
	 */
	public int getSclkPin( )
	{
		return this.sclkPin;
	}

	/**
	 * @param sclkPin
	 *            the sclkPin to set
	 */
	public void setSclkPin( final int sclkPin )
	{
		this.sclkPin = sclkPin;
	}

	/**
	 * @return the numLeds
	 */
	public int getNumLeds( )
	{
		return this.numLeds;
	}

	/**
	 * @param numLeds
	 *            the numLeds to set
	 */
	public void setNumLeds( final int numLeds )
	{
		this.numLeds = numLeds;
	}

	/**
	 * @return the speedHz
	 */
	public int getSpeedHz( )
	{
		return this.speedHz;
	}

	/**
	 * @param speedHz
	 *            the speedHz to set
	 */
	public void setSpeedHz( final int speedHz )
	{
		this.speedHz = speedHz;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled( )
	{
		return this.enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled( final boolean enabled )
	{
		this.enabled = enabled;
	}

	/**
	 * @return the profile
	 */
	@JsonIgnore
	public ColorProfile getProfile( )
	{
		return this.profile;
	}

	/**
	 * @param profile
	 *            the profile to set
	 */
	@JsonIgnore
	public void setProfile( final ColorProfile profile )
	{
		this.profile = profile;
	}

	@JsonProperty
	public boolean hasProfile( )
	{
		return this.profile != null;
	}

	@JsonIgnore
	public void setHasProfile( final boolean unused )
	{
		// empty setter to prevent jackson errors
	}

	@JsonProperty
	public Long profileId( )
	{
		return this.profile == null ? null : this.profile.getId( );
	}

	@JsonIgnore
	public void setProfileId( final Long unused )
	{
		// empty setter to prevent jackson errors
	}

	@Override
	public String toString( )
	{
		return "LEDStrip [id=" + getId( ) + ", name=" + this.name + ", description=" + this.description + ", misoPin="
				+ this.misoPin + ", sclkPin=" + this.sclkPin + ", numLeds=" + this.numLeds + ", speedHz=" + this.speedHz
				+ "]";
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = super.hashCode( );
		result = prime * result + Objects.hash( this.description, this.enabled, this.misoPin, this.name, this.numLeds,
				this.profile, this.sclkPin, this.speedHz );
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
		final LEDStrip other = (LEDStrip) obj;
		return Objects.equals( this.description, other.description ) && this.enabled == other.enabled
				&& this.misoPin == other.misoPin && Objects.equals( this.name, other.name )
				&& this.numLeds == other.numLeds && Objects.equals( this.profile, other.profile )
				&& this.sclkPin == other.sclkPin && this.speedHz == other.speedHz;
	}

}

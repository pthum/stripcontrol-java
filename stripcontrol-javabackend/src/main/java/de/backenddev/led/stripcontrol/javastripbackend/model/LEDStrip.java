package de.backenddev.led.stripcontrol.javastripbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class LEDStrip
{
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Long id;

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
	public LEDStrip( String name, String description, int misoPin, int sclkPin, int numLeds, int speedHz )
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
	 * @return the id
	 */
	public Long getId( )
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id )
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName( )
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name )
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription( )
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description )
	{
		this.description = description;
	}

	/**
	 * @return the misoPin
	 */
	public int getMisoPin( )
	{
		return misoPin;
	}

	/**
	 * @param misoPin
	 *            the misoPin to set
	 */
	public void setMisoPin(int misoPin )
	{
		this.misoPin = misoPin;
	}

	/**
	 * @return the sclkPin
	 */
	public int getSclkPin( )
	{
		return sclkPin;
	}

	/**
	 * @param sclkPin
	 *            the sclkPin to set
	 */
	public void setSclkPin(int sclkPin )
	{
		this.sclkPin = sclkPin;
	}

	/**
	 * @return the numLeds
	 */
	public int getNumLeds( )
	{
		return numLeds;
	}

	/**
	 * @param numLeds
	 *            the numLeds to set
	 */
	public void setNumLeds(int numLeds )
	{
		this.numLeds = numLeds;
	}

	/**
	 * @return the speedHz
	 */
	public int getSpeedHz( )
	{
		return speedHz;
	}

	/**
	 * @param speedHz
	 *            the speedHz to set
	 */
	public void setSpeedHz(int speedHz )
	{
		this.speedHz = speedHz;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled( )
	{
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled )
	{
		this.enabled = enabled;
	}

	/**
	 * @return the profile
	 */
	@JsonIgnore
	public ColorProfile getProfile( )
	{
		return profile;
	}

	/**
	 * @param profile
	 *            the profile to set
	 */
	@JsonIgnore
	public void setProfile(ColorProfile profile )
	{
		this.profile = profile;
	}

	@JsonProperty
	public boolean hasProfile( )
	{
		return this.profile != null;
	}

	@JsonIgnore
	public void setHasProfile(boolean unused )
	{
		// empty setter to prevent jackson errors
	}

	@JsonProperty
	public Long profileId( )
	{
		return this.profile == null ? null : this.profile.getId( );
	}

	@JsonIgnore
	public void setProfileId(Long unused )
	{
		// empty setter to prevent jackson errors
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( description == null ) ? 0 : description.hashCode( ) );
		result = prime * result + (int) ( id ^ ( id >>> 32 ) );
		result = prime * result + misoPin;
		result = prime * result + ( ( name == null ) ? 0 : name.hashCode( ) );
		result = prime * result + numLeds;
		result = prime * result + sclkPin;
		result = prime * result + speedHz;
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
		LEDStrip other = (LEDStrip) obj;
		if ( description == null )
		{
			if ( other.description != null )
				return false;
		} else if ( !description.equals( other.description ) )
			return false;
		if ( id != other.id )
			return false;
		if ( misoPin != other.misoPin )
			return false;
		if ( name == null )
		{
			if ( other.name != null )
				return false;
		} else if ( !name.equals( other.name ) )
			return false;
		if ( numLeds != other.numLeds )
			return false;
		if ( sclkPin != other.sclkPin )
			return false;
		if ( speedHz != other.speedHz )
			return false;
		return true;
	}

	@Override
	public String toString( )
	{
		return "LEDStrip [id=" + id + ", name=" + name + ", description=" + description + ", misoPin=" + misoPin
				+ ", sclkPin=" + sclkPin + ", numLeds=" + numLeds + ", speedHz=" + speedHz + "]";
	}

}

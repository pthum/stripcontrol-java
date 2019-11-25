package de.backenddev.led.stripcontrol.javastripbackend.model;

import java.util.Objects;

import javax.persistence.Entity;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EffectType;

/**
 * The effect configuration
 * 
 * @author thum
 */
@Entity
public class EffectConfiguration extends AbstractModel
{
	private EffectType type;

	private int stepSpeedMs;

	private String additionalMeta;

	/**
	 * Default constructor
	 */
	public EffectConfiguration( )
	{
		super( );
	}

	/**
	 * @param type
	 * @param stepSpeedMs
	 * @param additionalMeta
	 */
	protected EffectConfiguration( final EffectType type, final int stepSpeedMs, final String additionalMeta )
	{
		super( );
		this.type = type;
		this.stepSpeedMs = stepSpeedMs;
		this.additionalMeta = additionalMeta;
	}

	/**
	 * @return the type
	 */
	public EffectType getType( )
	{
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType( final EffectType type )
	{
		this.type = type;
	}

	/**
	 * @return the stepSpeedMs
	 */
	public int getStepSpeedMs( )
	{
		return this.stepSpeedMs;
	}

	/**
	 * @param stepSpeedMs
	 *            the stepSpeedMs to set
	 */
	public void setStepSpeedMs( final int stepSpeedMs )
	{
		this.stepSpeedMs = stepSpeedMs;
	}

	/**
	 * @return the additionalMeta
	 */
	public String getAdditionalMeta( )
	{
		return this.additionalMeta;
	}

	/**
	 * @param additionalMeta
	 *            the additionalMeta to set
	 */
	public void setAdditionalMeta( final String additionalMeta )
	{
		this.additionalMeta = additionalMeta;
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = super.hashCode( );
		result = prime * result + Objects.hash( this.additionalMeta, this.stepSpeedMs, this.type );
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
		final EffectConfiguration other = (EffectConfiguration) obj;
		return Objects.equals( this.additionalMeta, other.additionalMeta ) && this.stepSpeedMs == other.stepSpeedMs
				&& this.type == other.type;
	}

}

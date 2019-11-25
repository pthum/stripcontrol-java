package de.backenddev.led.stripcontrol.javastripbackend.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EffectType;

/**
 * The effect configuration
 * 
 * @author thum
 */
@Entity
public class EffectConfiguration
{
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Long id;

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
	protected EffectConfiguration( final EffectType type, final int stepSpeedMs,final String additionalMeta )
	{
		super( );
		this.type = type;
		this.stepSpeedMs = stepSpeedMs;
		this.additionalMeta = additionalMeta;
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

}

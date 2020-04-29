package de.backenddev.led.stripcontrol.javastripbackend.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract model class which should be persisted in the database
 * 
 * @author thum
 *
 */
@MappedSuperclass
public abstract class AbstractModel
{
	@Id
	@GeneratedValue ( strategy = GenerationType.SEQUENCE )
	private Long id;

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

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( this.id == null ) ? 0 : this.id.hashCode( ) );
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
		final AbstractModel other = (AbstractModel) obj;
		if ( this.id == null )
		{
			if ( other.id != null )
				return false;
		} else if ( !this.id.equals( other.id ) )
			return false;
		return true;
	}

}

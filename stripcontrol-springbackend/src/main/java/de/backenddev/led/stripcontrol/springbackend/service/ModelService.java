package de.backenddev.led.stripcontrol.springbackend.service;

import java.util.Optional;

/**
 * Handles the control of and actions on objects of type T
 * 
 * @author thum
 *
 */
public interface ModelService<T>
{
	/**
	 * handles the creation and update of an object<br/>
	 * 
	 * @param object
	 *            the object to store
	 * @return the created object
	 */
	public T save( T object );

	/**
	 * handles the deletion of an object <br/>
	 * 
	 * @param id
	 */
	public void remove( long id );

	/**
	 * loads the object by its id
	 * 
	 * @param id
	 * @return
	 */
	public Optional<T> getById( long id );

	/**
	 * retrieves all objects of this type from the database
	 * 
	 * @return
	 */
	public Iterable<T> getAll( );
}

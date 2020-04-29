package de.backenddev.led.stripcontrol.ledhandling;

public interface IEvent<T>
{
	/**
	 * @return the type
	 */
	public EventType getType( );

	/**
	 * @return the id
	 */
	public Long getId( );

	/**
	 * @return the oldState
	 */
	public T getState( );
}

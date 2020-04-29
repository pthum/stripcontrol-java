package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import org.springframework.context.ApplicationEvent;

public abstract class Event<T> extends ApplicationEvent
{
	private final EventType type;
	private final T state;
	private final Long id;

	/**
	 * 
	 * @param source
	 *            the source, must not be <code>null</code>
	 * @param type
	 *            the {@link EventType}
	 * @param strip
	 *            may be <code>null</code> for a {@link EventType#DELETE} event or
	 *            the object AFTER save
	 * @param id
	 *            may be <code>null</code> for a {@link EventType#SAVE} with a new
	 *            object, otherwise contain the id
	 */
	public Event( final Object source, final EventType type, final T state, final Long id )
	{
		super( source );
		this.type = type;
		this.state = state;
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public EventType getType( )
	{
		return this.type;
	}

	/**
	 * @return the id
	 */
	public Long getId( )
	{
		return this.id;
	}

	/**
	 * @return the oldState
	 */
	public T getState( )
	{
		return this.state;
	}

	@Override
	public String toString( )
	{
		return "Event [type=" + this.type + ", state=" + this.state + ", id=" + this.id + "]";
	}

}

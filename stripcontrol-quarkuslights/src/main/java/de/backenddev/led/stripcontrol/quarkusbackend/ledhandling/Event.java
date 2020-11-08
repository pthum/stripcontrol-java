package de.backenddev.led.stripcontrol.quarkusbackend.ledhandling;

import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import de.backenddev.led.stripcontrol.ledhandling.EventType;
import de.backenddev.led.stripcontrol.ledhandling.IEvent;

public abstract class Event<T> implements IEvent<T>
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
	public Event( final EventType type, final T state, final Long id )
	{
		this.type = type;
		this.state = state;
		this.id = id;
	}

	/**
	 * @return the type
	 */
	@Override
	public EventType getType( )
	{
		return this.type;
	}

	/**
	 * @return the id
	 */
	@Override
	public Long getId( )
	{
		return this.id;
	}

	/**
	 * @return the oldState
	 */
	@Override
	public T getState( )
	{
		return this.state;
	}

	@Override
	public String toString( )
	{
		final Gson gson = new Gson( );
		return gson.toJson( this );
	}

	public static <Z> Z fromJson( final String json, final Class<Z> clazz )
	{
		final Gson gson = new Gson( );
		return gson.fromJson( json, clazz );
	}

	public static <Z> Z fromJson( final byte[] json, final Class<Z> clazz )
	{
		final Gson gson = new Gson( );

		return gson.fromJson( new String( json, StandardCharsets.UTF_8 ), clazz );
	}

}

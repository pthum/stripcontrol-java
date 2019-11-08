package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import org.springframework.context.ApplicationEvent;

import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

public class StripEvent extends ApplicationEvent
{

	private static final long serialVersionUID = -2371775511199519174L;
	private final EventType type;
	private final LEDStrip strip;
	private final Long stripId;

	/**
	 * 
	 * @param source
	 *            the source, must not be <code>null</code>
	 * @param type
	 *            the {@link EventType}
	 * @param strip
	 *            may be <code>null</code> for a {@link EventType#DELETE} event or
	 *            the object AFTER save
	 * @param stripId
	 *            may be <code>null</code> for a {@link EventType#SAVE} with a new
	 *            object, otherwise contain the id
	 */
	public StripEvent( Object source, EventType type, LEDStrip strip, Long stripId )
	{
		super( source );
		this.type = type;
		this.strip = strip;
		this.stripId = stripId;
	}

	/**
	 * @return the type
	 */
	public EventType getType( )
	{
		return type;
	}

	/**
	 * @return the strip
	 */
	public LEDStrip getStrip( )
	{
		return strip;
	}

	/**
	 * @return the stripId
	 */
	public Long getStripId( )
	{
		return stripId;
	}

	@Override
	public String toString( )
	{
		return "StripEvent [type=" + type + ", strip=" + strip + ", stripId=" + stripId + "]";
	}

}

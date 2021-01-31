package de.backenddev.led.stripcontrol.quarkusbackend.ledhandling;

import de.backenddev.led.stripcontrol.ledhandling.EventType;
import de.backenddev.led.stripcontrol.model.LEDStrip;

public class StripEvent extends Event<LEDStrip>
{

	private static final long serialVersionUID = -2371775511199519174L;

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
	public StripEvent( final EventType type, final LEDStrip strip, final Long stripId )
	{
		super( type, strip, stripId );
	}
}

package de.backenddev.led.stripcontrol.quarkusbackend.ledhandling;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EventType;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;

public class ProfileEvent extends Event<ColorProfile>
{
	private static final long serialVersionUID = -6436182964039671435L;

	/**
	 * 
	 * @param source
	 *            the source, must not be <code>null</code>
	 * @param type
	 *            the {@link EventType}
	 * @param profile
	 *            may be <code>null</code> for a {@link EventType#DELETE} event or
	 *            the object AFTER save
	 * @param profileId
	 *            may be <code>null</code> for a {@link EventType#SAVE} with a new
	 *            object, otherwise contain the id
	 */
	public ProfileEvent( final Object source, final EventType type, final ColorProfile profile, final Long profileId )
	{
		super( source, type, profile, profileId );
	}
}

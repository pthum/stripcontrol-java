package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import org.springframework.context.ApplicationEvent;

import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;

public class ProfileEvent extends ApplicationEvent
{
	private static final long serialVersionUID = -6436182964039671435L;
	private final EventType type;
	private final ColorProfile profile;
	private final Long profileId;

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
	public ProfileEvent( Object source, EventType type, ColorProfile strip, Long stripId )
	{
		super( source );
		this.type = type;
		this.profile = strip;
		this.profileId = stripId;
	}

	/**
	 * @return the type
	 */
	public EventType getType( )
	{
		return type;
	}

	/**
	 * @return the profile
	 */
	public ColorProfile getProfile( )
	{
		return profile;
	}

	/**
	 * @return the profileId
	 */
	public Long getProfileId( )
	{
		return profileId;
	}

	@Override
	public String toString( )
	{
		return "ProfileEvent [type=" + type + ", profile=" + profile + ", profileId=" + profileId + "]";
	}

}

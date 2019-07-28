package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.util.Optional;

import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;

public interface ColorProfileService
{
	public ColorProfile saveColorProfile(ColorProfile profile );

	public void removeColorProfile(long id );

	public Optional<ColorProfile> getById(long id );

	public Iterable<ColorProfile> getAllColorProfiles( );
}

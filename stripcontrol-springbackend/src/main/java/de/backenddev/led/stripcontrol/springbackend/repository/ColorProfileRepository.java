package de.backenddev.led.stripcontrol.springbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.backenddev.led.stripcontrol.model.ColorProfile;

public interface ColorProfileRepository extends CrudRepository<ColorProfile, Long>
{
	List<ColorProfile> findByRedAndGreenAndBlueAndBrightness( final int red, final int green, final int blue,
			final int brightness );
}
package de.backenddev.led.stripcontrol.quarkusbackend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

public interface LEDStripRepository extends CrudRepository<LEDStrip, Long>
{
	public List<LEDStrip> findByEnabled(final boolean enabled );
}
package de.backenddev.led.stripcontrol.javastripbackend.repository;

import org.springframework.data.repository.CrudRepository;

import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

public interface LEDStripRepository extends CrudRepository<LEDStrip, Long>
{

}
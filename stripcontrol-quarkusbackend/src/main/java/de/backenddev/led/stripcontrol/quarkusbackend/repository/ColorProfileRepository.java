package de.backenddev.led.stripcontrol.quarkusbackend.repository;

import org.springframework.data.repository.CrudRepository;

import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;

public interface ColorProfileRepository extends CrudRepository<ColorProfile, Long>
{

}
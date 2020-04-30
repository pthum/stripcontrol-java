package de.backenddev.led.stripcontrol.springbackend.repository;

import org.springframework.data.repository.CrudRepository;

import de.backenddev.led.stripcontrol.model.ColorProfile;

public interface ColorProfileRepository extends CrudRepository<ColorProfile, Long>
{

}
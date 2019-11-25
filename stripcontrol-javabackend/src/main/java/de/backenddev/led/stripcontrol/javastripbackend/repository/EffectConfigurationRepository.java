package de.backenddev.led.stripcontrol.javastripbackend.repository;

import org.springframework.data.repository.CrudRepository;

import de.backenddev.led.stripcontrol.javastripbackend.model.EffectConfiguration;

public interface EffectConfigurationRepository extends CrudRepository<EffectConfiguration, Long>
{
}
package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.util.Optional;

import de.backenddev.led.stripcontrol.javastripbackend.model.EffectConfiguration;

public interface EffectConfigurationService
{
	public EffectConfiguration saveEffectConfiguration( EffectConfiguration effectConfiguration );

	public void removeEffectConfiguration( long id );

	public Optional<EffectConfiguration> getById( long id );

	public Iterable<EffectConfiguration> getAllEffectConfigurations( );
}

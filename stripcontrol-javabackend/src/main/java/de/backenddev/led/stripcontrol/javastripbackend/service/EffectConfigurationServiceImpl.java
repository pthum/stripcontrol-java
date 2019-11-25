package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EventType;
import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.javastripbackend.model.EffectConfiguration;
import de.backenddev.led.stripcontrol.javastripbackend.repository.EffectConfigurationRepository;

@Service
public class EffectConfigurationServiceImpl implements EffectConfigurationService
{
	@Autowired
	private EffectConfigurationRepository repo;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public EffectConfiguration saveEffectConfiguration( final EffectConfiguration effectConfiguration )
	{
		final EffectConfiguration result = this.repo.save( effectConfiguration );
		return result;
	}

	@Override
	public void removeEffectConfiguration( final long id )
	{
		this.repo.deleteById( id );
		this.applicationEventPublisher.publishEvent( new StripEvent( this, EventType.DELETE, null, id ) );
	}

	@Override
	public Optional<EffectConfiguration> getById( final long id )
	{
		return this.repo.findById( id );
	}

	@Override
	public Iterable<EffectConfiguration> getAllEffectConfigurations( )
	{
		return this.repo.findAll( );
	}

}

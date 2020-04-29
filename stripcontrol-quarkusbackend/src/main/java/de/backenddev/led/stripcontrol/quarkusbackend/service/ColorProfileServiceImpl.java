package de.backenddev.led.stripcontrol.quarkusbackend.service;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EventType;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.ProfileEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.repository.ColorProfileRepository;
import io.vertx.core.eventbus.EventBus;

@Service
public class ColorProfileServiceImpl implements ModelService<ColorProfile>
{
	@Autowired
	ColorProfileRepository repo;

	@Inject
	EventBus eventPublisher;

	@Override
	public ColorProfile save( final ColorProfile profile )
	{
		final Long idBeforeSave = profile != null ? profile.getId( ) : null;
		final ColorProfile result = this.repo.save( profile );
		/* due to efficiency: only send updates */
		if ( idBeforeSave != null )
		{
			this.eventPublisher.send( "ProfileEvent", new ProfileEvent( this, EventType.SAVE, result, idBeforeSave ) );
		}
		return result;
	}

	@Override
	public void remove( final long id )
	{
		this.repo.deleteById( id );
		this.eventPublisher.send( "StripEvent", new StripEvent( this, EventType.DELETE, null, id ) );
	}

	@Override
	public Optional<ColorProfile> getById( final long id )
	{
		return this.repo.findById( id );
	}

	@Override
	public Iterable<ColorProfile> getAll( )
	{
		return this.repo.findAll( );
	}

}

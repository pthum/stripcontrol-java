package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EventType;
import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.ProfileEvent;
import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.repository.ColorProfileRepository;

@Service
public class ColorProfileServiceImpl implements ColorProfileService
{
	@Autowired
	private ColorProfileRepository repo;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public ColorProfile saveColorProfile( final ColorProfile profile )
	{
		final Long idBeforeSave = profile != null ? profile.getId( ) : null;
		final ColorProfile result = this.repo.save( profile );
		/* due to efficiency: only send updates */
		if ( idBeforeSave != null )
		{
			this.applicationEventPublisher
					.publishEvent( new ProfileEvent( this, EventType.SAVE, result, idBeforeSave ) );
		}
		return result;
	}

	@Override
	public void removeColorProfile( final long id )
	{
		this.repo.deleteById( id );
		this.applicationEventPublisher.publishEvent( new StripEvent( this, EventType.DELETE, null, id ) );
	}

	@Override
	public Optional<ColorProfile> getById( final long id )
	{
		return this.repo.findById( id );
	}

	@Override
	public Iterable<ColorProfile> getAllColorProfiles( )
	{
		return this.repo.findAll( );
	}

}

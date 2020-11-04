package de.backenddev.led.stripcontrol.quarkusbackend.service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.backenddev.led.stripcontrol.ledhandling.EventType;
import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.quarkusbackend.aqmp.LEDSender;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.ProfileEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.repository.ColorProfileRepository;

@ApplicationScoped
public class ColorProfileServiceImpl implements ModelService<ColorProfile>
{
	@Inject
	ColorProfileRepository repo;

	@Inject
	LEDSender ledSender;

	@Override
	public ColorProfile save( final ColorProfile profile )
	{
		final Long idBeforeSave = profile != null ? profile.getId( ) : null;
		final ColorProfile result = this.repo.save( profile );
		/* due to efficiency: only send updates */
		if ( idBeforeSave != null )
		{
			this.ledSender.send( new ProfileEvent( this, EventType.SAVE, result, idBeforeSave ) );
		}
		return result;
	}

	@Override
	public void remove( final long id )
	{
		this.repo.deleteById( id );
		this.ledSender.send( new StripEvent( this, EventType.DELETE, null, id ) );
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

package de.backenddev.led.stripcontrol.quarkusbackend.service;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EventType;
import de.backenddev.led.stripcontrol.model.LEDStrip;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.repository.LEDStripRepository;
import io.vertx.core.eventbus.EventBus;

@Service
public class LEDStripServiceImpl implements ModelService<LEDStrip>
{
	@Autowired
	LEDStripRepository repo;

	@Inject
	EventBus eventPublisher;

	@Override
	public LEDStrip save( final LEDStrip strip )
	{
		final Long idBeforeSave = strip != null ? strip.getId( ) : null;
		final LEDStrip result = this.repo.save( strip );
		this.eventPublisher.send( "StripEvent", new StripEvent( this, EventType.SAVE, result, idBeforeSave ) );
		return result;
	}

	public LEDStrip toggleStrip( final Long id, final boolean state )
	{
		final Optional<LEDStrip> optStrip = this.repo.findById( id );
		if ( optStrip.isPresent( ) == false )
		{
			return null;
		}
		final LEDStrip oldStrip = optStrip.get( );
		oldStrip.setEnabled( state );
		final LEDStrip result = this.repo.save( oldStrip );
		this.eventPublisher.send( "StripEvent", new StripEvent( this, EventType.SAVE, result, id ) );
		return result;
	}

	@Override
	public void remove( final long id )
	{
		this.repo.deleteById( id );
		this.eventPublisher.send( "StripEvent", new StripEvent( this, EventType.DELETE, null, id ) );
	}

	@Override
	public Iterable<LEDStrip> getAll( )
	{
		return this.repo.findAll( );
	}

	@Override
	public Optional<LEDStrip> getById( final long id )
	{
		return this.repo.findById( id );
	}

}

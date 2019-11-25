package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.EventType;
import de.backenddev.led.stripcontrol.javastripbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.repository.LEDStripRepository;

@Service
public class LEDStripServiceImpl implements ModelService<LEDStrip>
{
	@Autowired
	private LEDStripRepository repo;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public LEDStrip save( final LEDStrip strip )
	{
		final Long idBeforeSave = strip != null ? strip.getId( ) : null;
		final LEDStrip result = this.repo.save( strip );
		this.applicationEventPublisher.publishEvent( new StripEvent( this, EventType.SAVE, result, idBeforeSave ) );
		return result;
	}

	@Override
	public void remove( final long id )
	{
		this.repo.deleteById( id );
		this.applicationEventPublisher.publishEvent( new StripEvent( this, EventType.DELETE, null, id ) );
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

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
public class LEDStripServiceImpl implements LEDStripService
{
	@Autowired
	private LEDStripRepository repo;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public LEDStrip saveLEDStrip(LEDStrip strip )
	{
		Long idBeforeSave = strip != null ? strip.getId( ) : null;
		LEDStrip result = repo.save( strip );
		applicationEventPublisher.publishEvent( new StripEvent( this, EventType.SAVE, result, idBeforeSave ) );
		return result;
	}

	@Override
	public void removeLEDStrip(long id )
	{
		repo.deleteById( id );
		applicationEventPublisher.publishEvent( new StripEvent( this, EventType.DELETE, null, id ) );
	}

	@Override
	public Iterable<LEDStrip> getAllLEDStrips( )
	{
		return repo.findAll( );
	}

	@Override
	public Optional<LEDStrip> getById(long id )
	{
		return repo.findById( id );
	}

}

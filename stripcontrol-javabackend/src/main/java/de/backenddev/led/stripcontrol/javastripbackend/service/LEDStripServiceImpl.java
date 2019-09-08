package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.repository.LEDStripRepository;

@Service
public class LEDStripServiceImpl implements LEDStripService
{
	@Autowired
	private LEDStripRepository repo;

	@Override
	public LEDStrip saveLEDStrip(LEDStrip profile )
	{
		return repo.save( profile );
	}

	@Override
	public void removeLEDStrip(long id )
	{
		repo.deleteById( id );
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

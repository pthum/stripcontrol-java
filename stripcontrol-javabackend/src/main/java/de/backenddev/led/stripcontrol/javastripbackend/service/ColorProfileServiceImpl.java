package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.repository.ColorProfileRepository;

@Service
public class ColorProfileServiceImpl implements ColorProfileService
{
	@Autowired
	private ColorProfileRepository repo;

	@Override
	public ColorProfile saveColorProfile(ColorProfile profile )
	{
		return repo.save( profile );
	}

	@Override
	public void removeColorProfile(long id )
	{
		repo.deleteById( id );
	}

	@Override
	public Optional<ColorProfile> getById(long id )
	{
		return repo.findById( id );
	}

	@Override
	public Iterable<ColorProfile> getAllColorProfiles( )
	{
		return repo.findAll( );
	}

}

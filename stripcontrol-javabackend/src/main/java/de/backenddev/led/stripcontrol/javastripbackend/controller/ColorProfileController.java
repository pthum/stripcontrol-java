package de.backenddev.led.stripcontrol.javastripbackend.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.service.ColorProfileService;

@RestController
@RequestMapping ( BackendController.API_BASE + "/colorprofile" )
public class ColorProfileController
{
	private static final Logger LOG = LoggerFactory.getLogger( ColorProfileController.class );

	@Autowired
	private ColorProfileService service;

	@GetMapping ( "" )
	public Iterable<ColorProfile> getColorProfiles( )
	{
		return service.getAllColorProfiles( );
	}

	@PostMapping ( "" )
	public ResponseEntity<Object> createColorProfile(@Valid @RequestBody ColorProfile colorProfile )
	{
		final ColorProfile createdProfile = service.saveColorProfile( colorProfile );
		LOG.info( "ReturnObj: " + createdProfile );
		URI location = ServletUriComponentsBuilder.fromCurrentRequest( ).path( "/{id}" )
				.buildAndExpand( createdProfile.getId( ) ).toUri( );
		return ResponseEntity.created( location ).build( );

	}

	@GetMapping ( "/{id}" )
	public ResponseEntity<ColorProfile> getColorProfile(@PathVariable Long id )
	{
		final Optional<ColorProfile> profile = service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( profile.get( ) );
	}

	@PutMapping ( "/{id}" )
	public ResponseEntity<Object> updateColorProfile(@PathVariable Long id,
			@Valid @RequestBody ColorProfile colorProfile )
	{
		Optional<ColorProfile> profile = service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		colorProfile.setId( profile.get( ).getId( ) );
		service.saveColorProfile( colorProfile );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}" )
	public ResponseEntity<Object> deleteColorProfile(@PathVariable Long id )
	{
		Optional<ColorProfile> profile = service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		service.removeColorProfile( id );
		return ResponseEntity.noContent( ).build( );
	}
}

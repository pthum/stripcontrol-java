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

import de.backenddev.led.stripcontrol.javastripbackend.JavastripBackendApplication;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.service.ModelService;

@RestController
@RequestMapping ( JavastripBackendApplication.API_BASE + "/ledstrip" )
public class LEDStripController
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDStripController.class );

	@Autowired
	private ModelService<LEDStrip> service;

	@Autowired
	private ModelService<ColorProfile> cpService;

	@GetMapping ( "" )
	public Iterable<LEDStrip> getLEDStrips( )
	{
		return this.service.getAll( );
	}

	@PostMapping ( "" )
	public ResponseEntity<Object> createLEDStrip( @Valid @RequestBody final LEDStrip ledStrip )
	{
		final LEDStrip createdStrip = this.service.save( ledStrip );
		LOG.info( "ReturnObj: " + createdStrip );
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest( ).path( "/{id}" )
				.buildAndExpand( createdStrip.getId( ) ).toUri( );
		return ResponseEntity.created( location ).build( );

	}

	@GetMapping ( "/{id}" )
	public ResponseEntity<LEDStrip> getLEDStrip( @PathVariable final Long id )
	{
		final Optional<LEDStrip> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( strip.get( ) );
	}

	@PutMapping ( "/{id}" )
	public ResponseEntity<Object> updateLEDStrip( @PathVariable final Long id,
			@Valid @RequestBody final LEDStrip ledStrip )
	{
		final Optional<LEDStrip> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		ledStrip.setId( strip.get( ).getId( ) );
		ledStrip.setProfile( strip.get( ).getProfile( ) );
		this.service.save( ledStrip );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}" )
	public ResponseEntity<Object> deleteLEDStrip( @PathVariable final Long id )
	{
		final Optional<LEDStrip> optStrip = this.service.getById( id );
		if ( optStrip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		this.service.remove( id );
		return ResponseEntity.noContent( ).build( );
	}

	@GetMapping ( "/{id}/profile" )
	public ResponseEntity<ColorProfile> getLEDStripProfile( @PathVariable final Long id )
	{
		final Optional<LEDStrip> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false || strip.get( ).hasProfile( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( strip.get( ).getProfile( ) );
	}

	@PutMapping ( "/{id}/profile" )
	public ResponseEntity<Object> updateLEDStripProfile( @PathVariable final Long id,
			@Valid @RequestBody final ColorProfile colorProfile )
	{
		final Optional<LEDStrip> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final Optional<ColorProfile> dbProfile = this.cpService.getById( colorProfile.getId( ) );
		if ( dbProfile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final LEDStrip updateStrip = strip.get( );
		updateStrip.setProfile( dbProfile.get( ) );
		this.service.save( updateStrip );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}/profile" )
	public ResponseEntity<Object> deleteLEDStripProfile( @PathVariable final Long id )
	{
		final Optional<LEDStrip> optStrip = this.service.getById( id );
		if ( optStrip.isPresent( ) == false || optStrip.get( ).hasProfile( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final LEDStrip strip = optStrip.get( );
		strip.setProfile( null );
		this.service.save( strip );
		return ResponseEntity.noContent( ).build( );
	}

}

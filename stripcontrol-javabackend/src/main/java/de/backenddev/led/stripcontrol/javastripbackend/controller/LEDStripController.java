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
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.service.ColorProfileService;
import de.backenddev.led.stripcontrol.javastripbackend.service.LEDStripService;

@RestController
@RequestMapping ( BackendController.API_BASE + "/ledstrip" )
public class LEDStripController
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDStripController.class );

	@Autowired
	private LEDStripService service;

	@Autowired
	private ColorProfileService cpService;

	@GetMapping ( "" )
	public Iterable<LEDStrip> getLEDStrips( )
	{
		return service.getAllLEDStrips( );
	}

	@PostMapping ( "" )
	public ResponseEntity<Object> createLEDStrip(@Valid @RequestBody LEDStrip ledStrip )
	{
		final LEDStrip createdStrip = service.saveLEDStrip( ledStrip );
		LOG.info( "ReturnObj: " + createdStrip );
		URI location = ServletUriComponentsBuilder.fromCurrentRequest( ).path( "/{id}" )
				.buildAndExpand( createdStrip.getId( ) ).toUri( );
		return ResponseEntity.created( location ).build( );

	}

	@GetMapping ( "/{id}" )
	public ResponseEntity<LEDStrip> getLEDStrip(@PathVariable Long id )
	{
		final Optional<LEDStrip> strip = service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( strip.get( ) );
	}

	@PutMapping ( "/{id}" )
	public ResponseEntity<Object> updateLEDStrip(@PathVariable Long id, @Valid @RequestBody LEDStrip ledStrip )
	{
		Optional<LEDStrip> strip = service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		ledStrip.setId( strip.get( ).getId( ) );
		service.saveLEDStrip( ledStrip );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}" )
	public ResponseEntity<Object> deleteLEDStrip(@PathVariable Long id )
	{
		Optional<LEDStrip> optStrip = service.getById( id );
		if ( optStrip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		service.removeLEDStrip( id );
		return ResponseEntity.noContent( ).build( );
	}

	@GetMapping ( "/{id}/profile" )
	public ResponseEntity<ColorProfile> getLEDStripProfile(@PathVariable Long id )
	{
		final Optional<LEDStrip> strip = service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		if ( strip.get( ).getProfile( ) == null )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( strip.get( ).getProfile( ) );
	}

	@PutMapping ( "/{id}/profile" )
	public ResponseEntity<Object> updateLEDStripProfile(@PathVariable Long id,
			@Valid @RequestBody ColorProfile colorProfile )
	{
		Optional<LEDStrip> strip = service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		Optional<ColorProfile> dbProfile = cpService.getById( colorProfile.getId( ) );
		if ( dbProfile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		LEDStrip updateStrip = strip.get( );
		updateStrip.setProfile( dbProfile.get( ) );
		service.saveLEDStrip( updateStrip );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}/profile" )
	public ResponseEntity<Object> deleteLEDStripProfile(@PathVariable Long id )
	{
		Optional<LEDStrip> optStrip = service.getById( id );
		if ( optStrip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		LEDStrip strip = optStrip.get( );
		if ( strip.getProfile( ) == null )
		{
			return ResponseEntity.notFound( ).build( );
		}
		strip.setProfile( null );
		service.saveLEDStrip( strip );
		return ResponseEntity.noContent( ).build( );
	}
}

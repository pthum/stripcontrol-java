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

import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.service.LEDStripService;

@RestController
@RequestMapping ( BackendController.API_BASE + "/ledstrip" )
public class LEDStripController
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDStripController.class );

	@Autowired
	private LEDStripService service;

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
		Optional<LEDStrip> profile = service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		service.removeLEDStrip( id );
		return ResponseEntity.noContent( ).build( );
	}
}

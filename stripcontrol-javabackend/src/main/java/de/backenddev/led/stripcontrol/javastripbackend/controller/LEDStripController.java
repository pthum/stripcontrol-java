package de.backenddev.led.stripcontrol.javastripbackend.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.backenddev.led.stripcontrol.javastripbackend.JavastripBackendApplication;
import de.backenddev.led.stripcontrol.javastripbackend.service.ModelService;
import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;

@RestController
@RequestMapping ( JavastripBackendApplication.API_BASE + "/ledstrip" )
public class LEDStripController extends AbstractModelController<LEDStrip>
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDStripController.class );

	@Autowired
	private ModelService<LEDStrip> service;

	@Autowired
	private ModelService<ColorProfile> cpService;

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

	@Override
	ModelService<LEDStrip> getModelService( )
	{
		return this.service;
	}

	@Override
	Logger getLogger( )
	{
		return LOG;
	}

	@Override
	void prepareUpdateObjectBeforeSave( final LEDStrip updateObject, final LEDStrip dbObject )
	{
		updateObject.setProfile( dbObject.getProfile( ) );
	}

}

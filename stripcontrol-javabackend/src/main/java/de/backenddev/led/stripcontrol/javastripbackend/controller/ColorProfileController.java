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
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.EffectConfiguration;
import de.backenddev.led.stripcontrol.javastripbackend.service.ModelService;

@RestController
@RequestMapping ( JavastripBackendApplication.API_BASE + "/colorprofile" )
public class ColorProfileController extends AbstractModelController<ColorProfile>
{
	private static final Logger LOG = LoggerFactory.getLogger( ColorProfileController.class );

	@Autowired
	private ModelService<ColorProfile> service;

	@Autowired
	private ModelService<EffectConfiguration> effectService;

	@GetMapping ( "/{id}/onEffect" )
	public ResponseEntity<EffectConfiguration> getOnEffect( @PathVariable final Long id )
	{
		final Optional<ColorProfile> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false || strip.get( ).hasOnEffect( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( strip.get( ).getOnEffect( ) );
	}

	@PutMapping ( "/{id}/onEffect" )
	public ResponseEntity<Object> updateOnEffect( @PathVariable final Long id,
			@Valid @RequestBody final EffectConfiguration onEffect )
	{
		final Optional<ColorProfile> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final Optional<EffectConfiguration> dbEffect = this.effectService.getById( onEffect.getId( ) );
		if ( dbEffect.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final ColorProfile updateStrip = strip.get( );
		updateStrip.setOnEffect( dbEffect.get( ) );
		this.service.save( updateStrip );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}/onEffect" )
	public ResponseEntity<Object> deleteOnEffect( @PathVariable final Long id )
	{
		final Optional<ColorProfile> optStrip = this.service.getById( id );
		if ( optStrip.isPresent( ) == false || optStrip.get( ).hasOnEffect( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final ColorProfile strip = optStrip.get( );
		strip.setOnEffect( null );
		this.service.save( strip );
		return ResponseEntity.noContent( ).build( );
	}

	@GetMapping ( "/{id}/offEffect" )
	public ResponseEntity<EffectConfiguration> getOffEffect( @PathVariable final Long id )
	{
		final Optional<ColorProfile> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false || strip.get( ).hasOffEffect( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( strip.get( ).getOffEffect( ) );
	}

	@PutMapping ( "/{id}/offEffect" )
	public ResponseEntity<Object> updateOffEffect( @PathVariable final Long id,
			@Valid @RequestBody final EffectConfiguration offEffect )
	{
		final Optional<ColorProfile> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final Optional<EffectConfiguration> dbEffect = this.effectService.getById( offEffect.getId( ) );
		if ( dbEffect.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final ColorProfile updateStrip = strip.get( );
		updateStrip.setOffEffect( dbEffect.get( ) );
		this.service.save( updateStrip );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}/offEffect" )
	public ResponseEntity<Object> deleteOffEffect( @PathVariable final Long id )
	{
		final Optional<ColorProfile> optStrip = this.service.getById( id );
		if ( optStrip.isPresent( ) == false || optStrip.get( ).hasOffEffect( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final ColorProfile strip = optStrip.get( );
		strip.setOffEffect( null );
		this.service.save( strip );
		return ResponseEntity.noContent( ).build( );
	}

	@Override
	ModelService<ColorProfile> getModelService( )
	{
		return this.service;
	}

	@Override
	Logger getLogger( )
	{
		return LOG;
	}

	@Override
	void prepareUpdateObjectBeforeSave( final ColorProfile updateObject, final ColorProfile dbObject )
	{
		updateObject.setOffEffect( dbObject.getOffEffect( ) );
		updateObject.setOnEffect( dbObject.getOnEffect( ) );
	}

}

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
import de.backenddev.led.stripcontrol.javastripbackend.model.EffectConfiguration;
import de.backenddev.led.stripcontrol.javastripbackend.service.ColorProfileService;
import de.backenddev.led.stripcontrol.javastripbackend.service.EffectConfigurationService;

@RestController
@RequestMapping ( JavastripBackendApplication.API_BASE + "/colorprofile" )
public class ColorProfileController
{
	private static final Logger LOG = LoggerFactory.getLogger( ColorProfileController.class );

	@Autowired
	private ColorProfileService service;

	@Autowired
	private EffectConfigurationService effService;

	@GetMapping ( "" )
	public Iterable<ColorProfile> getColorProfiles( )
	{
		return this.service.getAllColorProfiles( );
	}

	@PostMapping ( "" )
	public ResponseEntity<Object> createColorProfile( @Valid @RequestBody final ColorProfile colorProfile )
	{
		final ColorProfile createdProfile = this.service.saveColorProfile( colorProfile );
		LOG.info( "ReturnObj: " + createdProfile );
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest( ).path( "/{id}" )
				.buildAndExpand( createdProfile.getId( ) ).toUri( );
		return ResponseEntity.created( location ).build( );

	}

	@GetMapping ( "/{id}" )
	public ResponseEntity<ColorProfile> getColorProfile( @PathVariable final Long id )
	{
		final Optional<ColorProfile> profile = this.service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( profile.get( ) );
	}

	@PutMapping ( "/{id}" )
	public ResponseEntity<Object> updateColorProfile( @PathVariable final Long id,
			@Valid @RequestBody final ColorProfile colorProfile )
	{
		final Optional<ColorProfile> profile = this.service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		colorProfile.setId( profile.get( ).getId( ) );
		this.service.saveColorProfile( colorProfile );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}" )
	public ResponseEntity<Object> deleteColorProfile( @PathVariable final Long id )
	{
		final Optional<ColorProfile> profile = this.service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		this.service.removeColorProfile( id );
		return ResponseEntity.noContent( ).build( );
	}

	@GetMapping ( "/{id}/onEffect" )
	public ResponseEntity<EffectConfiguration> getOnEffect( @PathVariable final Long id )
	{
		final Optional<ColorProfile> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		if ( strip.get( ).hasOnEffect( ) )
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
		final Optional<EffectConfiguration> dbEffect = this.effService.getById( onEffect.getId( ) );
		if ( dbEffect.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final ColorProfile updateStrip = strip.get( );
		updateStrip.setOnEffect( dbEffect.get( ) );
		this.service.saveColorProfile( updateStrip );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}/onEffect" )
	public ResponseEntity<Object> deleteOnEffect( @PathVariable final Long id )
	{
		final Optional<ColorProfile> optStrip = this.service.getById( id );
		if ( optStrip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final ColorProfile strip = optStrip.get( );
		if ( strip.hasOnEffect( ) )
		{
			return ResponseEntity.notFound( ).build( );
		}
		strip.setOnEffect( null );
		this.service.saveColorProfile( strip );
		return ResponseEntity.noContent( ).build( );
	}
	@GetMapping ( "/{id}/offEffect" )
	public ResponseEntity<EffectConfiguration> getOffEffect( @PathVariable final Long id )
	{
		final Optional<ColorProfile> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		if ( strip.get( ).hasOffEffect( ) )
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
		final Optional<EffectConfiguration> dbEffect = this.effService.getById( offEffect.getId( ) );
		if ( dbEffect.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final ColorProfile updateStrip = strip.get( );
		updateStrip.setOffEffect( dbEffect.get( ) );
		this.service.saveColorProfile( updateStrip );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}/offEffect" )
	public ResponseEntity<Object> deleteOffEffect( @PathVariable final Long id )
	{
		final Optional<ColorProfile> optStrip = this.service.getById( id );
		if ( optStrip.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final ColorProfile strip = optStrip.get( );
		if ( strip.hasOffEffect( ) )
		{
			return ResponseEntity.notFound( ).build( );
		}
		strip.setOffEffect( null );
		this.service.saveColorProfile( strip );
		return ResponseEntity.noContent( ).build( );
	}

}

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
import de.backenddev.led.stripcontrol.javastripbackend.model.EffectConfiguration;
import de.backenddev.led.stripcontrol.javastripbackend.service.EffectConfigurationService;

@RestController
@RequestMapping ( JavastripBackendApplication.API_BASE + "/EffectConfiguration" )
public class EffectConfigurationController
{
	private static final Logger LOG = LoggerFactory.getLogger( EffectConfigurationController.class );

	@Autowired
	private EffectConfigurationService service;

	@GetMapping ( "" )
	public Iterable<EffectConfiguration> getEffectConfigurations( )
	{
		return this.service.getAllEffectConfigurations( );
	}

	@PostMapping ( "" )
	public ResponseEntity<Object> createEffectConfiguration(
			@Valid @RequestBody final EffectConfiguration EffectConfiguration )
	{
		final EffectConfiguration createdProfile = this.service.saveEffectConfiguration( EffectConfiguration );
		LOG.info( "ReturnObj: " + createdProfile );
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest( ).path( "/{id}" )
				.buildAndExpand( createdProfile.getId( ) ).toUri( );
		return ResponseEntity.created( location ).build( );

	}

	@GetMapping ( "/{id}" )
	public ResponseEntity<EffectConfiguration> getEffectConfiguration( @PathVariable final Long id )
	{
		final Optional<EffectConfiguration> profile = this.service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( profile.get( ) );
	}

	@PutMapping ( "/{id}" )
	public ResponseEntity<Object> updateEffectConfiguration( @PathVariable final Long id,
			@Valid @RequestBody final EffectConfiguration EffectConfiguration )
	{
		final Optional<EffectConfiguration> profile = this.service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		EffectConfiguration.setId( profile.get( ).getId( ) );
		this.service.saveEffectConfiguration( EffectConfiguration );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}" )
	public ResponseEntity<Object> deleteEffectConfiguration( @PathVariable final Long id )
	{
		final Optional<EffectConfiguration> profile = this.service.getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		this.service.removeEffectConfiguration( id );
		return ResponseEntity.noContent( ).build( );
	}
}

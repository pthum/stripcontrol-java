package de.backenddev.led.stripcontrol.javastripbackend.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.backenddev.led.stripcontrol.javastripbackend.service.ModelService;
import de.backenddev.led.stripcontrol.model.AbstractModel;

public abstract class AbstractModelController<T extends AbstractModel>
{
	@GetMapping ( "" )
	public Iterable<T> getAll( )
	{
		return getModelService( ).getAll( );
	}

	@PostMapping ( "" )
	public ResponseEntity<Object> create( @Valid @RequestBody final T EffectConfiguration )
	{
		final T createdProfile = getModelService( ).save( EffectConfiguration );
		getLogger( ).info( "ReturnObj: " + createdProfile );
		final URI location = ServletUriComponentsBuilder.fromCurrentRequest( ).path( "/{id}" )
				.buildAndExpand( createdProfile.getId( ) ).toUri( );
		return ResponseEntity.created( location ).build( );

	}

	@GetMapping ( "/{id}" )
	public ResponseEntity<T> get( @PathVariable final Long id )
	{
		final Optional<T> profile = getModelService( ).getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		return ResponseEntity.ok( profile.get( ) );
	}

	@PutMapping ( "/{id}" )
	public ResponseEntity<Object> update( @PathVariable final Long id, @Valid @RequestBody final T updateObject )
	{
		final Optional<T> optDbObject = getModelService( ).getById( id );
		if ( optDbObject.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		final T dbObject = optDbObject.get( );
		updateObject.setId( dbObject.getId( ) );
		prepareUpdateObjectBeforeSave( updateObject, dbObject );
		getModelService( ).save( updateObject );
		return ResponseEntity.ok( ).build( );
	}

	@DeleteMapping ( "/{id}" )
	public ResponseEntity<Object> delete( @PathVariable final Long id )
	{
		final Optional<T> profile = getModelService( ).getById( id );
		if ( profile.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		getModelService( ).remove( id );
		return ResponseEntity.noContent( ).build( );
	}

	abstract ModelService<T> getModelService( );

	abstract Logger getLogger( );

	abstract void prepareUpdateObjectBeforeSave( final T updateObject, final T dbObject );

}

package de.backenddev.led.stripcontrol.quarkusbackend.controller;

import java.net.URI;
import java.net.URISyntaxException;
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

import de.backenddev.led.stripcontrol.model.AbstractModel;
import de.backenddev.led.stripcontrol.quarkusbackend.TimerUtil;
import de.backenddev.led.stripcontrol.quarkusbackend.service.ModelService;

public abstract class AbstractModelController<T extends AbstractModel>
{
	public static final String API_BASE = "/api";

	@GetMapping ( "" )
	public Iterable<T> getAll( )
	{
		return getModelService( ).getAll( );
	}

	@PostMapping ( "" )
	public ResponseEntity<Object> create( @Valid @RequestBody final T EffectConfiguration ) throws URISyntaxException
	{
		final T createdProfile = getModelService( ).save( EffectConfiguration );
		getLogger( ).info( "ReturnObj: " + createdProfile );

		final URI location = new URI( "" );
		// ServletUriComponentsBuilder.fromCurrentRequest( ).path( "/{id}" )
		// .buildAndExpand( createdProfile.getId( ) ).toUri( );
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
		final TimerUtil timer = new TimerUtil( "REST-UpdateObject" );
		final Optional<T> optDbObject = getModelService( ).getById( id );
		if ( optDbObject.isPresent( ) == false )
		{
			return ResponseEntity.notFound( ).build( );
		}
		timer.logAndUpdate( "Get DB Object" );
		final T dbObject = optDbObject.get( );
		updateObject.setId( dbObject.getId( ) );
		prepareUpdateObjectBeforeSave( updateObject, dbObject );
		getModelService( ).save( updateObject );
		timer.logAndUpdate( "Update DB Object" );
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

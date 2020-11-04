package de.backenddev.led.stripcontrol.quarkusbackend.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

import de.backenddev.led.stripcontrol.model.AbstractModel;
import de.backenddev.led.stripcontrol.quarkusbackend.service.ModelService;

public abstract class AbstractModelController<T extends AbstractModel>
{
	public static final String API_BASE = "/api";

	protected Iterable<T> getAll( )
	{
		return getModelService( ).getAll( );
	}

	protected Response create( final T effectConfiguration ) throws URISyntaxException
	{
		final T createdProfile = getModelService( ).save( effectConfiguration );
		getLogger( ).info( "ReturnObj: " + createdProfile );

		final URI location = new URI( "" );
		// ServletUriComponentsBuilder.fromCurrentRequest( ).path( "/{id}" )
		// .buildAndExpand( createdProfile.getId( ) ).toUri( );

		return Response.created( location ).build( );

	}

	protected T get( final Long id )
	{
		final Optional<T> profile = getModelService( ).getById( id );
		return profile.orElseThrow( NotFoundException::new );
	}

	protected void update( final Long id, final T updateObject )
	{
		final Optional<T> optDbObject = getModelService( ).getById( id );
		final T dbObject = optDbObject.orElseThrow( NotFoundException::new );

		updateObject.setId( dbObject.getId( ) );
		prepareUpdateObjectBeforeSave( updateObject, dbObject );
		getModelService( ).save( updateObject );
	}

	protected Response delete( final Long id )
	{
		final Optional<T> profile = getModelService( ).getById( id );
		if ( profile.isPresent( ) == false )
		{
			throw new NotFoundException( );
		}
		getModelService( ).remove( id );
		return Response.noContent( ).build( );
	}

	abstract ModelService<T> getModelService( );

	abstract Logger getLogger( );

	abstract void prepareUpdateObjectBeforeSave( final T updateObject, final T dbObject );

}

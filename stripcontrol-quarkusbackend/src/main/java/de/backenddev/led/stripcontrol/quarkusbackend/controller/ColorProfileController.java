package de.backenddev.led.stripcontrol.quarkusbackend.controller;

import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.quarkusbackend.service.ModelService;

@Path ( AbstractModelController.API_BASE + "/colorprofile" )
@Produces ( MediaType.APPLICATION_JSON )
@Consumes ( MediaType.APPLICATION_JSON )
public class ColorProfileController extends AbstractModelController<ColorProfile>
{
	private static final Logger LOG = LoggerFactory.getLogger( ColorProfileController.class );

	@Inject
	ModelService<ColorProfile> service;

	@GET
	@Override
	public Iterable<ColorProfile> getAll( )
	{
		return super.getAll( );
	}

	@POST
	@Override
	public Response create( @Valid final ColorProfile effectConfiguration ) throws URISyntaxException
	{
		return super.create( effectConfiguration );
	}

	@GET
	@Path ( "/{id}" )
	@Override
	public ColorProfile get( @PathParam ( "id" ) final Long id )
	{
		return super.get( id );
	}

	@PUT
	@Path ( "/{id}" )
	@Override
	public void update( @PathParam ( "id" ) final Long id, @Valid final ColorProfile updateObject )
	{
		super.update( id, updateObject );
	}

	@DELETE
	@Path ( "/{id}" )
	@Override
	public Response delete( @PathParam ( "id" ) final Long id )
	{
		return super.delete( id );
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
	}

}

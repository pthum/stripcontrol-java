package de.backenddev.led.stripcontrol.quarkusbackend.controller;

import java.net.URISyntaxException;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
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
import de.backenddev.led.stripcontrol.model.LEDStrip;
import de.backenddev.led.stripcontrol.quarkusbackend.service.ModelService;

@Path ( AbstractModelController.API_BASE + "/ledstrip" )
@Produces ( MediaType.APPLICATION_JSON )
@Consumes ( MediaType.APPLICATION_JSON )
public class LEDStripController extends AbstractModelController<LEDStrip>
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDStripController.class );

	@Inject
	ModelService<LEDStrip> service;

	@Inject
	ModelService<ColorProfile> cpService;

	@POST
	@Override
	public Response create( @Valid final LEDStrip EffectConfiguration ) throws URISyntaxException
	{
		return super.create( EffectConfiguration );
	}

	@GET
	@Override
	public Iterable<LEDStrip> getAll( )
	{
		return super.getAll( );
	}

	@GET
	@Path ( "/{id}" )
	@Override
	public LEDStrip get( @PathParam ( "id" ) final Long id )
	{
		return super.get( id );
	}

	@PUT
	@Path ( "/{id}" )
	@Override
	public void update( @PathParam ( "id" ) final Long id, @Valid final LEDStrip updateObject )
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

	@GET
	@Path ( "/{id}/profile" )
	public ColorProfile getLEDStripProfile( @PathParam ( "id" ) final Long id )
	{
		final Optional<LEDStrip> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false || strip.get( ).hasProfile( ) == false )
		{
			throw new NotFoundException( );
		}
		return strip.get( ).getProfile( );
	}

	@PUT
	@Path ( "/{id}/profile" )
	public Response updateLEDStripProfile( @PathParam ( "id" ) final Long id, @Valid final ColorProfile colorProfile )
	{
		final Optional<LEDStrip> strip = this.service.getById( id );
		if ( strip.isPresent( ) == false )
		{
			throw new NotFoundException( );
		}
		final Optional<ColorProfile> dbProfile = this.cpService.getById( colorProfile.getId( ) );
		if ( dbProfile.isPresent( ) == false )
		{
			throw new NotFoundException( );
		}
		final LEDStrip updateStrip = strip.get( );
		updateStrip.setProfile( dbProfile.get( ) );
		this.service.save( updateStrip );
		return Response.ok( ).build( );
	}

	@DELETE
	@Path ( "/{id}/profile" )
	public Response deleteLEDStripProfile( @PathParam ( "id" ) final Long id )
	{
		final Optional<LEDStrip> optStrip = this.service.getById( id );
		if ( optStrip.isPresent( ) == false || optStrip.get( ).hasProfile( ) == false )
		{
			throw new NotFoundException( );
		}
		final LEDStrip strip = optStrip.get( );
		strip.setProfile( null );
		this.service.save( strip );
		return Response.noContent( ).build( );
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

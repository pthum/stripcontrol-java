package de.backenddev.led.stripcontrol.quarkusbackend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Main controller for the health endpoint
 * 
 * @author thum
 *
 *
 */
@Path ( AbstractModelController.API_BASE + "/" )
@Produces ( MediaType.APPLICATION_JSON )
@Consumes ( MediaType.APPLICATION_JSON )
public class MainController
{
	@GET
	@Path ( "/health" )
	public Map<String, String> getStatus( )
	{
		final Map<String, String> status = new HashMap<>( );
		status.put( "status", "UP" );
		return status;
	}
}

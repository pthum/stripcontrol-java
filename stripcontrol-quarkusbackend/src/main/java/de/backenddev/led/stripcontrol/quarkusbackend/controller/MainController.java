package de.backenddev.led.stripcontrol.quarkusbackend.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main controller for the health endpoint
 * 
 * @author thum
 *
 *
 */
@RestController
@RequestMapping ( AbstractModelController.API_BASE + "/" )
public class MainController
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDStripController.class );

	@GetMapping ( "/health" )
	public ResponseEntity<Map<String, String>> getLEDStripProfile( )
	{
		final Map<String, String> status = new HashMap<>( );
		status.put( "status", "UP" );
		return ResponseEntity.ok( status );
	}
}

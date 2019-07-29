package de.backenddev.led.stripcontrol.javastripbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ( BackendController.API_BASE )
public class BackendController
{
	public static final String API_BASE = "/api";
	private static final Logger LOG = LoggerFactory.getLogger( BackendController.class );

	// Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
	// Required because of 'mode: history' usage in frontend routing
	@RequestMapping ( value = "{_:^(?!index\\.html|api).$}" )
	public String redirectApi( )
	{
		LOG.info( "URL entered directly into the Browser, so we need to redirect..." );
		return "forward:/";
	}
}

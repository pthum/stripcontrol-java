package de.backenddev.led.stripcontrol.springbackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.backenddev.led.stripcontrol.ledhandling.StripRegistry;

/**
 * General configuration and bean creation for the app
 * 
 * @author thum
 *
 */
@Configuration
public class SpringBackendConfiguration
{
	@Value ( "${strips.enabled}" )
	private boolean stripsEnabled;

	@Value ( "${strips.effecttime:20}" )
	private int effectTime;

	/**
	 * 
	 * @return the strip registry
	 */
	@Bean
	public StripRegistry stripRegistry( )
	{
		return new StripRegistry( this.stripsEnabled, this.effectTime );
	}
}

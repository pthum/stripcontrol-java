package de.backenddev.led.stripcontrol.springbackend;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private StripConfig config;

	/**
	 * 
	 * @return the strip registry
	 */
	@Bean
	public StripRegistry stripRegistry( )
	{
		return new StripRegistry( this.config.isEnabled( ), this.config.getEffectTime( ) );
	}
}

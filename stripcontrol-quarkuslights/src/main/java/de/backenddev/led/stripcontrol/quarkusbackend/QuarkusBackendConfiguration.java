package de.backenddev.led.stripcontrol.quarkusbackend;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.backenddev.led.stripcontrol.ledhandling.StripRegistry;

/**
 * General configuration and bean creation for the app
 * 
 * @author thum
 *
 */
@Dependent
public class QuarkusBackendConfiguration
{

	@ConfigProperty ( name = "strips.enabled" )
	boolean stripsEnabled;

	@ConfigProperty ( name = "strips.effecttime" )
	int effectTime;

	@Produces
	public StripRegistry stripRegistry( )
	{
		return new StripRegistry( this.stripsEnabled, this.effectTime );
	}

}

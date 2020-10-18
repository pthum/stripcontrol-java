package de.backenddev.led.stripcontrol.quarkusbackend;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.backenddev.led.stripcontrol.ledhandling.StripRegistry;
import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;

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

	@ConfigProperty ( name = "strips.predefinedStrips" )
	Optional<List<LEDStrip>> predefinedStrips;
	@ConfigProperty ( name = "strips.predefinedProfiles" )
	Optional<List<ColorProfile>> predefinedProfiles;

	@Produces
	public StripRegistry stripRegistry( )
	{
		return new StripRegistry( this.stripsEnabled, this.effectTime );
	}

	/**
	 * @return the predefinedStrips
	 */
	public List<LEDStrip> getPredefinedStrips( )
	{
		if ( this.predefinedStrips.isPresent( ) )
		{
			return this.predefinedStrips.get( );
		}
		return new ArrayList<>( );
	}

	/**
	 * @return the predefinedProfiles
	 */
	public List<ColorProfile> getPredefinedProfiles( )
	{
		if ( this.predefinedProfiles.isPresent( ) )
		{
			return this.predefinedProfiles.get( );
		}
		return new ArrayList<>( );

	}

}

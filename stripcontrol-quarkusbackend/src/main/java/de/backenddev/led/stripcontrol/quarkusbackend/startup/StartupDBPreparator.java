package de.backenddev.led.stripcontrol.quarkusbackend.startup;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;
import de.backenddev.led.stripcontrol.quarkusbackend.QuarkusBackendConfiguration;
import de.backenddev.led.stripcontrol.quarkusbackend.repository.ColorProfileRepository;
import de.backenddev.led.stripcontrol.quarkusbackend.repository.LEDStripRepository;

/**
 * This class is to prepare predefined objects during startup
 * 
 * @author thum
 *
 */
@ApplicationScoped
public class StartupDBPreparator
{
	private static final Logger LOG = LoggerFactory.getLogger( StartupDBPreparator.class );

	@Inject
	LEDStripRepository ledRepo;

	@Inject
	ColorProfileRepository profileRepo;
	@Inject
	QuarkusBackendConfiguration config;

	@ActivateRequestContext
	public void prepareExistingObjects( )
	{
		initColorProfiles( );
		initLEDStrips( );
		LOG.info( "Preinitialized Strips and Profiles" );
	}

	private void initColorProfiles( )
	{
		final List<ColorProfile> profiles = this.config.getPredefinedProfiles( );
		if ( profiles == null )
		{
			return;
		}
		for ( final ColorProfile profile : profiles )
		{
			if ( this.profileRepo.findByRedAndGreenAndBlueAndBrightness( profile.getRed( ), profile.getGreen( ),
					profile.getBlue( ), profile.getBrightness( ) ).isEmpty( ) )
			{
				this.profileRepo.save( profile );
				LOG.info( "Created profile {}", profile );
			}
		}
	}

	private void initLEDStrips( )
	{
		final List<LEDStrip> strips = this.config.getPredefinedStrips( );
		if ( strips == null )
		{
			return;
		}
		for ( final LEDStrip strip : strips )
		{
			final List<LEDStrip> dbStrips = this.ledRepo.findByMisoPinAndSclkPin( strip.getMisoPin( ),
					strip.getSclkPin( ) );
			if ( dbStrips.isEmpty( ) )
			{
				this.ledRepo.save( strip );
				LOG.info( "Created strip {}", strip );
			}
		}
	}
}

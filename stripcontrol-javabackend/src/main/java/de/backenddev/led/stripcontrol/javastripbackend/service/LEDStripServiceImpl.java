package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.backenddev.led.apa102.APA102Control;
import de.backenddev.led.apa102.APA102Helper;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.repository.LEDStripRepository;

@Service
public class LEDStripServiceImpl implements LEDStripService
{
	@Autowired
	private LEDStripRepository repo;

	@Value ( "${strips.enabled}" )
	private boolean stripsEnabled;

	@Override
	public LEDStrip saveLEDStrip(LEDStrip strip )
	{
		/* update the LED strip only, if this is an update */
		if ( strip != null && strip.getId( ) != null )
		{
			try
			{
				APA102Control apaStrip = Apa102Factory.createControl( strip, !stripsEnabled );
				if ( apaStrip != null )
				{
					if ( strip.isEnabled( ) )
					{
						if ( strip.getProfile( ) != null )
						{
							ColorProfile profile = strip.getProfile( );
							APA102Helper.setStripColor( apaStrip, profile.getRed( ), profile.getGreen( ),
									profile.getBlue( ), profile.getBrightness( ) );
						} else
						{
							apaStrip.clearStrip( );
						}
					} else
					{
						apaStrip.clearStrip( );
					}
				}
			} catch ( IOException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace( );
			}
		}

		return repo.save( strip );
	}

	@Override
	public void removeLEDStrip(long id )
	{
		try
		{
			Optional<LEDStrip> strip = repo.findById( id );
			if ( strip.isPresent( ) && strip.get( ).isEnabled( ) )
			{
				APA102Control apaStrip = Apa102Factory.createControl( strip.get( ), !stripsEnabled );
				if ( apaStrip != null )
				{
					apaStrip.clearStrip( );
				}
			}
		} catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}
		repo.deleteById( id );
	}

	@Override
	public Iterable<LEDStrip> getAllLEDStrips( )
	{
		return repo.findAll( );
	}

	@Override
	public Optional<LEDStrip> getById(long id )
	{
		return repo.findById( id );
	}

}

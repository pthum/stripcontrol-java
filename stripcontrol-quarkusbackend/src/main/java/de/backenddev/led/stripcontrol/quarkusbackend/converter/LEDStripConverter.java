package de.backenddev.led.stripcontrol.quarkusbackend.converter;

import org.eclipse.microprofile.config.spi.Converter;

import com.google.gson.Gson;

import de.backenddev.led.stripcontrol.model.LEDStrip;

/**
 * Converts JSON-string to ledstrips
 * 
 * @author thum
 */
public class LEDStripConverter implements Converter<LEDStrip>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3501579449180699465L;

	@Override
	public LEDStrip convert( final String value )
	{
		final Gson gson = new Gson( );
		return gson.fromJson( value, LEDStrip.class );
	}

}

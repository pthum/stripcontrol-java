package de.backenddev.led.stripcontrol.quarkusbackend.converter;

import org.eclipse.microprofile.config.spi.Converter;

import com.google.gson.Gson;

import de.backenddev.led.stripcontrol.model.ColorProfile;

/**
 * Converts JSON-string to ledstrips
 * 
 * @author thum
 */
public class ColorProfileConverter implements Converter<ColorProfile>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3501579449180699465L;

	@Override
	public ColorProfile convert( final String value )
	{
		final Gson gson = new Gson( );
		return gson.fromJson( value, ColorProfile.class );
	}

}

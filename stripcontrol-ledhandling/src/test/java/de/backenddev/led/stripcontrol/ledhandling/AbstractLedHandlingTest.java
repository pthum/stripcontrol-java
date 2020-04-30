package de.backenddev.led.stripcontrol.ledhandling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Assert;

import de.backenddev.led.apa102.APA102Strip;
import de.backenddev.led.stripcontrol.ledhandling.Apa102Meta;
import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;

public abstract class AbstractLedHandlingTest
{

	protected void checkMeta( final Apa102Meta testMeta, final int expR, final int expG, final int expB,
			final int expBrightness, final Long expProfileId, final boolean expEnabled )
	{
		assertEquals( "red", expR, testMeta.getR( ) );
		assertEquals( "green", expG, testMeta.getG( ) );
		assertEquals( "blue", expB, testMeta.getB( ) );
		assertEquals( "brightness", expBrightness, testMeta.getBrightness( ) );
		assertEquals( "enabled", expEnabled, testMeta.isEnabled( ) );
		assertEquals( "profileId", expProfileId, testMeta.getProfileId( ) );
	}

	protected void checkControl( final Apa102Meta testMeta, final int expR, final int expG, final int expB,
			final double expBrightness )
	{
		try
		{
			final Field stripField = testMeta.getClass( ).getDeclaredField( "strip" );
			stripField.setAccessible( true );
			final APA102Strip control = (APA102Strip) stripField.get( testMeta );
			assertThat( control.getPixelData( ) ).allMatch( pixel -> pixel.getRed( ) == expR );
			assertThat( control.getPixelData( ) ).allMatch( pixel -> pixel.getGreen( ) == expG );
			assertThat( control.getPixelData( ) ).allMatch( pixel -> pixel.getBlue( ) == expB );
			assertThat( control.getPixelData( ) ).allMatch( pixel -> pixel.getBrightnessPercent( ) == expBrightness );
		}
		catch ( IllegalAccessException | NoSuchFieldException e )
		{
			e.printStackTrace( );
			Assert.fail( "Failed to handle the strip field " + e.toString( ) );
		}

	}

	protected LEDStrip getStrip( final boolean enabled )
	{
		final LEDStrip strip = new LEDStrip( "Test", null, 1, 2, 1, 0 );
		return strip;
	}

	protected ColorProfile getProfile( final int r, final int g, final int b, final int brightness,
			final Long profileId )
	{
		final ColorProfile profile = new ColorProfile( r, g, b, brightness );
		profile.setId( profileId );
		return profile;
	}
}

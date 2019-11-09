package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import static org.junit.Assert.assertEquals;

import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

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
		final NoOpApa102Control control = (NoOpApa102Control) testMeta.control;
		assertEquals( "red", expR, control.red );
		assertEquals( "green", expG, control.green );
		assertEquals( "blue", expB, control.blue );
		assertEquals( "brightness", expBrightness, control.brightnessPercent, 0.1d );
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

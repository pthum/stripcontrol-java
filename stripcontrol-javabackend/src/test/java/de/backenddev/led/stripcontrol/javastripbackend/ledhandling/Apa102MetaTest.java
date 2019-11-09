package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import java.io.IOException;

import org.junit.Test;

import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

public class Apa102MetaTest extends AbstractLedHandlingTest
{

	/**
	 * Case: Create a strip (save), set profile, enable and disable
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileEnableDisable( ) throws IOException
	{
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		/* event would create meta */
		final Apa102Meta testMeta = new Apa102Meta( strip, true );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, 1L ) );
		testMeta.update( strip );

		/* metadata should be equal to strip, control shouldn't have changed */
		checkMeta( testMeta, 100, 100, 100, 100, 1L, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* enable the strip */
		strip.setEnabled( true );
		testMeta.update( strip );

		/* metadata and control should be equal to strip */
		checkMeta( testMeta, 100, 100, 100, 100, 1L, true );
		checkControl( testMeta, 100, 100, 100, 100.0d );

		/* disable the strip again */
		strip.setEnabled( false );
		testMeta.update( strip );

		/* metadata and control should be equal to strip */
		checkMeta( testMeta, 100, 100, 100, 100, 1L, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );
	}

	/**
	 * Case: Create a strip (save), set a profile and another profile
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileAndSetAnotherProfile( ) throws IOException
	{
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		/* event would create meta */
		final Apa102Meta testMeta = new Apa102Meta( strip, true );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, 1L ) );
		testMeta.update( strip );

		/* metadata should be equal to strip, control shouldn't have changed */
		checkMeta( testMeta, 100, 100, 100, 100, 1L, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set another profile */
		strip.setProfile( getProfile( 50, 50, 50, 50, 2L ) );
		testMeta.update( strip );

		/* metadata should be equal to new profile, control shouldn't have changed */
		checkMeta( testMeta, 50, 50, 50, 50, 2L, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );
	}

	/**
	 * Case: Create a strip (save), set a profile and update profile (while strip is
	 * not enabled)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileAndUpdateProfile_NotEnabled( ) throws IOException
	{
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		/* event would create meta */
		final Apa102Meta testMeta = new Apa102Meta( strip, true );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, 1L ) );
		testMeta.update( strip );

		/* change the profile and update it */
		final ColorProfile profile = strip.getProfile( );
		profile.setRed( 20 );
		testMeta.update( profile );

		/* metadata should be equal to new profile, control shouldn't have changed */
		checkMeta( testMeta, 20, 100, 100, 100, 1L, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );
	}

	/**
	 * Case: Create a strip (save), set a profile and update profile (while strip is
	 * enabled)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileAndUpdateProfile_Enabled( ) throws IOException
	{
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		/* event would create meta */
		final Apa102Meta testMeta = new Apa102Meta( strip, true );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, 1L ) );
		strip.setEnabled( true );
		testMeta.update( strip );

		/* change the profile and update it */
		final ColorProfile profile = strip.getProfile( );
		profile.setRed( 20 );
		testMeta.update( profile );

		/* metadata and control should be equal to new profile */
		checkMeta( testMeta, 20, 100, 100, 100, 1L, true );
		checkControl( testMeta, 20, 100, 100, 100.0d );

		/* update brightness */
		profile.setBrightness( 0 );
		testMeta.update( profile );

		/* metadata and control should be equal to new profile */
		checkMeta( testMeta, 20, 100, 100, 0, 1L, true );
		checkControl( testMeta, 20, 100, 100, 0.0d );
	}

	/**
	 * Case: Create a strip (save), set a profile and delete profile (while strip is
	 * enabled)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileAndDeleteProfile_Enabled( ) throws IOException
	{
		final long stripId = 1L;
		final long profileId = 2L;
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		strip.setId( stripId );
		/* event would create meta */
		final Apa102Meta testMeta = new Apa102Meta( strip, true );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, profileId ) );
		strip.setEnabled( true );
		testMeta.update( strip );

		/* delete the profile */
		testMeta.update( (ColorProfile) null );
		checkMeta( testMeta, 0, 0, 0, 0, null, true );
		checkControl( testMeta, 0, 0, 0, 0.0d );
	}

	/**
	 * Case: Create a strip (save), set a profile and delete profile (while strip is
	 * disabled)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileAndDeleteProfile_Disabled( ) throws IOException
	{
		final long stripId = 1L;
		final long profileId = 2L;
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		strip.setId( stripId );
		/* event would create meta */
		final Apa102Meta testMeta = new Apa102Meta( strip, true );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, profileId ) );
		strip.setEnabled( false );
		testMeta.update( strip );

		/* delete the profile */
		testMeta.update( (ColorProfile) null );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );
	}

	/**
	 * Case: Create a strip (save), set a profile which has no color and delete
	 * profile (while strip is
	 * enabled)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileNoColorAndDeleteProfile_Enabled( ) throws IOException
	{
		final long stripId = 1L;
		final long profileId = 2L;
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		strip.setId( stripId );
		/* event would create meta */
		final Apa102Meta testMeta = new Apa102Meta( strip, true );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 0, 0, 0, 100, profileId ) );
		strip.setEnabled( true );
		testMeta.update( strip );

		/* delete the profile */
		testMeta.update( (ColorProfile) null );
		checkMeta( testMeta, 0, 0, 0, 0, null, true );
		checkControl( testMeta, 0, 0, 0, 0.0d );
	}

	/**
	 * Case: Create a strip (save), set a profile which has no brightness and delete
	 * profile (while strip is
	 * disabled)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileNoBrightnessAndDeleteProfile_Disabled( ) throws IOException
	{
		final long stripId = 1L;
		final long profileId = 2L;
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		strip.setId( stripId );
		/* event would create meta */
		final Apa102Meta testMeta = new Apa102Meta( strip, true );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 0, profileId ) );
		strip.setEnabled( false );
		testMeta.update( strip );

		/* delete the profile */
		testMeta.update( (ColorProfile) null );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );
	}

}

package de.backenddev.led.stripcontrol.ledhandling;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;

public class StripRegistryTest extends AbstractLedHandlingTest
{
	private TestStripRegistry registry;

	@Before
	public void setupTest( )
	{
		this.registry = new TestStripRegistry( false, 0 );
	}

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
		strip.setId( 1L );
		/* event would create meta */
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, null ) );
		final Apa102Meta testMeta = this.registry.getMap( ).get( 1L );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, 1L ) );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, 1L ) );

		/* metadata should be equal to strip, control shouldn't have changed */
		checkMeta( testMeta, 100, 100, 100, 100, 1L, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* enable the strip */
		strip.setEnabled( true );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, 1L ) );

		/* metadata and control should be equal to strip */
		checkMeta( testMeta, 100, 100, 100, 100, 1L, true );
		checkControl( testMeta, 100, 100, 100, 100.0d );

		/* disable the strip again */
		strip.setEnabled( false );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, 1L ) );

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
		strip.setId( 1L );
		/* event would create meta */
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, null ) );
		final Apa102Meta testMeta = this.registry.getMap( ).get( 1L );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, 1L ) );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, 1L ) );

		/* metadata should be equal to strip, control shouldn't have changed */
		checkMeta( testMeta, 100, 100, 100, 100, 1L, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set another profile */
		strip.setProfile( getProfile( 50, 50, 50, 50, 2L ) );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, 1L ) );

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
		strip.setId( 1L );
		/* event would create meta */
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, null ) );
		final Apa102Meta testMeta = this.registry.getMap( ).get( 1L );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, 1L ) );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, 1L ) );

		/* change the profile and update it */
		final ColorProfile profile = strip.getProfile( );
		profile.setRed( 20 );
		testMeta.update( null, profile );
		this.registry.handleSaveProfile( new ProfileEvent( this, EventType.SAVE, profile, 1L ) );

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
		strip.setId( 1L );
		/* event would create meta */
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, null ) );
		final Apa102Meta testMeta = this.registry.getMap( ).get( 1L );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, 1L ) );
		strip.setEnabled( true );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, 1L ) );

		/* change the profile and update it */
		final ColorProfile profile = strip.getProfile( );
		profile.setRed( 20 );
		this.registry.handleSaveProfile( new ProfileEvent( this, EventType.SAVE, profile, 1L ) );

		/* metadata and control should be equal to new profile */
		checkMeta( testMeta, 20, 100, 100, 100, 1L, true );
		checkControl( testMeta, 20, 100, 100, 100.0d );

		/* update brightness */
		profile.setBrightness( 0 );
		this.registry.handleSaveProfile( new ProfileEvent( this, EventType.SAVE, profile, 1L ) );

		/* metadata and control should be equal to new profile */
		checkMeta( testMeta, 20, 100, 100, 0, 1L, true );
		checkControl( testMeta, 20, 100, 100, 0.0d );
	}

	/**
	 * Case: Create a strip (save), set a profile and delete strip (while strip is
	 * enabled)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileAndDeleteStrip_Enabled( ) throws IOException
	{
		final long stripId = 1L;
		final long profileId = 2L;
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		strip.setId( stripId );
		/* event would create meta */
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, null ) );
		final Apa102Meta testMeta = this.registry.getMap( ).get( stripId );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, profileId ) );
		strip.setEnabled( true );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, stripId ) );

		/* delete the strip */
		this.registry.handleDelete( new StripEvent( this, EventType.DELETE, null, stripId ) );
		final Apa102Meta shouldBeNullMeta = this.registry.getMap( ).get( stripId );
		assertNull( shouldBeNullMeta );
	}

	/**
	 * Case: Create a strip (save), set a profile and delete strip (while strip is
	 * disabled)
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCreateStripProfileAndDeleteStrip_Disabled( ) throws IOException
	{
		final long stripId = 1L;
		final long profileId = 2L;
		/* create a strip */
		final LEDStrip strip = getStrip( false );
		strip.setId( stripId );
		/* event would create meta */
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, null ) );
		final Apa102Meta testMeta = this.registry.getMap( ).get( stripId );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, profileId ) );
		strip.setEnabled( false );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, stripId ) );

		/* delete the strip */
		this.registry.handleDelete( new StripEvent( this, EventType.DELETE, null, stripId ) );
		final Apa102Meta shouldBeNullMeta = this.registry.getMap( ).get( stripId );
		assertNull( shouldBeNullMeta );
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
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, null ) );
		final Apa102Meta testMeta = this.registry.getMap( ).get( stripId );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, profileId ) );
		strip.setEnabled( true );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, stripId ) );

		/* delete the profile */
		this.registry.handleDeleteProfile( new ProfileEvent( this, EventType.DELETE, null, profileId ) );
		final Apa102Meta shouldBeNullMeta = this.registry.getMap( ).get( stripId );
		assertNotNull( shouldBeNullMeta );
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
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, null ) );
		final Apa102Meta testMeta = this.registry.getMap( ).get( stripId );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );

		/* set a profile */
		strip.setProfile( getProfile( 100, 100, 100, 100, profileId ) );
		strip.setEnabled( false );
		this.registry.handleSave( new StripEvent( this, EventType.SAVE, strip, stripId ) );

		/* delete the profile */
		this.registry.handleDeleteProfile( new ProfileEvent( this, EventType.DELETE, null, profileId ) );
		final Apa102Meta shouldBeNullMeta = this.registry.getMap( ).get( stripId );
		assertNotNull( shouldBeNullMeta );
		checkMeta( testMeta, 0, 0, 0, 0, null, false );
		checkControl( testMeta, 0, 0, 0, 0.0d );
	}

	public static class TestStripRegistry extends StripRegistry
	{
		public TestStripRegistry( final boolean stripsEnabled, final int effectTime )
		{
			super( stripsEnabled, effectTime );
		}

		public Map<Long, Apa102Meta> getMap( )
		{
			return this.map;
		}
	}

	public static class Event<T> implements IEvent<T>
	{
		private final EventType type;
		private final Long id;
		private final T state;

		public Event( final EventType type, final Long id, final T state )
		{
			this.type = type;
			this.id = id;
			this.state = state;
		}

		@Override
		public EventType getType( )
		{
			return this.type;
		}

		@Override
		public Long getId( )
		{
			return this.id;
		}

		@Override
		public T getState( )
		{
			return this.state;
		}

	}

	public static class StripEvent extends Event<LEDStrip>
	{

		public StripEvent( final Object source, final EventType type, final LEDStrip state, final Long id )
		{
			super( type, id, state );
		}

	}

	public static class ProfileEvent extends Event<ColorProfile>
	{

		public ProfileEvent( final Object source, final EventType type, final ColorProfile state, final Long id )
		{
			super( type, id, state );
		}

	}
}

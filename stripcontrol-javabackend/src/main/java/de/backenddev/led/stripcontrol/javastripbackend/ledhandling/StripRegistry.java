package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.backenddev.led.stripcontrol.javastripbackend.controller.LEDStripController;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

/**
 * This class is intended to have an overview over the registered strips and
 * control them
 * 
 * @author thum
 *
 */
@Component
public class StripRegistry
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDStripController.class );

	Map<Long, Apa102Meta> map;

	@Value ( "${strips.enabled}" )
	private boolean stripsEnabled;

	public StripRegistry( )
	{
		this.map = new HashMap<>( );
	}

	@EventListener
	public void handleStripEvent( final StripEvent event )
	{
		LOG.debug( event.toString( ) );
		if ( event != null )
		{
			switch ( event.getType( ) )
			{
				case SAVE:
					handleSave( event );
					break;
				case DELETE:
					handleDelete( event );
					break;
				default:
					break;
			}
		}
	}

	@EventListener
	public void handleProfileEvent( final ProfileEvent event )
	{
		LOG.debug( event.toString( ) );
		if ( event != null )
		{
			switch ( event.getType( ) )
			{
				case SAVE:
					handleSave( event );
					break;
				case DELETE:
					handleDelete( event );
					break;
				default:
					break;
			}
		}
	}

	private void handleSave( final StripEvent event )
	{
		final Apa102Meta control = getOrCreate( event.getStripId( ), event.getStrip( ) );
		final LEDStrip strip = event.getStrip( );
		try
		{
			control.update( strip );
		}
		catch ( final IOException e )
		{
			LOG.error( "Couldn't update state", e );
		}
	}

	private void handleDelete( final StripEvent event )
	{
		try
		{
			final Apa102Meta apaStrip = this.map.remove( event.getStripId( ) );
			if ( apaStrip != null )
			{
				apaStrip.shutdown( );
			}
		}
		catch ( final IOException e )
		{
			LOG.error( "Couldn't clear state", e );
		}
	}

	private void handleSave( final ProfileEvent event )
	{
		if ( event == null || event.getProfileId( ) == null )
		{
			/* no action if freshly saved profile */
			return;
		}
		final ColorProfile profile = event.getProfile( );
		final List<Apa102Meta> matching = this.map.values( ).stream( ).filter( meta -> meta.getProfileId( ) != null
				&& profile != null && meta.getProfileId( ).equals( profile.getId( ) ) ).collect( Collectors.toList( ) );
		for ( final Apa102Meta meta : matching )
		{
			try
			{
				if ( meta.isEnabled( ) )
				{
					meta.update( profile );
				}
			}
			catch ( final IOException e )
			{
				LOG.error( "Couldn't clear state", e );
			}
		}
	}

	private void handleDelete( final ProfileEvent event )
	{
		final Long profileId = event.getProfileId( );
		if ( profileId == null )
		{
			return;
		}

		final List<Apa102Meta> matching = this.map.values( ).stream( )
				.filter( meta -> meta.getProfileId( ) != null && meta.getProfileId( ).equals( profileId ) )
				.collect( Collectors.toList( ) );
		for ( final Apa102Meta meta : matching )
		{
			try
			{
				meta.update( (ColorProfile) null );
			}
			catch ( final IOException e )
			{
				LOG.error( "Couldn't clear state", e );
			}
		}
	}

	private Apa102Meta getOrCreate( final Long stripId, final LEDStrip strip )
	{
		Apa102Meta control = null;
		if ( stripId != null )
		{
			control = this.map.get( stripId );
		}
		if ( control == null )
		{
			try
			{
				control = new Apa102Meta( strip, !this.stripsEnabled );
				this.map.put( strip.getId( ), control );
			}
			catch ( final IOException e )
			{
				LOG.debug( "Control creation failed", e );
			}
		}
		return control;
	}

}

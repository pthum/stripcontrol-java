package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import de.backenddev.led.stripcontrol.javastripbackend.controller.LEDStripController;
import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import io.quarkus.vertx.ConsumeEvent;

/**
 * This class is intended to have an overview over the registered strips and
 * control them
 * 
 * @author thum
 *
 */
// @Component
@ApplicationScoped
public class StripRegistry
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDStripController.class );

	final Map<Long, Apa102Meta> map;

	@Value ( "${strips.enabled}" )
	boolean stripsEnabled;

	@Value ( "${strips.effecttime:20}" )
	int effectTime;

	public StripRegistry( )
	{
		this.map = new HashMap<>( );
	}

	// @EventListener
	@ConsumeEvent ( value = "StripEvent" )
	public void handleStripEvent( final StripEvent event )
	{
		if ( event != null )
		{
			LOG.debug( event.toString( ) );
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

	// @EventListener
	@ConsumeEvent ( value = "ProfileEvent" )
	public void handleProfileEvent( final ProfileEvent event )
	{
		if ( event != null )
		{
			LOG.debug( event.toString( ) );
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
		final Apa102Meta control = getOrCreate( event.getId( ), event.getState( ) );
		final LEDStrip strip = event.getState( );
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
			final Apa102Meta apaStrip = this.map.remove( event.getId( ) );
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
		if ( event.getId( ) == null )
		{
			/* no action if freshly saved profile */
			return;
		}
		final ColorProfile profile = event.getState( );
		final List<Apa102Meta> matching = getMetaWithProfileId( profile == null ? null : profile.getId( ) );

		matching.stream( ).filter( meta -> meta.isEnabled( ) ).forEach( meta ->
		{
			try
			{
				meta.update( profile );
			}
			catch ( final IOException e )
			{
				LOG.error( "Couldn't clear state", e );
			}
		} );
	}

	private void handleDelete( final ProfileEvent event )
	{
		final Long profileId = event.getId( );
		final List<Apa102Meta> matching = getMetaWithProfileId( profileId );
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

	private List<Apa102Meta> getMetaWithProfileId( final Long profileId )
	{
		if ( profileId == null )
		{
			return Collections.emptyList( );
		}
		final List<Apa102Meta> matching = this.map.values( ).stream( )
				.filter( meta -> meta.getProfileId( ) != null && meta.getProfileId( ).equals( profileId ) )
				.collect( Collectors.toList( ) );
		return matching;
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
				control = new Apa102Meta( strip, !this.stripsEnabled, this.effectTime );
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

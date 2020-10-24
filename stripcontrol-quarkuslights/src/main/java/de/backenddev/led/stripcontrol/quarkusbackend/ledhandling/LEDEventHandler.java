package de.backenddev.led.stripcontrol.quarkusbackend.ledhandling;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.backenddev.led.stripcontrol.ledhandling.StripRegistry;

/**
 * This class is intended to have an overview over the registered strips and
 * control them
 * 
 * @author thum
 *
 */
@ApplicationScoped
public class LEDEventHandler
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDEventHandler.class );

	@Autowired
	StripRegistry registry;

	public LEDEventHandler( )
	{
	}

	@Incoming ( "ledstrip" )
	public void handleStripEvent( final byte[] eventString )
	{
		final StripEvent event = Event.fromJson( eventString, StripEvent.class );
		LOG.info( "Got stripevent {}", event );
		if ( event != null )
		{
			LOG.debug( event.toString( ) );
			switch ( event.getType( ) )
			{
				case SAVE:
					this.registry.handleSave( event );
					break;
				case DELETE:
					this.registry.handleDelete( event );
					break;
				default:
					break;
			}
		}
	}

	@Incoming ( "profile" )
	public void handleProfileEvent( final byte[] eventString )
	{
		LOG.info( "Got profileevents" );
		final ProfileEvent event = Event.fromJson( eventString, ProfileEvent.class );
		if ( event != null )
		{
			LOG.debug( event.toString( ) );
			switch ( event.getType( ) )
			{
				case SAVE:
					this.registry.handleSaveProfile( event );
					break;
				case DELETE:
					this.registry.handleDeleteProfile( event );
					break;
				default:
					break;
			}
		}
	}

}

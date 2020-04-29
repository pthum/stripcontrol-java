package de.backenddev.led.stripcontrol.quarkusbackend.ledhandling;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.quarkus.vertx.ConsumeEvent;

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

	@ConsumeEvent ( value = "StripEvent" )
	public void handleStripEvent( final StripEvent event )
	{
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

	@ConsumeEvent ( value = "ProfileEvent" )
	public void handleProfileEvent( final ProfileEvent event )
	{
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

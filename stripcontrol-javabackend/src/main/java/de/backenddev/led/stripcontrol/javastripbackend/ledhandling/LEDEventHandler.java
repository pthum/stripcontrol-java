package de.backenddev.led.stripcontrol.javastripbackend.ledhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;

/**
 * This class is intended to have an overview over the registered strips and
 * control them
 * 
 * @author thum
 *
 */
@Component
public class LEDEventHandler
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDEventHandler.class );

	@Autowired
	StripRegistry registry;

	public LEDEventHandler( )
	{
	}

	@EventListener
	public void handleStripEvent( final IEvent<LEDStrip> event )
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

	@EventListener
	public void handleProfileEvent( final IEvent<ColorProfile> event )
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

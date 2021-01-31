package de.backenddev.led.stripcontrol.quarkusbackend.aqmp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.ProfileEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.StripEvent;

@ApplicationScoped
public class LEDSender
{
	private static final Logger LOG = LoggerFactory.getLogger( LEDSender.class );
	@Inject
	@Channel ( "profile" )
	Emitter<String> emitter;
	@Inject
	@Channel ( "ledstrip" )
	Emitter<String> stripEmitter;

	public void send( final ProfileEvent event )
	{
		try
		{
			this.emitter.send( event.toString( ) );
			LOG.debug( "Sending event {}", event );
		}
		catch ( final Exception e )
		{
			LOG.error( "Failed to send ProfileEvent", e );
		}
	}

	public void send( final StripEvent event )
	{
		try
		{
			this.stripEmitter.send( event.toString( ) );
			LOG.debug( "Sending event {}", event );
		}
		catch ( final Exception e )
		{
			LOG.error( "Failed to send StripEvent", e );
		}
	}
}

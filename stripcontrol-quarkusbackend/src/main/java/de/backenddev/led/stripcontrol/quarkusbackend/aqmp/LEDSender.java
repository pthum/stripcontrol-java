package de.backenddev.led.stripcontrol.quarkusbackend.aqmp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.ProfileEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.StripEvent;

@ApplicationScoped
public class LEDSender
{

	@Inject
	@Channel ( "profile" )
	Emitter<ProfileEvent> emitter;
	@Inject
	@Channel ( "ledstrip" )
	Emitter<StripEvent> stripEmitter;

	public void send( final ProfileEvent event )
	{
		this.emitter.send( event );
	}

	public void send( final StripEvent event )
	{
		this.stripEmitter.send( event );
	}
}

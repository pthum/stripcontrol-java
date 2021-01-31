package de.backenddev.led.stripcontrol.quarkusbackend.service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.backenddev.led.stripcontrol.ledhandling.EventType;
import de.backenddev.led.stripcontrol.model.LEDStrip;
import de.backenddev.led.stripcontrol.quarkusbackend.aqmp.LEDSender;
import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.StripEvent;
import de.backenddev.led.stripcontrol.quarkusbackend.repository.LEDStripRepository;

@ApplicationScoped
public class LEDStripServiceImpl implements ModelService<LEDStrip>
{
	@Inject
	LEDStripRepository repo;

	@Inject
	LEDSender ledSender;

	@Override
	public LEDStrip save( final LEDStrip strip )
	{
		final Long idBeforeSave = strip != null ? strip.getId( ) : null;
		final LEDStrip result = this.repo.save( strip );
		this.ledSender.send( new StripEvent( EventType.SAVE, result, idBeforeSave ) );
		return result;
	}

	public LEDStrip toggleStrip( final Long id, final boolean state )
	{
		final Optional<LEDStrip> optStrip = this.repo.findById( id );
		if ( optStrip.isPresent( ) == false )
		{
			return null;
		}
		final LEDStrip oldStrip = optStrip.get( );
		oldStrip.setEnabled( state );
		final LEDStrip result = this.repo.save( oldStrip );
		this.ledSender.send( new StripEvent( EventType.SAVE, result, id ) );
		return result;
	}

	@Override
	public void remove( final long id )
	{
		this.repo.deleteById( id );
		this.ledSender.send( new StripEvent( EventType.DELETE, null, id ) );
	}

	@Override
	public Iterable<LEDStrip> getAll( )
	{
		return this.repo.findAll( );
	}

	@Override
	public Optional<LEDStrip> getById( final long id )
	{
		return this.repo.findById( id );
	}

}

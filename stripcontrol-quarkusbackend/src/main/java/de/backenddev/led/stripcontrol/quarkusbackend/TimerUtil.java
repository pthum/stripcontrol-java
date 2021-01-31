package de.backenddev.led.stripcontrol.quarkusbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Timer utility to track time
 * 
 * @author thum
 *
 */
public class TimerUtil
{
	private static final Logger LOG = LoggerFactory.getLogger( TimerUtil.class );
	private static final double NS_TO_MS_FACTOR = 1000000.0d;
	private long start;
	private CharSequence name;

	/**
	 * Initialize a new timer
	 * 
	 * @param start
	 * @param name
	 */
	public TimerUtil( final CharSequence name )
	{
		this.start = System.nanoTime( );
		this.name = name;
	}

	/**
	 * @return the start
	 */
	public long getStart( )
	{
		return this.start;
	}

	/**
	 * @return the name
	 */
	public CharSequence getName( )
	{
		return this.name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName( final CharSequence name )
	{
		this.name = name;
	}

	public void logAndUpdate( final CharSequence action )
	{
		logTimeSpan( action );
		updateStart( );
	}

	public void logTimeSpan( final CharSequence action )
	{
		if ( LOG.isTraceEnabled( ) )
		{
			LOG.trace( "{}: {} took {} ms", this.name, action, ( System.nanoTime( ) - this.start ) / NS_TO_MS_FACTOR );
		}
	}

	public void updateStart( )
	{
		this.start = System.nanoTime( );
	}

}

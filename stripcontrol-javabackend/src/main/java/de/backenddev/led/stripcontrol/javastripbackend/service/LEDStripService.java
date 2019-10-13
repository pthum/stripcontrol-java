package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.util.Optional;

import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

/**
 * Handles the control of and actions on an LED strip
 * 
 * @author thum
 *
 */
public interface LEDStripService
{
	/**
	 * handles the creation and update of an LED strip<br/>
	 * this contains also the control over the configured strip
	 * 
	 * @param strip
	 *            the led strip
	 * @return the created LED strip
	 */
	public LEDStrip saveLEDStrip(LEDStrip strip );

	/**
	 * handles the deletion of an LED strip <br/>
	 * may also control the strip
	 * 
	 * @param id
	 */
	public void removeLEDStrip(long id );

	/**
	 * loads the strip by its id
	 * 
	 * @param id
	 * @return
	 */
	public Optional<LEDStrip> getById(long id );

	/**
	 * retrieves all LED strips from the database
	 * 
	 * @return
	 */
	public Iterable<LEDStrip> getAllLEDStrips( );
}

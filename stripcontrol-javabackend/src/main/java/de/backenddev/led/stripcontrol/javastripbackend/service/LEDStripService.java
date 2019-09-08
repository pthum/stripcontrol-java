package de.backenddev.led.stripcontrol.javastripbackend.service;

import java.util.Optional;

import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;

public interface LEDStripService
{
	public LEDStrip saveLEDStrip(LEDStrip profile );

	public void removeLEDStrip(long id );

	public Optional<LEDStrip> getById(long id );

	public Iterable<LEDStrip> getAllLEDStrips( );
}

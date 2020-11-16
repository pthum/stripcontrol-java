package de.backenddev.led.stripcontrol.springbackend;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;

@Configuration
@ConfigurationProperties ( prefix = "strips" )
public class StripConfig
{
	private boolean enabled;

	private int effectTime;

	private List<LEDStrip> predefinedStrips;

	private List<ColorProfile> predefinedProfiles;

	/**
	 * @return the enabled
	 */
	public boolean isEnabled( )
	{
		return this.enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled( final boolean enabled )
	{
		this.enabled = enabled;
	}

	/**
	 * @return the effectTime
	 */
	public int getEffectTime( )
	{
		return this.effectTime;
	}

	/**
	 * @param effectTime
	 *            the effectTime to set
	 */
	public void setEffectTime( final int effectTime )
	{
		this.effectTime = effectTime;
	}

	/**
	 * @return the predefinedStrips
	 */
	public List<LEDStrip> getPredefinedStrips( )
	{
		return this.predefinedStrips;
	}

	/**
	 * @param predefinedStrips
	 *            the predefinedStrips to set
	 */
	public void setPredefinedStrips( final List<LEDStrip> predefinedStrips )
	{
		this.predefinedStrips = predefinedStrips;
	}

	/**
	 * @return the predefinedProfiles
	 */
	public List<ColorProfile> getPredefinedProfiles( )
	{
		return this.predefinedProfiles;
	}

	/**
	 * @param predefinedProfiles
	 *            the predefinedProfiles to set
	 */
	public void setPredefinedProfiles( final List<ColorProfile> predefinedProfiles )
	{
		this.predefinedProfiles = predefinedProfiles;
	}

}

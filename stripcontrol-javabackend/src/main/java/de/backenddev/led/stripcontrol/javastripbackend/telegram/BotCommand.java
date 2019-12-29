package de.backenddev.led.stripcontrol.javastripbackend.telegram;

import io.micrometer.core.instrument.util.StringUtils;

public enum BotCommand
{
	LED_ON( "/ledon", "Set an LED Strip to on, use: /ledon <id>", "Turning on LED with id %s" ),

	LED_OFF( "/ledoff", "Set an LED Strip to off, use: /ledoff <id>", "Turning off LED with id %s" ),

	GET_STRIPS( "/getstrips", "Returns all LED Strips, use: /getstrips", " All LED Strips: \n %s" ),

	HELP( "/help", "Prints all commands", "All available commands:\n %s" );

	private final String commandStart;
	private final String description;
	private final String responseText;

	private BotCommand( final String commandStart, final String description, final String responseText )
	{
		this.commandStart = commandStart;
		this.description = description;
		this.responseText = responseText;
	}

	/**
	 * @return the commandStart
	 */
	public String getCommandStart( )
	{
		return this.commandStart;
	}

	/**
	 * @return the description
	 */
	public String getDescription( )
	{
		return this.description;
	}

	public String getResponseText( final String id )
	{
		return String.format( this.responseText, id );
	}

	public static BotCommand getValueFromMessage( final String message )
	{
		if ( StringUtils.isBlank( message ) )
		{
			return null;
		}
		for ( final BotCommand command : BotCommand.values( ) )
		{
			if ( message.toLowerCase( ).startsWith( command.getCommandStart( ).toLowerCase( ) ) )
			{
				return command;
			}
		}

		return null;
	}
}

package de.backenddev.led.stripcontrol.javastripbackend.telegram;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.service.LEDStripServiceImpl;

public class BotUpdateListener implements UpdatesListener
{
	private static final Logger LOG = LoggerFactory.getLogger( BotUpdateListener.class );
	private final Set<String> allowedUserIds;
	private final TelegramBot bot;
	private final LEDStripServiceImpl ledService;

	public BotUpdateListener( final TelegramBot bot, final LEDStripServiceImpl ledService,
			final Set<String> allowedUserIds )
	{
		this.bot = bot;
		this.allowedUserIds = allowedUserIds;
		this.ledService = ledService;
	}

	@Override
	public int process( final List<Update> updates )
	{
		for ( final Update update : updates )
		{
			LOG.trace( update.toString( ) );
			LOG.debug( "allowed Users list: {}", this.allowedUserIds );
			final Message msg = update.message( );
			if ( this.allowedUserIds.contains( msg.from( ).id( ).toString( ) ) == false || msg == null
					|| msg.from( ) == null || msg.from( ).id( ) == null || StringUtils.isBlank( msg.text( ) ) )
			{
				/* skip if message is null (may've been edited. we only accept new messages) */
				continue;
			}
			final BotCommand command = BotCommand.getValueFromMessage( msg.text( ) );

			if ( command == null )
			{
				LOG.info( "No Command found" );
				continue;
			}
			boolean isSendResponse = true;
			String text = "";
			switch ( command )
			{
				case LED_ON:
				case LED_OFF:
					final String[] txtSplit = msg.text( ).split( " " );
					if ( txtSplit.length != 2 || StringUtils.isNumeric( txtSplit[1] ) == false )
					{
						isSendResponse = false;
						break;
					}

					LOG.info( "Turning on LED with id {}", txtSplit[1] );
					final boolean turnOn = command.equals( BotCommand.LED_ON );
					this.ledService.toggleStrip( Long.parseLong( txtSplit[1] ), turnOn );
					text = txtSplit[1];
					break;
				case GET_STRIPS:
					LOG.debug( "Get all strips" );
					final Iterable<LEDStrip> sourceIterator = this.ledService.getAll( );
					text = StreamSupport.stream( sourceIterator.spliterator( ), false )
							.map( strip -> "ID: " + strip.getId( ) + " Name: " + strip.getName( ) + "\n" )
							.collect( Collectors.joining( ) );
					break;
				case HELP:
					text = Stream.of( BotCommand.values( ) )
							.map( cmd -> cmd.getCommandStart( ) + " " + cmd.getDescription( ) + "\n" )
							.collect( Collectors.joining( ) );
					LOG.debug( "Get Help {}", text );
					break;
				default:
					isSendResponse = false;
					break;
			}
			if ( isSendResponse )
			{
				sendResponse( msg, text, command );
			}

		}
		return UpdatesListener.CONFIRMED_UPDATES_ALL;
	}

	private void sendResponse( final Message msg, final String formatText, final BotCommand command )
	{
		final SendMessage resp = buildResponse( msg.chat( ).id( ), formatText, command );
		final SendResponse response = this.bot.execute( resp );
		LOG.trace( response.toString( ) );
	}

	private SendMessage buildResponse( final Long chatId, final String messageAppend, final BotCommand command )
	{
		return new SendMessage( chatId, command.getResponseText( messageAppend ) ).parseMode( ParseMode.Markdown )
				.disableWebPagePreview( true ).disableNotification( true );
	}

}

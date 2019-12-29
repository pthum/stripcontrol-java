package de.backenddev.led.stripcontrol.javastripbackend;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import de.backenddev.led.stripcontrol.javastripbackend.model.LEDStrip;
import de.backenddev.led.stripcontrol.javastripbackend.service.LEDStripServiceImpl;
import de.backenddev.led.stripcontrol.javastripbackend.telegram.BotCommand;
import io.micrometer.core.instrument.util.StringUtils;

@Configuration
public class TelegramConfiguration
{
	private static final Logger LOG = LoggerFactory.getLogger( TelegramConfiguration.class );
	@Value ( "${strips.telegram.botkey}" )
	private String botkey;

	@Value ( "${strips.telegram.allowedUserIds}" )
	private String allowedUserIds;

	@Autowired
	private LEDStripServiceImpl ledService;

	@Bean
	public TelegramBot telegramBot( )
	{
		LOG.info( "botkey: " + this.botkey );
		final TelegramBot bot = new TelegramBot( this.botkey );
		final GetUpdates getUpdates = new GetUpdates( ).limit( 100 ).offset( 0 ).timeout( 10 );
		final Set<String> allowedUserList = Stream.of( this.allowedUserIds.split( "," ) )
				.collect( Collectors.toSet( ) );

		bot.setUpdatesListener( new UpdatesListener( )
		{
			@Override
			public int process( final List<Update> updates )
			{
				for ( final Update update : updates )
				{
					LOG.trace( update.toString( ) );
					LOG.debug( "allowed Users list: " + allowedUserList );
					if ( update.message( ) == null )
					{
						/* skip if message is null (may've been edited. we only accept new messages) */
						continue;
					}
					if ( allowedUserList.contains( update.message( ).from( ).id( ).toString( ) ) )
					{
						final Message msg = update.message( );

						if ( StringUtils.isBlank( msg.text( ) ) )
						{
							continue;
						}

						// Bot logic Here
						final BotCommand command = BotCommand.getValueFromMessage( msg.text( ) );
						final String[] txtSplit = msg.text( ).split( " " );

						if ( command == null )
						{
							LOG.info( "No Command found" );
							continue;
						}

						switch ( command )
						{
							case LED_ON:
								LOG.info( "Turning on LED with id " + txtSplit[1] );

								sendResponse( bot, msg, txtSplit[1], command );
								break;
							case LED_OFF:
								LOG.info( "Turning on LED with id " + txtSplit[1] );
								sendResponse( bot, msg, txtSplit[1], command );
								break;
							case GET_STRIPS:
								LOG.debug( "Get all strips" );
								final Iterable<LEDStrip> sourceIterator = TelegramConfiguration.this.ledService
										.getAll( );
								final String allStrips = StreamSupport.stream( sourceIterator.spliterator( ), false )
										.map( strip -> "ID: " + strip.getId( ) + " Name: " + strip.getName( ) + "\n" )
										.collect( Collectors.joining( ) );
								sendResponse( bot, msg, allStrips, command );
								break;
							case HELP:
								final String text = Stream.of( BotCommand.values( ) )
										.map( cmd -> cmd.getCommandStart( ) + " " + cmd.getDescription( ) + "\n" )
										.collect( Collectors.joining( ) );
								LOG.debug( "Get Help " + text );
								sendResponse( bot, msg, text, command );
								break;
							default:
								break;
						}

					}
				}
				return UpdatesListener.CONFIRMED_UPDATES_ALL;
			}

			public void sendResponse( final TelegramBot bot, final Message msg, final String formatText,
					final BotCommand command )
			{
				final SendMessage resp = buildResponse( msg.chat( ).id( ), msg.messageId( ), formatText, command );
				final SendResponse response = bot.execute( resp );
				LOG.trace( response.toString( ) );
			}

			public SendMessage buildResponse( final Long chatId, final Integer messageId, final String ledId,
					final BotCommand command )
			{
				final SendMessage request = new SendMessage( chatId, command.getResponseText( ledId ) )
						.parseMode( ParseMode.Markdown ).disableWebPagePreview( true ).disableNotification( true );
				return request;
			}

		} );
		return bot;
	}

}

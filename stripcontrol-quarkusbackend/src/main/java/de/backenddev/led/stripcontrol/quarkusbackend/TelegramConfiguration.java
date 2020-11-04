package de.backenddev.led.stripcontrol.quarkusbackend;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetUpdates;

import de.backenddev.led.stripcontrol.quarkusbackend.service.LEDStripServiceImpl;
import de.backenddev.led.stripcontrol.quarkusbackend.telegram.BotUpdateListener;

// Not Implemented ATM
public class TelegramConfiguration
{
	private static final Logger LOG = LoggerFactory.getLogger( TelegramConfiguration.class );

	@ConfigProperty ( name = "strips.telegram.botkey" )
	private String botkey;

	@ConfigProperty ( name = "strips.telegram.allowedUserIds" )
	private String allowedUserIds;

	@Inject
	private LEDStripServiceImpl ledService;

	public TelegramBot telegramBot( )
	{
		LOG.info( "botkey: " + this.botkey );
		final TelegramBot bot = new TelegramBot( this.botkey );
		new GetUpdates( ).limit( 100 ).offset( 0 ).timeout( 10 );
		final Set<String> allowedUserList = Stream.of( this.allowedUserIds.split( "," ) )
				.collect( Collectors.toSet( ) );
		bot.setUpdatesListener( new BotUpdateListener( bot, this.ledService, allowedUserList ) );
		return bot;
	}

}

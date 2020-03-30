package de.backenddev.led.stripcontrol.javastripbackend;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetUpdates;

import de.backenddev.led.stripcontrol.javastripbackend.service.LEDStripServiceImpl;
import de.backenddev.led.stripcontrol.javastripbackend.telegram.BotUpdateListener;

@ConditionalOnProperty ( name = "strips.telegram.botkey" )
@Configuration
public class TelegramConfiguration
{
	private static final Logger LOG = LoggerFactory.getLogger( TelegramConfiguration.class );
	@Value ( "${strips.telegram.botkey:}" )
	private String botkey;

	@Value ( "${strips.telegram.allowedUserIds:}" )
	private String allowedUserIds;

	@Autowired
	private LEDStripServiceImpl ledService;

	@Bean
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

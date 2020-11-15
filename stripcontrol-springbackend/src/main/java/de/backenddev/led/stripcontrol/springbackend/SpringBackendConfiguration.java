package de.backenddev.led.stripcontrol.springbackend;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import de.backenddev.led.stripcontrol.ledhandling.StripRegistry;

/**
 * General configuration and bean creation for the app
 * 
 * @author thum
 *
 */
@Configuration
public class SpringBackendConfiguration
{
	@Autowired
	private StripConfig config;

	@Autowired
	Environment env;

	/**
	 * 
	 * @return the strip registry
	 */
	@Bean
	public StripRegistry stripRegistry( )
	{
		return new StripRegistry( this.config.isEnabled( ), this.config.getEffectTime( ) );
	}

	@Bean
	public DataSource dataSource( )
	{
		final DriverManagerDataSource dataSource = new DriverManagerDataSource( );
		dataSource.setDriverClassName( this.env.getProperty( "spring.datasource.driverClassName" ) );
		dataSource.setUrl( this.env.getProperty( "spring.datasource.url" ) );
		dataSource.setUsername( this.env.getProperty( "spring.datasource.user" ) );
		dataSource.setPassword( this.env.getProperty( "spring.datasource.password" ) );
		return dataSource;
	}
}

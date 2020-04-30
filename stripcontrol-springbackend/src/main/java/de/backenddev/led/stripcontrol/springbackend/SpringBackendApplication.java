package de.backenddev.led.stripcontrol.springbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication ( scanBasePackages = "com.github.lwaddicor.springstartupanalysis,de.backenddev" )
@EntityScan ( basePackages = "de.backenddev.led.stripcontrol.model" )
public class SpringBackendApplication
{
	public static final String API_BASE = "/api";

	public static void main( final String[] args )
	{
		final SpringApplication app = new SpringApplication( SpringBackendApplication.class );
		app.run( args );
	}

}

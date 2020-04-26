package de.backenddev.led.stripcontrol.javastripbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication ( scanBasePackages = "com.github.lwaddicor.springstartupanalysis,de.backenddev" )
@EntityScan ( basePackages = "de.backenddev.led.stripcontrol.javastripbackend.model" )
public class JavastripBackendApplication
{
	public static final String API_BASE = "/api";

	public static void main( final String[] args )
	{
		final SpringApplication app = new SpringApplication( JavastripBackendApplication.class );
		// app.addInitializers( new ConditionEvaluationReportLoggingListener2(
		// LogLevel.INFO ) );
		app.run( args );
	}

}

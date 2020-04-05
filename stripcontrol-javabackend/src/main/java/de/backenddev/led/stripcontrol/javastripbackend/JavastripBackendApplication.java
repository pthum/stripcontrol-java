package de.backenddev.led.stripcontrol.javastripbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;

@SpringBootApplication ( exclude = { OAuth2ResourceServerAutoConfiguration.class, JmxAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, JtaAutoConfiguration.class, } )
public class JavastripBackendApplication
{
	public static final String API_BASE = "/api";

	public static void main(String[] args )
	{
		SpringApplication.run( JavastripBackendApplication.class, args );
	}

}

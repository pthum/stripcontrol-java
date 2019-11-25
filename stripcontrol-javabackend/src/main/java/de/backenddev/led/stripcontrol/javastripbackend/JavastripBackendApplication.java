package de.backenddev.led.stripcontrol.javastripbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavastripBackendApplication
{
	public static final String API_BASE = "/api";

	public static void main(String[] args )
	{
		SpringApplication.run( JavastripBackendApplication.class, args );
	}

}

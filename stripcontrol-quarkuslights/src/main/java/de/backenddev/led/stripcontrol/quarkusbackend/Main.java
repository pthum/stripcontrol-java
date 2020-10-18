package de.backenddev.led.stripcontrol.quarkusbackend;

import javax.inject.Inject;

import de.backenddev.led.stripcontrol.quarkusbackend.ledhandling.LEDEventHandler;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main
{
	public static void main( final String... args )
	{
		Quarkus.run( MyApp.class, args );
	}

	public static class MyApp implements QuarkusApplication
	{
		@Inject
		LEDEventHandler handler;

		@Override
		public int run( final String... args ) throws Exception
		{
			Quarkus.waitForExit( );
			return 0;
		}
	}
}

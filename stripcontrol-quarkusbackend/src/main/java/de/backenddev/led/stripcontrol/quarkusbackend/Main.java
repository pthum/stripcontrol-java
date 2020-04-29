package de.backenddev.led.stripcontrol.quarkusbackend;

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

		@Override
		public int run( final String... args ) throws Exception
		{
			System.out.println( "Do startup logic here" );
			Quarkus.waitForExit( );
			return 0;
		}
	}
}

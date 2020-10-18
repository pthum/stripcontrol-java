package de.backenddev.led.stripcontrol.quarkusbackend;

import javax.inject.Inject;

import de.backenddev.led.stripcontrol.quarkusbackend.startup.StartupDBPreparator;
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
		StartupDBPreparator dbPreparator;

		@Override
		public int run( final String... args ) throws Exception
		{
			this.dbPreparator.prepareExistingObjects( );
			Quarkus.waitForExit( );
			return 0;
		}
	}
}

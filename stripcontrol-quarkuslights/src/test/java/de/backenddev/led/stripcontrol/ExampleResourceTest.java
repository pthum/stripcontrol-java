package de.backenddev.led.stripcontrol;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ExampleResourceTest
{

	@Test
	public void testHelloEndpoint( )
	{
		given( ).when( ).get( "/api/ledstrip" ).then( ).statusCode( 200 );
	}

}
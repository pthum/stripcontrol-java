package de.backenddev.led.stripcontrol.springbackend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.model.LEDStrip;
import de.backenddev.led.stripcontrol.springbackend.controller.LEDStripController;
import de.backenddev.led.stripcontrol.springbackend.service.ModelService;

@RunWith ( SpringRunner.class )
@WebMvcTest ( LEDStripController.class )
public class LEDStripControllerTest
{
	private static final String CP_PATH = "/api/ledstrip";
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ModelService<LEDStrip> service;

	@MockBean
	private ModelService<ColorProfile> cpService;

	private final ObjectMapper mapper = new ObjectMapper( );

	/**
	 * Test creating an object with valid values
	 */
	@Test
	public void testGetObjects( ) throws Exception
	{
		final List<LEDStrip> profiles = new ArrayList<>( );
		final LEDStrip cp1 = new LEDStrip( "testStrip", "testdescription", 1, 2, 5, 8000000 );
		final LEDStrip cp2 = new LEDStrip( "testStrip2", "testdescription", 3, 4, 5, 8000000 );
		cp1.setId( 1L );
		cp2.setId( 2L );
		profiles.add( cp1 );
		profiles.add( cp2 );
		when( this.service.getAll( ) ).thenReturn( profiles );

		this.mockMvc.perform( get( CP_PATH ) ).andExpect( status( ).isOk( ) )
				.andExpect( content( ).json( toJson( profiles ) ) );
	}

	/**
	 * Test creating an object with valid values
	 */
	@Test
	public void testPostCorrectObject( ) throws Exception
	{
		final LEDStrip cp = new LEDStrip( "testStrip", "testdescription", 1, 2, 5, 8000000 );
		final LEDStrip cpWithId = new LEDStrip( "testStrip", "testdescription", 1, 2, 5, 8000000 );
		cpWithId.setId( 1L );
		when( this.service.save( cp ) ).thenReturn( cpWithId );

		this.mockMvc.perform( post( CP_PATH ).contentType( MediaType.APPLICATION_JSON ).content( toJson( cp ) ) )
				.andExpect( status( ).isCreated( ) ).andExpect( redirectedUrlPattern( "http://*" + CP_PATH + "/1" ) );
	}

	/**
	 * Test the GET /api/ledstrip/{id} for a non-existing object
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetNotExistingObject( ) throws Exception
	{
		when( this.service.getById( 1 ) ).thenReturn( Optional.empty( ) );
		this.mockMvc.perform( get( CP_PATH + "/1" ) ).andExpect( status( ).isNotFound( ) );
	}

	/**
	 * Test the GET /api/ledstrip/{id} for an existing object
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetExistingObject( ) throws Exception
	{
		final LEDStrip cp = new LEDStrip( "testStrip", "testdescription", 1, 2, 5, 8000000 );
		when( this.service.getById( 1 ) ).thenReturn( Optional.of( cp ) );
		this.mockMvc.perform( get( CP_PATH + "/1" ) ).andExpect( status( ).isOk( ) )
				.andExpect( content( ).contentType( "application/json" ) ).andExpect( content( ).json( toJson( cp ) ) );
	}

	/**
	 * Test the DELETE /api/ledstrip/{id} for a non-existing object
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteNotExistingObject( ) throws Exception
	{
		when( this.service.getById( 1 ) ).thenReturn( Optional.empty( ) );
		this.mockMvc.perform( delete( CP_PATH + "/1" ) ).andExpect( status( ).isNotFound( ) );
	}

	/**
	 * Test the DELETE /api/ledstrip/{id} for a non-existing object
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteExistingObject( ) throws Exception
	{
		final LEDStrip cp = new LEDStrip( "testStrip", "testdescription", 1, 2, 5, 8000000 );
		when( this.service.getById( 1 ) ).thenReturn( Optional.of( cp ) );
		this.mockMvc.perform( delete( CP_PATH + "/1" ) ).andExpect( status( ).isNoContent( ) );
	}

	private String toJson( final Object object ) throws Exception
	{
		return this.mapper.writeValueAsString( object );
	}
}

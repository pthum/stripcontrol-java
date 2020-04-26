package de.backenddev.led.stripcontrol.javastripbackend.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.backenddev.led.stripcontrol.javastripbackend.model.ColorProfile;
import de.backenddev.led.stripcontrol.javastripbackend.service.ModelService;

@RunWith ( SpringRunner.class )
@WebMvcTest ( ColorProfileController.class )
public class ColorProfileControllerTest
{
	private static final String CP_PATH = "/api/colorprofile";
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ModelService<ColorProfile> service;

	private final ObjectMapper mapper = new ObjectMapper( );

	/**
	 * Test creating an object with valid values
	 */
	@Test
	public void testGetObjects( ) throws Exception
	{
		final List<ColorProfile> profiles = new ArrayList<>( );
		final ColorProfile cp1 = new ColorProfile( 255, 255, 255, 100 );
		final ColorProfile cp2 = new ColorProfile( 255, 255, 255, 100 );
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
		final ColorProfile cp = new ColorProfile( 255, 255, 255, 100 );
		final ColorProfile cpWithId = new ColorProfile( 255, 255, 255, 100 );
		cpWithId.setId( 1L );
		when( this.service.save( cp ) ).thenReturn( cpWithId );

		this.mockMvc.perform( post( CP_PATH ).contentType( MediaType.APPLICATION_JSON ).content( toJson( cp ) ) )
				.andExpect( status( ).isCreated( ) ).andExpect( redirectedUrlPattern( "http://*" + CP_PATH + "/1" ) );
	}

	/**
	 * Test creating an object with invalid values
	 */
	@Test
	@Ignore
	public void testPostObjectWrongValues( ) throws Exception
	{
		final ColorProfile cp = new ColorProfile( 300, 255, 255, 100 );
		final ColorProfile cpWithId = new ColorProfile( 300, 255, 255, 100 );
		cpWithId.setId( 1L );
		when( this.service.save( cp ) ).thenReturn( cpWithId );

		this.mockMvc.perform( post( CP_PATH ).contentType( MediaType.APPLICATION_JSON ).content( toJson( cp ) ) )
				.andExpect( status( ).isBadRequest( ) ).andExpect( jsonPath( "$.errors.[0].['field']", is( "red" ) ) );
	}

	/**
	 * Test the GET /api/colorprofile/{id} for a non-existing object
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
	 * Test the GET /api/colorprofile/{id} for an existing object
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetExistingObject( ) throws Exception
	{
		final ColorProfile cp = new ColorProfile( 255, 255, 255, 100 );
		when( this.service.getById( 1 ) ).thenReturn( Optional.of( cp ) );
		this.mockMvc.perform( get( CP_PATH + "/1" ) ).andExpect( status( ).isOk( ) )
				.andExpect( content( ).contentType( "application/json" ) ).andExpect( content( ).json( toJson( cp ) ) );
	}

	/**
	 * Test the DELETE /api/colorprofile/{id} for a non-existing object
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
	 * Test the DELETE /api/colorprofile/{id} for a non-existing object
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteExistingObject( ) throws Exception
	{
		final ColorProfile cp = new ColorProfile( 255, 255, 255, 100 );
		when( this.service.getById( 1 ) ).thenReturn( Optional.of( cp ) );
		this.mockMvc.perform( delete( CP_PATH + "/1" ) ).andExpect( status( ).isNoContent( ) );
	}

	private String toJson( final Object object ) throws Exception
	{
		return this.mapper.writeValueAsString( object );
	}
}

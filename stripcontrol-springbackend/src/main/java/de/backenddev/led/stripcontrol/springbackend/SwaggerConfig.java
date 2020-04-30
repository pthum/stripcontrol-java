package de.backenddev.led.stripcontrol.springbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import de.backenddev.led.stripcontrol.springbackend.controller.MainController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * this class handles the swagger configuration
 * 
 * @author thum
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
	@Bean
	public Docket api( )
	{
		final Predicate<RequestHandler> pred = RequestHandlerSelectors
				.basePackage( MainController.class.getPackage( ).getName( ) );
		return new Docket( DocumentationType.SWAGGER_2 ).forCodeGeneration( false ).select( ).apis( pred )
				.paths( PathSelectors.ant( "/api/**" ) ).build( );
	}
}
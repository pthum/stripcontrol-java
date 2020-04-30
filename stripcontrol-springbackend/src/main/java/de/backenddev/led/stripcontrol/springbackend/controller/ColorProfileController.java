package de.backenddev.led.stripcontrol.springbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.backenddev.led.stripcontrol.model.ColorProfile;
import de.backenddev.led.stripcontrol.springbackend.SpringBackendApplication;
import de.backenddev.led.stripcontrol.springbackend.service.ModelService;

@RestController
@RequestMapping ( SpringBackendApplication.API_BASE + "/colorprofile" )
public class ColorProfileController extends AbstractModelController<ColorProfile>
{
	private static final Logger LOG = LoggerFactory.getLogger( ColorProfileController.class );

	@Autowired
	private ModelService<ColorProfile> service;

	@Override
	ModelService<ColorProfile> getModelService( )
	{
		return this.service;
	}

	@Override
	Logger getLogger( )
	{
		return LOG;
	}

	@Override
	void prepareUpdateObjectBeforeSave( final ColorProfile updateObject, final ColorProfile dbObject )
	{
	}

}

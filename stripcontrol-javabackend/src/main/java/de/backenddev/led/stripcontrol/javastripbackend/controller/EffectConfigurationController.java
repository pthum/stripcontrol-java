package de.backenddev.led.stripcontrol.javastripbackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.backenddev.led.stripcontrol.javastripbackend.JavastripBackendApplication;
import de.backenddev.led.stripcontrol.javastripbackend.model.EffectConfiguration;
import de.backenddev.led.stripcontrol.javastripbackend.service.ModelService;

@RestController
@RequestMapping ( JavastripBackendApplication.API_BASE + "/EffectConfiguration" )
public class EffectConfigurationController extends AbstractModelController<EffectConfiguration>
{
	private static final Logger LOG = LoggerFactory.getLogger( EffectConfigurationController.class );

	@Autowired
	private ModelService<EffectConfiguration> service;

	@Override
	ModelService<EffectConfiguration> getModelService( )
	{
		return this.service;
	}

	@Override
	Logger getLogger( )
	{
		return LOG;
	}
}

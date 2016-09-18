package com.mutsys.chimera.raml.resource;

import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.loader.ClassPathResourceLoader;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RamlResourceModelFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(RamlResourceModelFactory.class);
	
	public static RamlResourceModel createRamlApi(String resourceLocation) {
		RamlModelBuilder ramlModelBuilder = new RamlModelBuilder(new ClassPathResourceLoader());
		RamlModelResult ramlModelResult = ramlModelBuilder.buildApi(resourceLocation);
		if (ramlModelResult.hasErrors()) {
			LOG.error("raml model had errors");
			for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
				LOG.error(validationResult.getMessage());
			}
		}
		return createResources(ramlModelResult.getApiV10());
	}
	
	protected static RamlResourceModel createResources(Api api) {
		return RamlResourceFactory.getResources(api);
	}

}

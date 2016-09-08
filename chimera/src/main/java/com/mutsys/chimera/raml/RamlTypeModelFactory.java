package com.mutsys.chimera.raml;

import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.loader.ClassPathResourceLoader;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mutsys.chimera.raml.type.RamlTypeFactory;

public class RamlTypeModelFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(RamlTypeModelFactory.class);
	
	public static RamlTypeModel createRamlApi(String resourceLocation) {
		RamlModelBuilder ramlModelBuilder = new RamlModelBuilder(new ClassPathResourceLoader());
		RamlModelResult ramlModelResult = ramlModelBuilder.buildApi(resourceLocation);
		if (ramlModelResult.hasErrors()) {
			LOG.error("raml model had errors");
			for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
				LOG.error(validationResult.getMessage());
			}
		}
		return createTypes(ramlModelResult.getApiV10());
	}
	
	protected static RamlTypeModel createTypes(Api api) {
		return RamlTypeFactory.getTypes(api);
	}

}

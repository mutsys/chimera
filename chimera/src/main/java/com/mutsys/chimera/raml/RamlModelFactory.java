package com.mutsys.chimera.raml;

import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.v10.api.Api;

import com.mutsys.chimera.raml.type.RamlTypeFactory;

public class RamlModelFactory {
	
	public static RamlTypeRegistry createRamlApi(String resourceLocation) {
		RamlModelBuilder ramlModelBuilder = new RamlModelBuilder();
		RamlModelResult ramlModelResult = ramlModelBuilder.buildApi(resourceLocation);
		return createTypes(ramlModelResult.getApiV10());
	}
	
	protected static RamlTypeRegistry createTypes(Api api) {
		return RamlTypeFactory.getTypes(api);
	}

}

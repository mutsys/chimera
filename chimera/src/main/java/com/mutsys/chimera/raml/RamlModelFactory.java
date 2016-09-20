package com.mutsys.chimera.raml;

import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.loader.ClassPathResourceLoader;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mutsys.chimera.raml.resource.RamlResource;
import com.mutsys.chimera.raml.resource.RamlResourceFactory;
import com.mutsys.chimera.raml.resource.RamlResourceModel;
import com.mutsys.chimera.raml.resource.RamlResourceParameter;
import com.mutsys.chimera.raml.resource.method.RamlResourceMethod;
import com.mutsys.chimera.raml.resource.method.RamlResourceMethodResponse;
import com.mutsys.chimera.raml.type.RamlType;
import com.mutsys.chimera.raml.type.RamlTypeFactory;
import com.mutsys.chimera.raml.type.RamlTypeModel;
import com.mutsys.chimera.raml.type.RamlTypeReference;

public class RamlModelFactory {
	
private static final Logger LOG = LoggerFactory.getLogger(RamlModelFactory.class);
	
	public static RamlModel createRamlApi(String resourceLocation) {
		RamlModelBuilder ramlModelBuilder = new RamlModelBuilder(new ClassPathResourceLoader());
		RamlModelResult ramlModelResult = ramlModelBuilder.buildApi(resourceLocation);
		if (ramlModelResult.hasErrors()) {
			LOG.error("raml model had errors");
			for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
				LOG.error(validationResult.getMessage());
			}
		}
		
		Api api = ramlModelResult.getApiV10();
		RamlTypeModel ramlTypeModel = RamlTypeFactory.getTypes(api);
		RamlResourceModel ramlResourceModel = RamlResourceFactory.getResources(api);
		resolveTypeReferences(ramlTypeModel, ramlResourceModel);
		RamlModel ramlModel = new RamlModel(ramlTypeModel, ramlResourceModel);
		
		return ramlModel;
	}
	
	protected static void resolveTypeReferences(RamlTypeModel ramlTypeModel, RamlResourceModel ramlResourceModel) {
		for (RamlResource resource : ramlResourceModel.getResources()) {
			resolveTypeReferences(ramlTypeModel, resource);
		}		
	}
	
	protected static void resolveTypeReferences(RamlTypeModel ramlTypeModel, RamlResource resource) {
		for (RamlResourceParameter pathParameter : resource.getPathParameters()) {
			resolveTypeReference(ramlTypeModel, pathParameter.getRamlType());
		}
		for (RamlResourceMethod method : resource.getMethods()) {
			method.getRequestBody().ifPresent(b -> {
				resolveTypeReference(ramlTypeModel, b.getBodyType());
			});
			for (RamlResourceParameter queryParameter : method.getQueryParameters()) {
				resolveTypeReference(ramlTypeModel, queryParameter.getRamlType());
			}
			for (RamlResourceMethodResponse response : method.getResponses()) {
				response.getBody().ifPresent(b -> {
					resolveTypeReference(ramlTypeModel, b.getBodyType());
				});
			}
		}
		for (RamlResource ramlSubResource : resource.getSubResources()) {
			resolveTypeReferences(ramlTypeModel, ramlSubResource);
		}
	}
	
	protected static void resolveTypeReference(RamlTypeModel ramlTypeModel, RamlType ramlType) {
		if (ramlType.isReference()) {
			RamlTypeReference typeReference = (RamlTypeReference) ramlType;
			String referencedTypeName = typeReference.getReferencedTypeName();
			typeReference.setReferencedType(ramlTypeModel.getType(referencedTypeName));
		}
	}

}

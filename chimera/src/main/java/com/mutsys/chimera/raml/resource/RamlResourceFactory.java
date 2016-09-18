package com.mutsys.chimera.raml.resource;

import java.util.ArrayList;
import java.util.List;

import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.resources.Resource;

import com.mutsys.chimera.raml.resource.method.RamlMethodFactory;
import com.mutsys.chimera.raml.type.RamlTypeReference;

public class RamlResourceFactory {
	
	protected static final String SLASH = "/";
	
	protected static final String EMPTY_STRING = "";
	
	protected static final String TEMPLATE_BEGIN = "{";
	
	protected static final String TEMPLATE_END = "}";
	
	public static RamlResourceModel getResources(Api api) {
		RamlResourceModel resourceModel = new RamlResourceModel();
		List<RamlResource> ramlResources = collectResources(null, api.resources());
		for (RamlResource ramlResource : ramlResources) {
			resourceModel.addResource(ramlResource);
		}
		return resourceModel;
	}
		
	protected static List<RamlResource> collectResources(RamlResource parentRamlResource, List<Resource> resources) {
		List<RamlResource> ramlResources = new ArrayList<>();
		for (Resource resource : resources) {
			RamlResource ramlResource = convertResource(parentRamlResource, resource);
			collectCodeGenAttributes(ramlResource, resource);
			ramlResources.add(ramlResource);
			ramlResources.addAll(collectSubResources(new ArrayList<>(), ramlResource));
		}
		return ramlResources;
	}
	
	protected static void collectCodeGenAttributes(RamlResource ramlResource, Resource resource) {
		if (!ramlResource.isRootResource()) {
			return;
		}
		ramlResource.setJavaClassType(getAnnotation(resource, "javaClassType"));
		ramlResource.setJavaClassName(getAnnotation(resource, "javaClassName"));
		ramlResource.setJavaPackageName(getAnnotation(resource, "javaPackageName"));
	}
	
	protected static String getAnnotation(Resource resource, String annotationName) {
		return resource.annotations().stream()
			.filter(a -> a.annotation().name().equals(annotationName))
			.findFirst()
			.map(a -> a.structuredValue().value().toString())
			.orElse("");
	}
	
	protected static List<RamlResource> collectSubResources(List<RamlResource> subResources, RamlResource ramlResource) {
		for (RamlResource subResource : ramlResource.getSubResources()) {
			subResources.add(subResource);
			return collectSubResources(subResources, subResource);
		}
		return subResources;
	}
	
	protected static RamlResource convertResource(RamlResource parentRamlResource, Resource resource) {
		RamlResource ramlResource = new RamlResource();
		String relativePath = resource.relativeUri().value();
		ramlResource.setRelativePath(relativePath);
		
		ramlResource.setName(
			relativePath.substring(1)
			.replace(TEMPLATE_BEGIN, EMPTY_STRING)
			.replace(TEMPLATE_END, EMPTY_STRING)
		);
		
		if (parentRamlResource != null) {
			ramlResource.setParentResource(parentRamlResource);
			parentRamlResource.addSubResource(ramlResource);
		}
		
		resource.uriParameters().forEach(p -> {
			RamlResourceParameter pathParameter = new RamlResourceParameter();
			pathParameter.setName(p.name());
			RamlTypeReference typeReference = new RamlTypeReference();
			typeReference.setReferencedTypeName(p.type());
			pathParameter.setRamlType(typeReference);
			ramlResource.addPathParameter(pathParameter);
		});
		
		RamlMethodFactory.getMethods(resource.methods()).forEach(ramlResource::addMethod);
		
		ramlResource.getMethods().addAll(RamlMethodFactory.getMethods(resource.methods()));
		
		resource.resources().forEach(r -> convertResource(ramlResource, r));
		
		return ramlResource;
		
	}
	
}

package com.mutsys.chimera.java.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mutsys.chimera.java.type.JavaTypeReference;
import com.mutsys.chimera.java.type.ProvidedJavaType;
import com.mutsys.chimera.java.type.RamlJavaType;
import com.mutsys.chimera.raml.resource.RamlResource;
import com.mutsys.chimera.raml.resource.RamlResourceModel;
import com.mutsys.chimera.raml.resource.RamlResourceParameter;
import com.mutsys.chimera.raml.resource.method.RamlResourceMethod;
import com.mutsys.chimera.raml.resource.method.RamlResourceMethodResponse;
import com.mutsys.chimera.raml.resource.method.RamlResourceMethodType;
import com.mutsys.chimera.raml.type.BuiltInRamlType;
import com.mutsys.chimera.raml.type.RamlType;
import com.mutsys.chimera.raml.type.RamlTypeReference;
import com.mutsys.chimera.raml.type.user.UserDefinedObjectType;

public class JavaResourceModelFactory {
	
	public static JavaResourceModel create(RamlResourceModel ramlResourceModel) {
		JavaResourceModel javaResourceModel = new JavaResourceModel();
		RamlResource rootResource = getRootResource(ramlResourceModel);
		javaResourceModel.setResourcePath(rootResource.getRelativePath());
		getResourceMethods(rootResource).forEach(javaResourceModel::addResourceMethod);
		return javaResourceModel;
	}
	
	protected static RamlResource getRootResource(RamlResourceModel ramlResourceModel) {
		return ramlResourceModel.getResources().stream()
				.filter(r -> r.isRootResource())
				.findFirst()
				.orElse(null);
	}
	
	protected static void collectCodeGenInfo(JavaResourceModel javaResourceModel, RamlResource rootResource) {
		javaResourceModel.setName(rootResource.getJavaClassName());		
	}
	
	protected static List<JavaResourceMethod> getResourceMethods(RamlResource resource) {
		List<JavaResourceMethod> resourceMethods = new ArrayList<>();
		for (RamlResourceMethod method : resource.getMethods()) {
			resourceMethods.add(convertMethod(resource, method));
		}
		return resourceMethods;
	}
	
	protected static JavaResourceMethod convertMethod(RamlResource resource, RamlResourceMethod method) {
		JavaResourceMethod resourceMethod = new JavaResourceMethod();
		resourceMethod.setResourcePath(resource.getRelativePath());
		resourceMethod.setHttpMethod(method.getMethodType().getMethodName().toUpperCase());
		resourceMethod.setConsumesMediaType(getRequestMediaType(method));
		resourceMethod.setProducesMediaType(getResponseMediaType(method));
		resourceMethod.setName(getMethodName(resource, method));
		
		for (RamlResourceParameter pathParameter :  resource.getPathParameters()) {
			PathParameterMethodArgument pathParamArgument = new PathParameterMethodArgument();
			pathParamArgument.setArgumentName(pathParameter.getName());
			pathParamArgument.setArgumentJavaType(getJavaType(pathParameter.getRamlType()));
			resourceMethod.addArgument(pathParamArgument);
		}
		
		method.getRequestBody().ifPresent(b -> {
			RequestBodyMethodArgument requestBodyArgument = new RequestBodyMethodArgument();
			requestBodyArgument.setArgumentName("apiRequest");
			requestBodyArgument.setArgumentJavaType(getJavaType(b.getBodyType()));
			resourceMethod.addArgument(requestBodyArgument);
		});
		
		return resourceMethod;
	}
	
	protected static Optional<String> getRequestMediaType(RamlResourceMethod method) {
		return method.getRequestBody().map(b -> b.getMediaType().getMimeType());
	}
	
	protected static Optional<String> getResponseMediaType(RamlResourceMethod method) {
		for (RamlResourceMethodResponse response : method.getResponses()) {
			if (response.getBody().isPresent()) {
				return response.getBody().map(b -> b.getMediaType().getMimeType());
			}
		}
		return Optional.empty();
	}
	
	protected static String getMethodName(RamlResource resource, RamlResourceMethod method) {
		RamlResource rootResource = resource;
		while (!rootResource.isRootResource()) {
			rootResource = rootResource.getParentResource();
		}
		String resourceName = rootResource.getRelativePath().substring(1);
		StringBuilder camelCasedResourceName = new StringBuilder(resourceName.substring(0, 1).toUpperCase()).append(resourceName.substring(1));
		boolean hasPathParams = resource.getPathParameters().size() > 0;
		if (method.getMethodType().equals(RamlResourceMethodType.GET) && hasPathParams) {
			camelCasedResourceName.insert(0, "One");
		}
		return camelCasedResourceName.insert(0, method.getMethodType().getPrefix()).toString();
	}
	
	protected static RamlJavaType getJavaType(RamlType ramlType) {
		if (ramlType.isBuiltInType()) {
			BuiltInRamlType builtInType = (BuiltInRamlType) ramlType;
			ProvidedJavaType propertyType = new ProvidedJavaType();
			propertyType.setProvidedClass(builtInType.getJavaClass());
			return propertyType;
		}
		if (ramlType.isDefinition()) {
			UserDefinedObjectType typeDefinition = (UserDefinedObjectType) ramlType;
			JavaTypeReference propertyType = new JavaTypeReference();
			propertyType.setName(typeDefinition.getJavaClassName());
			propertyType.setPackageName(typeDefinition.getJavaPackageName());
			return propertyType;
		}
		if (ramlType.isReference()) {
			RamlTypeReference typeReference = (RamlTypeReference) ramlType;
			return getJavaType(typeReference.getReferencedType());
		}
		return null;
	}

}

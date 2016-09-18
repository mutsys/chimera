package com.mutsys.chimera.raml.resource.method;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.raml.v2.api.model.v10.bodies.Response;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Method;

import com.mutsys.chimera.raml.resource.RamlResourceParameter;
import com.mutsys.chimera.raml.type.RamlTypeReference;

public class RamlMethodFactory {
	
	public static List<RamlResourceMethod> getMethods(List<Method> methods) {
		List<RamlResourceMethod> ramlMethods = new ArrayList<>();
		for (Method method : methods) {
			ramlMethods.add(convertMethod(method));
		}
		return ramlMethods;
	}
	
	protected static RamlResourceMethod convertMethod(Method method) {
		RamlResourceMethodType methodType = RamlResourceMethodType.getMethodType(method.method());
		RamlResourceMethod ramlMethod = new RamlResourceMethod(methodType);
		if (ramlMethod.hasRequestBody()) {
			setRequestBody(ramlMethod, method);
		}
		ramlMethod.getResponses().addAll(getResponses(method));
		ramlMethod.getQueryParameters().addAll(getQueryParameters(method));
		return ramlMethod;
	}
	
	protected static List<RamlResourceParameter> getQueryParameters(Method method) {
		return method.queryParameters().stream().map(q -> convertQueryParameter(q)).collect(Collectors.toList());
	}
	
	protected static RamlResourceParameter convertQueryParameter(TypeDeclaration queryParameter) {
		RamlResourceParameter ramlQueryParameter = new RamlResourceParameter();
		ramlQueryParameter.setName(queryParameter.name());
		RamlTypeReference ramlTypeReference = new RamlTypeReference();
		ramlTypeReference.setReferencedTypeName(queryParameter.type());
		ramlQueryParameter.setRamlType(ramlTypeReference);
		return ramlQueryParameter;
	}
	
	protected static void setRequestBody(RamlResourceMethod ramlMethod, Method method) {
		List<TypeDeclaration> methodBodies = method.body();
		if (!methodBodies.isEmpty()) {
			ramlMethod.setRequestBody(convertBody(methodBodies.get(0)));
		}
	}
	
	protected static List<RamlResourceMethodResponse> getResponses(Method method) {
		return method.responses().stream().map(RamlMethodFactory::convertResponse).collect(Collectors.toList());
	}
	
	protected static RamlResourceMethodResponse convertResponse(Response response) {
		RamlResourceMethodResponse ramlResponse = new RamlResourceMethodResponse();
		ramlResponse.setResponseCode(response.code().value());
		List<TypeDeclaration> body = response.body();
		if (!body.isEmpty()) {
			TypeDeclaration responseBody = body.get(0);
			ramlResponse.setBody(convertBody(responseBody));
		}
		return ramlResponse;
	}
	
	protected static RamlResourceMethodBody convertBody(TypeDeclaration methodBody) {
		RamlResourceMethodBody body = new RamlResourceMethodBody();
		RamlResourceMethodBodyMediaType mediaType = RamlResourceMethodBodyMediaType.getMediaType(methodBody.name());
		body.setMediaType(mediaType);
		RamlTypeReference bodyType = new RamlTypeReference();
		bodyType.setReferencedTypeName(methodBody.type());
		body.setBodyType(bodyType);
		return body;
	}

}

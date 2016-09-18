package com.mutsys.chimera.raml.resource.method;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mutsys.chimera.raml.resource.RamlResourceParameter;

public class RamlResourceMethod {

	private RamlResourceMethodType resourceMethodType;	
	private List<RamlResourceParameter> queryParameters = new ArrayList<>();
	private Optional<RamlResourceMethodBody> request = Optional.empty();
	private List<RamlResourceMethodResponse> responses = new ArrayList<>();
	
	public RamlResourceMethod(RamlResourceMethodType resourceMethodType) {
		this.resourceMethodType = resourceMethodType;
	}

	public RamlResourceMethodType getMethodType() {
		return resourceMethodType;
	}

	public boolean isGet() {
		return RamlResourceMethodType.GET.equals(resourceMethodType);
	}

	public boolean isPost() {
		return RamlResourceMethodType.POST.equals(resourceMethodType);
	}

	public boolean isPut() {
		return RamlResourceMethodType.PUT.equals(resourceMethodType);
	}

	public boolean isDelete() {
		return RamlResourceMethodType.DELETE.equals(resourceMethodType);
	}

	public boolean hasRequestBody() {
		return resourceMethodType.requestHasBody();
	}

	public Optional<RamlResourceMethodBody> getRequestBody() {
		return request;
	}
	
	public void setRequestBody(RamlResourceMethodBody requestBody) {
		this.request = Optional.of(requestBody);
	}

	public List<RamlResourceMethodResponse> getResponses() {
		return responses;
	}
	
	public RamlResourceMethodResponse getResponse(String responseCode) {
		return responses.stream()
				.filter(r -> responseCode.equals(r.getResponseCode()))
				.findFirst()
				.orElse(null);
	}

	public void addResponse(RamlResourceMethodResponse response) {
		responses.add(response);
	}
	
	public List<RamlResourceParameter> getQueryParameters() {
		return queryParameters;
	}
	
	public RamlResourceParameter getQueryParameter(String parameterName) {
		return queryParameters.stream()
				.filter(q -> parameterName.equals(q.getName()))
				.findFirst()
				.orElse(null);
	}

	public void addQueryParameter(RamlResourceParameter queryParameter) {
		queryParameters.add(queryParameter);
	}
	
}

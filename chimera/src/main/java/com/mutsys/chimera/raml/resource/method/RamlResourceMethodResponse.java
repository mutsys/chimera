package com.mutsys.chimera.raml.resource.method;

import java.util.Optional;

public class RamlResourceMethodResponse {

	private String responseCode;
	private Optional<RamlResourceMethodBody> body = Optional.empty();

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Optional<RamlResourceMethodBody> getBody() {
		return body;
	}

	public void setBody(RamlResourceMethodBody body) {
		this.body = Optional.of(body);
	}

}

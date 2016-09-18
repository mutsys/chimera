package com.mutsys.chimera.raml.resource.method;

public class RamlResourceMethodResponse {

	private String responseCode;
	private RamlResourceMethodBody body;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public RamlResourceMethodBody getBody() {
		return body;
	}

	public void setBody(RamlResourceMethodBody body) {
		this.body = body;
	}

}

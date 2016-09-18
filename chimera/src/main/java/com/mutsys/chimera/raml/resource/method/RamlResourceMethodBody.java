package com.mutsys.chimera.raml.resource.method;

import com.mutsys.chimera.raml.type.RamlType;

public class RamlResourceMethodBody {
	
	private RamlResourceMethodBodyMediaType mediaType;
	private RamlType bodyType;
	
	public void setMediaType(RamlResourceMethodBodyMediaType mediaType) {
		this.mediaType = mediaType;
	}

	public RamlResourceMethodBodyMediaType getMediaType() {
		return mediaType;
	}
	
	public RamlType getBodyType() {
		return bodyType;
	}
	
	public void setBodyType(RamlType bodyType) {
		this.bodyType = bodyType;
	}

}

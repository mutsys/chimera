package com.mutsys.chimera.raml.resource.method;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum RamlResourceMethodBodyMediaType {
	
	JSON ("application/json"),
	XML  ("application/xml");
	
	private static final Map<String,RamlResourceMethodBodyMediaType> mimeTypeMap = Arrays.stream(values()).collect(Collectors.toMap(mt -> mt.getMimeType(), Function.identity()));
	
	private String mimeType;
	
	private RamlResourceMethodBodyMediaType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public static RamlResourceMethodBodyMediaType getMediaType(String mimeType) {
		return mimeTypeMap.get(mimeType);
	}

}

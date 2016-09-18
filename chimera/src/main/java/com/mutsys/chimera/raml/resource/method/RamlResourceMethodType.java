package com.mutsys.chimera.raml.resource.method;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum RamlResourceMethodType {
	
	GET    ("get",    false),
	POST   ("post",   true),
	PUT    ("put",    true),
	DELETE ("delete", false);
	
	private static final Map<String,RamlResourceMethodType> methodMap = Arrays.stream(values()).collect(Collectors.toMap(m -> m.getMethodName(), Function.identity()));
	
	private boolean requestHasBody;
	private String methodName;
	
	private RamlResourceMethodType(String methodName, boolean requestHasBody) {
		this.methodName = methodName;
		this.requestHasBody = requestHasBody;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public boolean requestHasBody() {
		return requestHasBody;
	}
	
	public static RamlResourceMethodType getMethodType(String methodName) {
		return methodMap.get(methodName);
	}

}

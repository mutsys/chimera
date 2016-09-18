package com.mutsys.chimera.java.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JavaResourceMethod {

	private String resourcePath;
	private Optional<String> consumesMediaType = Optional.empty();
	private Optional<String> producesMediaType = Optional.empty();;
	private String httpMethod;
	private String name;
	private List<JavaResourceMethodArgument> arguments = new ArrayList<>();

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public Optional<String> getConsumesMediaType() {
		return consumesMediaType;
	}

	public void setConsumesMediaType(Optional<String> consumesMediaType) {
		this.consumesMediaType = consumesMediaType;
	}

	public Optional<String> getProducesMediaType() {
		return producesMediaType;
	}

	public void setProducesMediaType(Optional<String> producesMediaType) {
		this.producesMediaType = producesMediaType;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<JavaResourceMethodArgument> getArguments() {
		return arguments;
	}

	void addArgument(JavaResourceMethodArgument argument) {
		arguments.add(argument);
	}

}

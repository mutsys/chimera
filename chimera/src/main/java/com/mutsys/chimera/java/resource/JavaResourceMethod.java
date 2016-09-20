package com.mutsys.chimera.java.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaResourceMethod {

	private JavaResourceModel resource;
	private Optional<String> consumesMediaType = Optional.empty();
	private Optional<String> producesMediaType = Optional.empty();;
	private String httpMethod;
	private String name;
	private List<JavaResourceMethodArgument> arguments = new ArrayList<>();

	public JavaResourceModel getResource() {
		return resource;
	}

	public void setResource(JavaResourceModel resource) {
		this.resource = resource;
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
	
	public List<String> getArgumentNames() {
		return arguments.stream()
				.map(a -> a.getArgumentName())
				.collect(Collectors.toList());
	}
	
	public JavaResourceMethodArgument getArgument(String argumentName) {
		return arguments.stream()
				.filter(a -> a.getArgumentName().equals(argumentName))
				.findFirst()
				.orElse(null);
	}

	void addArgument(JavaResourceMethodArgument argument) {
		arguments.add(argument);
	}

}

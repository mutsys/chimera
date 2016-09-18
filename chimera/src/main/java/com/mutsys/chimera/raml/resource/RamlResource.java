package com.mutsys.chimera.raml.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mutsys.chimera.raml.resource.method.RamlResourceMethod;

public class RamlResource {

	private String name;
	private String relativePath;
	private RamlResource parentResource;
	private Map<String,RamlResourceParameter> pathParameters = new HashMap<>();
	private Map<String,RamlResourceMethod> methods = new HashMap<>();
	private Map<String,RamlResource> subResources = new HashMap<>();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public boolean isRootResource() {
		return Objects.isNull(parentResource);
	}

	public RamlResource getParentResource() {
		return parentResource;
	}
	
	public void setParentResource(RamlResource parentResource) {
		this.parentResource = parentResource;
	}

	public String getPath() {
		return getAncestry().stream().map(RamlResource::getRelativePath).collect(Collectors.joining());
	}
	
	protected LinkedList<RamlResource> getAncestry() {
		LinkedList<RamlResource> ancestry = new LinkedList<>();
		ancestry.add(this);
		RamlResource parentResource = getParentResource();
		while (parentResource != null) {
			ancestry.push(parentResource);
			parentResource = parentResource.getParentResource();
		}
		return ancestry;
	}

	public List<RamlResourceMethod> getMethods() {
		return new ArrayList<>(methods.values());
	}
	
	public RamlResourceMethod getMethod(String methodName) {
		return methods.get(methodName);
	}

	public void addMethod(RamlResourceMethod method) {
		methods.put(method.getMethodType().getMethodName(), method);
	}

	public List<RamlResource> getSubResources() {
		return new ArrayList<>(subResources.values());
	}
	
	public RamlResource getSubResource(String relativePath) {
		return subResources.get(relativePath);
	}

	public void addSubResource(RamlResource subResource) {
		subResources.put(subResource.getRelativePath(), subResource);
	}
	
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	public String getRelativePath() {
		return relativePath;
	}
	
	public List<RamlResourceParameter> getPathParameters() {
		return new ArrayList<>(pathParameters.values());
	}
	
	public RamlResourceParameter getPathParameter(String parameterName) {
		return pathParameters.get(parameterName);
	}
	
	public void addPathParameter(RamlResourceParameter pathParameter) {
		pathParameters.put(pathParameter.getName(), pathParameter);
	}
	
}

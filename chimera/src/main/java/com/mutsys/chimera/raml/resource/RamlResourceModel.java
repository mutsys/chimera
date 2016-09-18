package com.mutsys.chimera.raml.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RamlResourceModel implements RamlResourceProvider {
	
	private final Map<String,RamlResource> resourceMap = new HashMap<>();
	
	public void addResource(RamlResource ramlResource) {
		resourceMap.put(ramlResource.getPath(), ramlResource);
	}
	
	public List<String> getResourcePaths() {
		return resourceMap.keySet().stream().sorted().collect(Collectors.toList());
	}
	
	public List<RamlResource> getResources() {
		return resourceMap.keySet().stream().sorted().map(p -> resourceMap.get(p)).collect(Collectors.toList());
	}
	
	public RamlResource getResource(String path) {
		return resourceMap.get(path);
	}

}

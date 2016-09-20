package com.mutsys.chimera.raml.resource;

import java.util.Arrays;
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
	
	private final static String SLASH = "/";
	private final static String EMPTY_STRING = "";
	
	public RamlResource getResource(String path) {
		RamlResource ramlResource = null;
		String[] pathSegments = path.split("/");
		if (pathSegments[0].equals(EMPTY_STRING)) {
			pathSegments = Arrays.copyOfRange(pathSegments, 1, pathSegments.length);
		}
		for (int i = 0; i < pathSegments.length; i++) {
			String segmentName = SLASH + pathSegments[i];
			if (i == 0) {
				ramlResource = resourceMap.get(segmentName);
			} else {
				ramlResource = ramlResource.getSubResource(segmentName);
			}
			if (ramlResource == null) {
				return ramlResource;
			}
		}
		
		return ramlResource;
	}

}

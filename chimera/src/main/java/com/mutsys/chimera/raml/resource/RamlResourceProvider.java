package com.mutsys.chimera.raml.resource;

import java.util.List;

public interface RamlResourceProvider {

	List<String> getResourcePaths();

	List<RamlResource> getResources();

	RamlResource getResource(String path);

}
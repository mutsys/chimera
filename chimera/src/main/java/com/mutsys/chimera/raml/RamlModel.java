package com.mutsys.chimera.raml;

import java.util.List;

import com.mutsys.chimera.raml.resource.RamlResource;
import com.mutsys.chimera.raml.resource.RamlResourceModel;
import com.mutsys.chimera.raml.resource.RamlResourceProvider;
import com.mutsys.chimera.raml.type.RamlTypeDefinition;
import com.mutsys.chimera.raml.type.RamlTypeModel;
import com.mutsys.chimera.raml.type.RamlTypeProvider;

public class RamlModel implements RamlTypeProvider, RamlResourceProvider {
	
	private final RamlTypeModel ramlTypeModel;
	private final RamlResourceModel ramlResourceModel;
	
	public RamlModel(RamlTypeModel ramlTypeModel, RamlResourceModel ramlResourceModel) {
		this.ramlTypeModel = ramlTypeModel;
		this.ramlResourceModel = ramlResourceModel;
	}
	
	public RamlTypeModel getRamlTypeModel() {
		return ramlTypeModel;
	}
	
	public RamlResourceModel getRamlResourceModel() {
		return ramlResourceModel;
	}
	
	@Override
	public List<String> getResourcePaths() {
		return ramlResourceModel.getResourcePaths();
	}
	@Override
	public List<RamlResource> getResources() {
		return ramlResourceModel.getResources();
	}
	@Override
	public RamlResource getResource(String path) {
		return ramlResourceModel.getResource(path);
	}
	@Override
	public List<String> getTypeNames(boolean withBuiltInTypes) {
		return ramlTypeModel.getTypeNames(withBuiltInTypes);
	}
	@Override
	public List<RamlTypeDefinition> getTypes(boolean withBuiltInTypes) {
		return ramlTypeModel.getTypes(withBuiltInTypes);
	}
	@Override
	public RamlTypeDefinition getType(String typeName) {
		return ramlTypeModel.getType(typeName);
	}

	@Override
	public List<String> getTypeNames() {
		return ramlTypeModel.getTypeNames();
	}

	@Override
	public List<RamlTypeDefinition> getTypes() {
		return ramlTypeModel.getTypes();
	}

}

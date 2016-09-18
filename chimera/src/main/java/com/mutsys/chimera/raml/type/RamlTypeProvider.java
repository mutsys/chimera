package com.mutsys.chimera.raml.type;

import java.util.List;

public interface RamlTypeProvider {

	List<String> getTypeNames(boolean withBuiltInTypes);
	
	List<String> getTypeNames();

	List<RamlTypeDefinition> getTypes(boolean withBuiltInTypes);
	
	List<RamlTypeDefinition> getTypes();

	RamlTypeDefinition getType(String typeName);

}
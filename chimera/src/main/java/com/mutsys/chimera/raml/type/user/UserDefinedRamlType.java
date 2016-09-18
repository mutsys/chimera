package com.mutsys.chimera.raml.type.user;

import com.mutsys.chimera.raml.GeneratedJavaType;
import com.mutsys.chimera.raml.type.RamlTypeDefinition;
import com.mutsys.chimera.raml.type.RamlTypeModel;

public interface UserDefinedRamlType extends RamlTypeDefinition, GeneratedJavaType {

	RamlTypeModel getTypeRegistry();
	
	void setTypeRegistry(RamlTypeModel typeRegistry);
	
	void setTypeName(String typeName);

	void setSuperType(String superType);

}
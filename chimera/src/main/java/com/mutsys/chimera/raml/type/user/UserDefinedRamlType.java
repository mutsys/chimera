package com.mutsys.chimera.raml.type.user;

import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.type.RamlTypeDefinition;

public interface UserDefinedRamlType extends RamlTypeDefinition {

	RamlTypeModel getTypeRegistry();
	
	void setTypeRegistry(RamlTypeModel typeRegistry);
	
	void setTypeName(String typeName);

	void setSuperType(String superType);
	
	String getJavaClassType();
	
	void setJavaClassType(String javaClassType);
	
	String getJavaClassName();
	
	void setJavaClassName(String javaClassName);
	
	String getJavaPackageName();
	
	void setJavaPackageName(String javaPackageName);

}
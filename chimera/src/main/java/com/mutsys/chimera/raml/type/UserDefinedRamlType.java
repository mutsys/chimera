package com.mutsys.chimera.raml.type;

import com.mutsys.chimera.raml.RamlTypeRegistry;

public interface UserDefinedRamlType extends RamlType {

	RamlTypeRegistry getTypeRegistry();
	
	void setTypeRegistry(RamlTypeRegistry typeRegistry);
	
	void setTypeName(String typeName);

	void setSuperType(String superType);
	
	String getJavaClassName();
	
	void setJavaClassName(String javaClassName);
	
	String getJavaPackageName();
	
	void setJavaPackageName(String javaPackageName);

}
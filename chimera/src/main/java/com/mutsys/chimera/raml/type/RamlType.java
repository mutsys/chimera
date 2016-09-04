package com.mutsys.chimera.raml.type;

public interface RamlType {
	
	boolean isReference();
	
	boolean isDefinition();
	
	RamlTypeFamily getTypeFamily();
	
	boolean isBuiltInType();
	
	String getTypeName();
	
	String getSuperType();
	
	boolean isScalar();
	
	boolean isObject();
	
	boolean isArray();

}

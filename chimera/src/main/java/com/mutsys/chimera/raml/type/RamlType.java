package com.mutsys.chimera.raml.type;

public interface RamlType {
	
	String getTypeName();
	
	boolean isReference();
	
	boolean isDefinition();
	
	RamlTypeFamily getTypeFamily();
	
	boolean isBuiltInType();
	
	boolean isScalar();
	
	boolean isObject();
	
	boolean isArray();

}

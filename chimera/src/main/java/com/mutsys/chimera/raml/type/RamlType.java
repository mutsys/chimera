package com.mutsys.chimera.raml.type;

public interface RamlType {
	
	RamlTypeFamily getTypeFamily();
	
	boolean isBuiltInType();
	
	String getTypeName();
	
	String getSuperType();
	
	boolean isScalar();
	
	boolean isObject();
	
	boolean isArray();

}

package com.mutsys.chimera.java.resource;

import com.mutsys.chimera.java.type.RamlJavaType;

public interface JavaResourceMethodArgument {
	
	JavaResourceMethodArgumentType getArgumentType();
	
	boolean isRequestBody();
	
	boolean isPathParameter();
	
	String getArgumentName();
	
	RamlJavaType getArgumentJavaType();

}

package com.mutsys.chimera.java.type;

import java.util.List;

public interface JavaClass extends UserDefinedJavaType {
	
	JavaClass getSuperClass();
	
	void setSuperClass(JavaClass superClass);
	
	List<JavaTypeReference> getImplementedInterfaces();
	
	void addImplementedInterface(JavaTypeReference javaInterface);

}

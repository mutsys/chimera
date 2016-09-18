package com.mutsys.chimera.java.type;

import java.util.List;

import com.mutsys.chimera.java.GeneratedJavaType;

public interface UserDefinedJavaType extends GeneratedJavaType {
	
	public boolean isInterface();
	
	public boolean isAbstractClass();
	
	public boolean isConcreteClass();
	
	List<JavaProperty> getProperties();
	
	List<String> getPropertyNames();
	
	JavaProperty getProperty(String propertyName);
	
	void addProperty(JavaProperty property);

}

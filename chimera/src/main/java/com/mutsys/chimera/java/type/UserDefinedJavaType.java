package com.mutsys.chimera.java.type;

import java.util.List;

import com.mutsys.chimera.java.pakkage.JavaPackage;

public interface UserDefinedJavaType extends RamlJavaType {
	
	JavaClassType getJavaClassType();
	
	public boolean isInterface();
	
	public boolean isAbstractClass();
	
	public boolean isConcreteClass();
	
	JavaPackage getPackage();
	
	void setPackage(JavaPackage pakkage);
	
	void setName(String name);
	
	List<JavaProperty> getProperties();
	
	List<String> getPropertyNames();
	
	JavaProperty getProperty(String propertyName);
	
	void addProperty(JavaProperty property);

}

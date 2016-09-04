package com.mutsys.chimera.java.type;

import java.util.List;

import com.mutsys.chimera.java.pakkage.JavaPackage;

public interface UserDefinedJavaType extends JavaType {
	
	JavaClassType getJavaClassType();
	
	public boolean isInterface();
	
	public boolean isAbstractClass();
	
	public boolean isConcreteClass();
	
	JavaPackage getPackage();
	
	void setPackage(JavaPackage pakkage);
	
	void setName(String name);
	
	List<JavaProperty> getProperties();
	
	void addProperty(JavaProperty property);

}

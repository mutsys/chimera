package com.mutsys.chimera.java.type;

import java.util.List;

import com.mutsys.chimera.java.JavaPackage;
import com.mutsys.chimera.raml.type.Property;

public interface JavaClass {
	
	JavaClassType getJavaClassType();
	
	public boolean isInterface();
	
	public boolean isAbstractClass();
	
	public boolean isConcreteClass();
	
	JavaPackage getPackage();
	
	void setPackage(JavaPackage pakkage);
	
	String getName();
	
	void setName(String name);
	
	List<Property> getProperties();
	
	void setProperties(List<Property> properties);

}

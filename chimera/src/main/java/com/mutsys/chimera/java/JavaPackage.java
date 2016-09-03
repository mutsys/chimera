package com.mutsys.chimera.java;

import java.util.List;

import com.mutsys.chimera.java.type.JavaClass;

public interface JavaPackage extends PackageContainer<JavaPackage> {
	
	PackageContainer<?> getParent();
	
	void setParent(PackageContainer<?> parent);
	
	String getName();
	
	void setName(String name);
	
	String getCanonicalName();
	
	List<JavaClass> getClasses();
	
	void addClass(JavaClass javaClass);
	
}

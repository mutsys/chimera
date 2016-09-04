package com.mutsys.chimera.java.pakkage;

import java.util.List;

interface PackageContainer<P extends PackageContainer<P>> {

	PackageContainerType getPackageContainerType();
	
	boolean isTypeModel();
	
	boolean isPackage();
	
	List<JavaPackage> getSubPackages();
	
	void addSubPackage(JavaPackage javaPackage);
	
	JavaPackage getSubPackage(String packageName);

}
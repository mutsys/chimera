package com.mutsys.chimera.java;

import java.util.List;

public interface PackageContainer<P extends PackageContainer<P>> {

	PackageContainerType getPackageContainerType();
	
	public boolean isTypeModel();
	
	public boolean isPackage();
	
	List<JavaPackage> getPackages();
	
	void setPackages(List<JavaPackage> packages);

}
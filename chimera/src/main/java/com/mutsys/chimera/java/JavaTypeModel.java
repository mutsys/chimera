package com.mutsys.chimera.java;

import java.util.List;

import com.mutsys.chimera.java.pakkage.AbstractPackageContainer;
import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.pakkage.PackageContainerType;

public class JavaTypeModel extends AbstractPackageContainer<JavaTypeModel> {

	public JavaTypeModel() {
		super(PackageContainerType.TYPE_MODEL);
	}
	
	public List<JavaPackage> getPackages() {
		return getSubPackages();
	}
	
	public JavaPackage getPackage(String packageName) {
		return getSubPackage(packageName);
	}

}

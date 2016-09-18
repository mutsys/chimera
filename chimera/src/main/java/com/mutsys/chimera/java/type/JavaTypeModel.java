package com.mutsys.chimera.java.type;

import java.util.List;

import com.mutsys.chimera.java.pakkage.AbstractPackageContainer;
import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.pakkage.PackageContainerType;

public class JavaTypeModel extends AbstractPackageContainer<JavaTypeModel> implements JavaTypeProvider {

	public JavaTypeModel() {
		super(PackageContainerType.TYPE_MODEL);
	}
	
	@Override
	public List<JavaPackage> getPackages() {
		return getSubPackages();
	}
	
	@Override
	public JavaPackage getPackage(String packageName) {
		return getSubPackage(packageName);
	}

}

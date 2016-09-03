package com.mutsys.chimera.java;

import java.util.List;

public abstract class AbstractPackageContainer<P extends PackageContainer<P>> implements PackageContainer<P> {

	final private PackageContainerType packageContainerType;
	private List<JavaPackage> packages;
	
	protected AbstractPackageContainer(PackageContainerType packageContainerType) {
		this.packageContainerType = packageContainerType;
	}
	
	@Override
	public PackageContainerType getPackageContainerType() {
		return packageContainerType;
	}

	@Override
	public boolean isTypeModel() {
		return packageContainerType.equals(PackageContainerType.TYPE_MODEL);
	}

	@Override
	public boolean isPackage() {
		return packageContainerType.equals(PackageContainerType.PACKAGE);
	}

	@Override
	public List<JavaPackage> getPackages() {
		return packages;
	}

	@Override
	public void setPackages(List<JavaPackage> packages) {
		this.packages = packages;
	}

}

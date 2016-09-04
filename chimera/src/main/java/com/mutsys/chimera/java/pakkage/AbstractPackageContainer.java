package com.mutsys.chimera.java.pakkage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPackageContainer<P extends PackageContainer<P>> implements PackageContainer<P> {

	final private PackageContainerType packageContainerType;
	private List<JavaPackage> subPackages = new ArrayList<>();
	
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
	public
	List<JavaPackage> getSubPackages() {
		return subPackages;
	}

	@Override
	public void addSubPackage(JavaPackage javaPackage) {
		subPackages.add(javaPackage);
		javaPackage.setParent(this);
	}

	@Override
	public JavaPackage getSubPackage(String packageName) {
		String[] packageNameSegments = packageName.split("\\.", 2);
		return subPackages.stream()
				.filter(p -> p.getName().equals(packageNameSegments[0]))
				.findFirst()
				.map(p -> {
					if (packageNameSegments.length > 1) {
						return p.getSubPackage(packageNameSegments[1]);
					}
					return p;
				}).orElse(null);
				
	}

	
	
}

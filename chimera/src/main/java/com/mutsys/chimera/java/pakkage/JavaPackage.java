package com.mutsys.chimera.java.pakkage;

import java.util.ArrayList;
import java.util.List;

import com.mutsys.chimera.java.JavaHierarchyMember;
import com.mutsys.chimera.java.type.UserDefinedJavaType;

public class JavaPackage extends AbstractPackageContainer<JavaPackage> implements JavaHierarchyMember {
	
	private PackageContainer<?> parent;
	private String name;
	private List<UserDefinedJavaType> classes = new ArrayList<>();
	
	public JavaPackage() {
		super(PackageContainerType.PACKAGE);
	}

	public PackageContainer<?> getParent() {
		return parent;
	}

	public void setParent(PackageContainer<?> parent) {
		this.parent = parent;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getCanonicalName() {
		StringBuilder nameBuilder = new StringBuilder(getName());
		JavaPackage pakkage = this;
		while (pakkage.getParent().isPackage()) {
			pakkage = (JavaPackage) pakkage.getParent();
			nameBuilder.insert(0, ".");
			nameBuilder.insert(0, pakkage.getName());
		}
		return nameBuilder.toString();
	}

	public List<UserDefinedJavaType> getClasses() {
		return classes;
	}

	public void addClass(UserDefinedJavaType javaClass) {
		classes.add(javaClass);
		javaClass.setPackage(this);
	}

	public boolean hasClasses() {
		return !classes.isEmpty();
	}
	
}

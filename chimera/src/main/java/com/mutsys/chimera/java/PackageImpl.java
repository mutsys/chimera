package com.mutsys.chimera.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mutsys.chimera.java.type.JavaClass;

public class PackageImpl extends AbstractPackageContainer<JavaPackage> implements JavaPackage {

	private PackageContainer<?> parent;
	private String name;
	private List<JavaClass> classes = new ArrayList<>();
	
	public PackageImpl() {
		super(PackageContainerType.PACKAGE);
	}

	@Override
	public PackageContainer<?> getParent() {
		return parent;
	}

	@Override
	public void setParent(PackageContainer<?> parent) {
		this.parent = parent;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getCanonicalName() {
		PackageHierarchyVisitor visitor = new PackageHierarchyVisitor();
		visitor.visit(this);
		return visitor.getHierarchy().stream()
				.map(p -> p.getName())
				.collect(Collectors.joining("."));
	}

	@Override
	public List<JavaClass> getClasses() {
		return classes;
	}

	@Override
	public void addClass(JavaClass javaClass) {
		classes.add(javaClass);
		javaClass.setPackage(this);
	}

	

}

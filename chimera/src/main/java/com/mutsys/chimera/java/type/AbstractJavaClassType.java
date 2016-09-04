package com.mutsys.chimera.java.type;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJavaClassType extends AbstractUserDefinedJavaType implements JavaClass {

	private JavaClass superClass;
	private List<JavaInterface> implementedInterfaces = new ArrayList<>();
	
	protected AbstractJavaClassType(JavaClassType javaClassType) {
		super(javaClassType);
	}

	@Override
	public JavaClass getSuperClass() {
		return superClass;
	}

	@Override
	public void setSuperClass(JavaClass superClass) {
		this.superClass = superClass;
	}

	@Override
	public List<JavaInterface> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public void addImplementedInterface(JavaInterface javaInterface) {
		implementedInterfaces.add(javaInterface);
	}

}

package com.mutsys.chimera.java.type;

import java.util.List;

import com.mutsys.chimera.java.JavaPackage;
import com.mutsys.chimera.raml.type.Property;

public abstract class AbstractJavaClass implements JavaClass {

	private JavaPackage pakkage;
	final private JavaClassType javaClassType;
	private String name;
	private List<Property> properties;
	
	protected AbstractJavaClass(JavaClassType javaClassType) {
		this.javaClassType = javaClassType;
	}
	
	@Override
	public JavaClassType getJavaClassType() {
		return javaClassType;
	}

	@Override
	public boolean isInterface() {
		return javaClassType.equals(JavaClassType.INTERFACE);
	}

	@Override
	public boolean isAbstractClass() {
		return javaClassType.equals(JavaClassType.ABSTRACT_CLASS);
	}

	@Override
	public boolean isConcreteClass() {
		return javaClassType.equals(JavaClassType.CONCRETE_CLASS);
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
	public List<Property> getProperties() {
		return properties;
	}

	@Override
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	@Override
	public JavaPackage getPackage() {
		return pakkage;
	}

	@Override
	public void setPackage(JavaPackage pakkage) {
		this.pakkage = pakkage;
	}

}

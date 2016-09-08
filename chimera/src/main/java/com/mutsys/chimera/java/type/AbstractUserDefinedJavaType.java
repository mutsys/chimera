package com.mutsys.chimera.java.type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mutsys.chimera.java.pakkage.JavaPackage;

public abstract class AbstractUserDefinedJavaType extends AbstractJavaType implements UserDefinedJavaType {

	private JavaPackage pakkage;
	final private JavaClassType javaClassType;
	private String name;
	private List<JavaProperty> properties = new ArrayList<>();
	
	protected AbstractUserDefinedJavaType(JavaClassType javaClassType) {
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
	public List<JavaProperty> getProperties() {
		return properties;
	}
	
	@Override
	public List<String> getPropertyNames() {
		return properties.stream().map(p -> p.getName()).collect(Collectors.toList());
	}

	@Override
	public JavaProperty getProperty(String propertyName) {
		return properties.stream().filter(p -> p.getName().equals(propertyName)).findFirst().orElse(null);
	}

	@Override
	public void addProperty(JavaProperty property) {
		properties.add(property);
	}

	@Override
	public JavaPackage getPackage() {
		return pakkage;
	}

	@Override
	public void setPackage(JavaPackage pakkage) {
		this.pakkage = pakkage;
	}

	@Override
	public boolean isUserDefined() {
		return true;
	}

	@Override
	public String getCanonicalName() {
		return new StringBuilder(pakkage.getCanonicalName())
				.append(".")
				.append(name)
				.toString();
	}

	@Override
	public String getPackageName() {
		return pakkage.getCanonicalName();
	}

}

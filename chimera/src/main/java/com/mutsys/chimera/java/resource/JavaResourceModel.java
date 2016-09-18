package com.mutsys.chimera.java.resource;

import java.util.ArrayList;
import java.util.List;

import com.mutsys.chimera.java.GeneratedJavaType;
import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.type.JavaClassType;

public class JavaResourceModel implements GeneratedJavaType {
	
	private String resourcePath;
	private String name;
	private JavaPackage pakkage;
	
	private List<JavaResourceMethod> resourceMethods = new ArrayList<>();
	
	public List<JavaResourceMethod> getResourceMethods() {
		return resourceMethods;
	}
	
	public void addResourceMethod(JavaResourceMethod resourceMethod) {
		resourceMethods.add(resourceMethod);
	}

	@Override
	public boolean isJvmType() {
		return false;
	}

	@Override
	public boolean isPrimitiveType() {
		return false;
	}

	@Override
	public boolean isAutoboxedType() {
		return false;
	}

	@Override
	public boolean isProvided() {
		return false;
	}

	@Override
	public boolean isUserDefined() {
		return false;
	}

	@Override
	public String getPackageName() {
		return pakkage.getCanonicalName();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getCanonicalName() {
		return new StringBuilder(pakkage.getCanonicalName())
			.append(".")
			.append(name)
			.toString();
	}

	@Override
	public JavaClassType getJavaClassType() {
		return JavaClassType.INTERFACE;
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
	public void setName(String name) {
		this.name = name;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

}

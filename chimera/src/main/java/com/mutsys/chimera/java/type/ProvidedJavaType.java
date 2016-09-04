package com.mutsys.chimera.java.type;

public class ProvidedJavaType extends AbstractJavaType {

	private Class<?> providedClass;
	
	public Class<?> getProvidedClass() {
		return providedClass;
	}

	public void setProvidedClass(Class<?> providedClass) {
		this.providedClass = providedClass;
	}

	@Override
	public boolean isUserDefined() {
		return false;
	}

	@Override
	public String getName() {
		return providedClass.getSimpleName();
	}

	@Override
	public String getCanonicalName() {
		return providedClass.getCanonicalName();
	}

	@Override
	public String getPackageName() {
		return providedClass.getPackage().getName();
	}

}

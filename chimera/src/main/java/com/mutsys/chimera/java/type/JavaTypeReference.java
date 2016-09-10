package com.mutsys.chimera.java.type;

public class JavaTypeReference extends AbstractJavaType {

	private String javaTypeName;
	private String javaTypePackageName;
	
	@Override
	public boolean isUserDefined() {
		return true;
	}

	@Override
	public String getName() {
		return javaTypeName;
	}
	
	public void setName(String name) {
		this.javaTypeName = name;
	}

	@Override
	public String getCanonicalName() {
		return new StringBuilder(javaTypePackageName)
				.append(".")
				.append(javaTypeName)
				.toString();
	}
	
	@Override
	public String getPackageName() {
		return javaTypePackageName;
	}
	
	public void setPackageName(String packageName) {
		this.javaTypePackageName = packageName;
	}

}

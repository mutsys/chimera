package com.mutsys.chimera.java.type;

public class JavaProperty {

	private String name;
	private RamlJavaType javaType;
	private JavaPropertyCardinality cardinality;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RamlJavaType getJavaType() {
		return javaType;
	}

	public void setJavaType(RamlJavaType javaType) {
		this.javaType = javaType;
	}

	public JavaPropertyCardinality getCardinality() {
		return cardinality;
	}

	public void setCardinality(JavaPropertyCardinality cardinality) {
		this.cardinality = cardinality;
	}

}

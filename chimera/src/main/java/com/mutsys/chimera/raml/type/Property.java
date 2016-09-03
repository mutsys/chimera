package com.mutsys.chimera.raml.type;

public class Property {

	private String ramlType;
	private String name;
	private boolean required;

	public String getType() {
		return ramlType;
	}

	public void setType(String ramlType) {
		this.ramlType = ramlType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRequired() {
		return required;
	}

	public void isRequired(boolean required) {
		this.required = required;
	}

}

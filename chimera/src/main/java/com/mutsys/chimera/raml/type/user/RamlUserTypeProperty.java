package com.mutsys.chimera.raml.type.user;

import com.mutsys.chimera.raml.type.RamlType;

public class RamlUserTypeProperty {

	private RamlType ramlType;
	private String name;
	private boolean required;

	public RamlType getType() {
		return ramlType;
	}

	public void setType(RamlType ramlType) {
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

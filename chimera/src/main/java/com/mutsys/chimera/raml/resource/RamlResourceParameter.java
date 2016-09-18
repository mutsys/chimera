package com.mutsys.chimera.raml.resource;

import com.mutsys.chimera.raml.type.RamlType;

public class RamlResourceParameter {
	
	private String name;
	private RamlType ramlType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RamlType getRamlType() {
		return ramlType;
	}

	public void setRamlType(RamlType ramlType) {
		this.ramlType = ramlType;
	}

}

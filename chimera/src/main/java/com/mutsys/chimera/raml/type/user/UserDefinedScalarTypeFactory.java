package com.mutsys.chimera.raml.type.user;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

public class UserDefinedScalarTypeFactory implements UserDefinedRamlTypeFactory {

	@Override
	public UserDefinedRamlType create(TypeDeclaration typeDeclaration) {
		UserDefinedScalarType scalarType = new UserDefinedScalarType();
		scalarType.setTypeName(typeDeclaration.name());
		scalarType.setSuperType(typeDeclaration.type());
		return scalarType;
	}

}

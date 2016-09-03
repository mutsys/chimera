package com.mutsys.chimera.raml.type.factory;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

import com.mutsys.chimera.raml.type.UserDefinedRamlType;
import com.mutsys.chimera.raml.type.UserDefinedScalarType;

public class UserDefinedScalarTypeFactory implements UserDefinedRamlTypeFactory {

	@Override
	public UserDefinedRamlType create(TypeDeclaration typeDeclaration) {
		UserDefinedScalarType scalarType = new UserDefinedScalarType();
		scalarType.setTypeName(typeDeclaration.name());
		scalarType.setSuperType(typeDeclaration.type());
		return scalarType;
	}

}

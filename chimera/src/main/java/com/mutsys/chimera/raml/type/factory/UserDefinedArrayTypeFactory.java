package com.mutsys.chimera.raml.type.factory;

import org.raml.v2.api.model.v10.datamodel.ArrayTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

import com.mutsys.chimera.raml.type.UserDefinedArrayType;
import com.mutsys.chimera.raml.type.UserDefinedRamlType;

public class UserDefinedArrayTypeFactory implements UserDefinedRamlTypeFactory {

	@Override
	public UserDefinedRamlType create(TypeDeclaration typeDeclaration) {
		UserDefinedArrayType arrayType = new UserDefinedArrayType();
		arrayType.setTypeName(typeDeclaration.name());
		arrayType.setMemberType(getMemberType((ArrayTypeDeclaration) typeDeclaration));
		return arrayType;
	}
	
	protected String getMemberType(ArrayTypeDeclaration typeDeclaration) {
		return typeDeclaration.items().name();
	}

}

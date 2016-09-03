package com.mutsys.chimera.raml.type.factory;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

import com.mutsys.chimera.raml.type.UserDefinedRamlType;

public interface UserDefinedRamlTypeFactory {
	
	UserDefinedRamlType create(TypeDeclaration typeDeclaration);

}

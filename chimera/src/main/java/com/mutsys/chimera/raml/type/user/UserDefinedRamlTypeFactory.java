package com.mutsys.chimera.raml.type.user;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

public interface UserDefinedRamlTypeFactory {
	
	UserDefinedRamlType create(TypeDeclaration typeDeclaration);

}

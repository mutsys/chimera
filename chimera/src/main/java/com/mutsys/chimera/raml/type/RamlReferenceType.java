package com.mutsys.chimera.raml.type;

public interface RamlReferenceType extends RamlType {

	String getReferencedTypeName();

	void setReferencedTypeName(String referencedTypeName);

	RamlTypeDefinition getReferencedType();

	void setReferencedType(RamlTypeDefinition referencedType);

}
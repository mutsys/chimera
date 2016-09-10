package com.mutsys.chimera.raml.type;

public class RamlArrayType implements RamlReferenceType {

	private String referencedTypeName;
	private RamlTypeDefinition referencedType;
	
	@Override
	public String getTypeName() {
		return referencedTypeName;
	}
	
	@Override
	public String getReferencedTypeName() {
		return referencedTypeName;
	}

	@Override
	public void setReferencedTypeName(String referencedTypeName) {
		this.referencedTypeName = referencedTypeName;
	}

	@Override
	public RamlTypeDefinition getReferencedType() {
		return referencedType;
	}

	@Override
	public void setReferencedType(RamlTypeDefinition referencedType) {
		this.referencedType = referencedType;
	}

	@Override
	public boolean isReference() {
		return true;
	}

	@Override
	public boolean isDefinition() {
		return false;
	}

	@Override
	public RamlTypeFamily getTypeFamily() {
		return RamlTypeFamily.ARRAY;
	}

	@Override
	public boolean isBuiltInType() {
		return false;
	}

	@Override
	public boolean isScalar() {
		return false;
	}

	@Override
	public boolean isObject() {
		return false;
	}

	@Override
	public boolean isArray() {
		return true;
	}
	
}

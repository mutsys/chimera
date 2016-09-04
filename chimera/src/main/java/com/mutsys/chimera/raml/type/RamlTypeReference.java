package com.mutsys.chimera.raml.type;

public class RamlTypeReference implements RamlType {
	
	private String typeName;
	private RamlType referencedType;
	
	public RamlTypeReference(String typeName) {
		this.typeName = typeName;
	}

	public RamlType getReferencedType() {
		return referencedType;
	}

	public void setReferencedType(RamlType referencedType) {
		this.referencedType = referencedType;
	}

	@Override
	public RamlTypeFamily getTypeFamily() {
		return referencedType.getTypeFamily();
	}

	@Override
	public boolean isBuiltInType() {
		return referencedType.isBuiltInType();
	}

	@Override
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String getSuperType() {
		return referencedType.getSuperType();
	}

	@Override
	public boolean isScalar() {
		return referencedType.isScalar();
	}

	@Override
	public boolean isObject() {
		return referencedType.isObject();
	}

	@Override
	public boolean isArray() {
		return referencedType.isArray();
	}

	@Override
	public boolean isReference() {
		return true;
	}

	@Override
	public boolean isDefinition() {
		return false;
	}

}

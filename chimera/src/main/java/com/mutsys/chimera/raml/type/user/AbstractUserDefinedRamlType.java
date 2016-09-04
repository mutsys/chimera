package com.mutsys.chimera.raml.type.user;

import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.type.RamlTypeFamily;

public abstract class AbstractUserDefinedRamlType implements UserDefinedRamlType {

	final private RamlTypeFamily typeFamily;
	private RamlTypeModel typeRegistry;
	private String superType;
	private String typeName;
	private String javaClassType;
	private String javaClassName;
	private String javaPackageName;

	protected AbstractUserDefinedRamlType(RamlTypeFamily typeFamily) {
		this.typeFamily = typeFamily;
	}

	@Override
	public RamlTypeModel getTypeRegistry() {
		return typeRegistry;
	}

	@Override
	public void setTypeRegistry(RamlTypeModel typeRegistry) {
		this.typeRegistry = typeRegistry;
	}

	@Override
	public RamlTypeFamily getTypeFamily() {
		return typeFamily;
	}

	@Override
	public boolean isScalar() {
		return typeFamily.equals(RamlTypeFamily.SCALAR);
	}

	@Override
	public boolean isObject() {
		return typeFamily.equals(RamlTypeFamily.OBJECT);
	}

	@Override
	public boolean isArray() {
		return typeFamily.equals(RamlTypeFamily.ARRAY);
	}

	@Override
	public boolean isBuiltInType() {
		return false;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String getSuperType() {
		return superType;
	}

	@Override
	public void setSuperType(String superType) {
		this.superType = superType;
	}

	@Override
	public String getJavaClassName() {
		return javaClassName;
	}

	@Override
	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}

	@Override
	public String getJavaPackageName() {
		return javaPackageName;
	}

	@Override
	public void setJavaPackageName(String javaPackageName) {
		this.javaPackageName = javaPackageName;
	}

	@Override
	public String getJavaClassType() {
		return javaClassType;
	}

	@Override
	public void setJavaClassType(String javaClassType) {
		this.javaClassType = javaClassType;
	}

	@Override
	public boolean isReference() {
		return false;
	}

	@Override
	public boolean isDefinition() {
		return true;
	}

}
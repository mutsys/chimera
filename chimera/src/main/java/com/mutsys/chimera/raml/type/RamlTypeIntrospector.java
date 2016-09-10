package com.mutsys.chimera.raml.type;

import java.util.Objects;

import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

public class RamlTypeIntrospector {

	public static boolean isBuiltInRamlType(TypeDeclaration typeDeclaration) {
		return Objects.nonNull(BuiltInRamlType.getType(typeDeclaration.type()));
	}
	
	public static boolean isScalarType(TypeDeclaration typeDeclaration) {
		RamlType builtInType = BuiltInRamlType.getType(typeDeclaration.type());
		return Objects.nonNull(builtInType) && builtInType.isScalar();
	}
	
	public static boolean isArrayType(TypeDeclaration typeDeclaration) {
		if (typeDeclaration.type().endsWith("[]")) {
			return true;
		}
		RamlType builtInType = BuiltInRamlType.getType(typeDeclaration.type());
		return Objects.nonNull(builtInType) && builtInType.isArray();
	}
	
	public static boolean isObjectType(TypeDeclaration typeDeclaration) {
		RamlType builtInType = BuiltInRamlType.getType(typeDeclaration.type());
		return Objects.isNull(builtInType) || builtInType.isObject();
	}
	
	public static RamlTypeFamily getRamlTypeFamily(TypeDeclaration typeDeclaration) {
		if (isScalarType(typeDeclaration)) {
			return RamlTypeFamily.SCALAR;
		}
		if (isArrayType(typeDeclaration)) {
			return RamlTypeFamily.ARRAY;
		}
		if (isObjectType(typeDeclaration)) {
			return RamlTypeFamily.OBJECT;
		}
		return RamlTypeFamily.ANY;
	}
	
}

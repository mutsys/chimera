package com.mutsys.chimera.raml.type;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.api.Library;
import org.raml.v2.api.model.v10.api.LibraryBase;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

import com.mutsys.chimera.raml.RamlTypeRegistry;
import com.mutsys.chimera.raml.type.factory.UserDefinedArrayTypeFactory;
import com.mutsys.chimera.raml.type.factory.UserDefinedObjectTypeFactory;
import com.mutsys.chimera.raml.type.factory.UserDefinedScalarTypeFactory;

public class RamlTypeFactory {

	public static RamlTypeRegistry getTypes(Api api) {
		List<TypeDeclaration> types = getTypes((LibraryBase) api);
		List<UserDefinedRamlType> ramlTypes = convertTypes(types);		
		RamlTypeRegistry registry = new RamlTypeRegistry();
		ramlTypes.forEach(registry::addRamlType);
		return registry;
	}
	
	protected static List<TypeDeclaration> getTypes(LibraryBase library) {
		List<TypeDeclaration> definedTypes = library.types();
		for (Library importedLibrary : library.uses()) {
			definedTypes.addAll(getTypes(importedLibrary));
		}
		return definedTypes;	
	}
	
	protected static List<UserDefinedRamlType> convertTypes(List<TypeDeclaration> typeDeclarations) {
		return typeDeclarations.stream()
				.map(t -> convertType(t))
				.collect(Collectors.toList());
	}
	
	protected static UserDefinedRamlType convertType(TypeDeclaration typeDeclaration) {
		if (extendsScalarType(typeDeclaration)) {
			UserDefinedScalarTypeFactory scalarTypeFactory = new UserDefinedScalarTypeFactory();
			return scalarTypeFactory.create(typeDeclaration);
		}
		if (isArrayType(typeDeclaration)) {
			UserDefinedArrayTypeFactory arrayTypeFactory = new UserDefinedArrayTypeFactory();
			return arrayTypeFactory.create(typeDeclaration);
		}
		if (isObjectType(typeDeclaration)) {
			UserDefinedObjectTypeFactory objectTypeFactory = new UserDefinedObjectTypeFactory();
			return objectTypeFactory.create(typeDeclaration);
		}
		return null;
	}
	
	protected static boolean isScalarType(TypeDeclaration typeDeclaration) {
		RamlType builtInType = BuiltInRamlType.getType(typeDeclaration.type());
		return Objects.nonNull(builtInType) && builtInType.isScalar();
	}
	
	protected static boolean extendsScalarType(TypeDeclaration typeDeclaration) {
		RamlType builtInType = BuiltInRamlType.getType(typeDeclaration.type());
		return Objects.nonNull(builtInType) && builtInType.isScalar();
	}
	
	protected static boolean isArrayType(TypeDeclaration typeDeclaration) {
		if (typeDeclaration.name().contains("[") && typeDeclaration.name().contains("]")) {
			return true;
		}
		RamlType builtInType = BuiltInRamlType.getType(typeDeclaration.type());
		return Objects.nonNull(builtInType) && builtInType.isArray();
	}
	
	protected static boolean isObjectType(TypeDeclaration typeDeclaration) {
		RamlType builtInType = BuiltInRamlType.getType(typeDeclaration.type());
		return Objects.isNull(builtInType) || Objects.nonNull(builtInType) && builtInType.isObject();
	}
	
}

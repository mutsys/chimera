package com.mutsys.chimera.raml.type;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.api.Library;
import org.raml.v2.api.model.v10.api.LibraryBase;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.type.user.UserDefinedArrayTypeFactory;
import com.mutsys.chimera.raml.type.user.UserDefinedObjectType;
import com.mutsys.chimera.raml.type.user.UserDefinedObjectTypeFactory;
import com.mutsys.chimera.raml.type.user.UserDefinedRamlType;
import com.mutsys.chimera.raml.type.user.UserDefinedScalarTypeFactory;

public class RamlTypeFactory {

	public static RamlTypeModel getTypes(Api api) {
		List<TypeDeclaration> types = getTypes((LibraryBase) api);
		List<UserDefinedRamlType> ramlTypes = convertTypes(types);		
		RamlTypeModel typeModel = new RamlTypeModel();
		ramlTypes.forEach(typeModel::addRamlType);
		resolveTypeReferences(typeModel);
		return typeModel;
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
	
	protected static void resolveTypeReferences(RamlTypeModel typeModel) {
		typeModel.getTypes(false).stream()
			.filter(t -> t.isDefinition())
			.filter(t -> t.isObject())
			.map(t -> (UserDefinedObjectType) t)
			.forEach(t -> t.getProperties().stream()
							.filter(p -> p.getType().isReference())
							.map(p -> (RamlTypeReference) p.getType())
							.forEach(r -> r.setReferencedType(typeModel.getType(r.getTypeName())))
					);
	}
	
}

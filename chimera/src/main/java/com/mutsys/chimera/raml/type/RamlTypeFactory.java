package com.mutsys.chimera.raml.type;

import java.util.List;
import java.util.stream.Collectors;

import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.api.Library;
import org.raml.v2.api.model.v10.api.LibraryBase;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

import com.mutsys.chimera.raml.RamlTypeModel;
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
		if (RamlTypeIntrospector.isScalarType(typeDeclaration)) {
			return new UserDefinedScalarTypeFactory().create(typeDeclaration);
		}
		if (RamlTypeIntrospector.isObjectType(typeDeclaration)) {
			return new UserDefinedObjectTypeFactory().create(typeDeclaration);
		}
		return null;
	}
	
	protected static void resolveTypeReferences(RamlTypeModel typeModel) {
		typeModel.getTypes(false).stream()
			.filter(t -> t.isDefinition())
			.filter(t -> t.isObject())
			.map(t -> (UserDefinedObjectType) t)
			.forEach(t -> t.getProperties().stream()
							.filter(p -> p.getType().isReference())
							.map(p -> (RamlReferenceType) p.getType())
							.forEach(r -> r.setReferencedType(typeModel.getType(r.getReferencedTypeName())))
					);
	}
	
}

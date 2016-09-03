package com.mutsys.chimera.raml.type.factory;

import java.util.stream.Stream;

import org.raml.v2.api.model.v10.datamodel.ObjectTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

import com.mutsys.chimera.raml.type.Property;
import com.mutsys.chimera.raml.type.UserDefinedObjectType;
import com.mutsys.chimera.raml.type.UserDefinedRamlType;

public class UserDefinedObjectTypeFactory implements UserDefinedRamlTypeFactory {

	@Override
	public UserDefinedRamlType create(TypeDeclaration typeDeclaration) {
		UserDefinedObjectType objectType = new UserDefinedObjectType();
		objectType.setTypeName(typeDeclaration.name());
		objectType.setSuperType(typeDeclaration.type());
		createProperties((ObjectTypeDeclaration) typeDeclaration).forEach(objectType::addProperty);
		objectType.setJavaClassName(getAnnotation((ObjectTypeDeclaration) typeDeclaration, "javaClassName"));
		objectType.setJavaPackageName(getAnnotation((ObjectTypeDeclaration) typeDeclaration, "javaPackageName"));
		return objectType;
	}
	
	protected Stream<Property> createProperties(ObjectTypeDeclaration typeDeclaration) {
		return typeDeclaration.properties().stream().map(this::createProperty);
	}
	
	protected Property createProperty(TypeDeclaration typeDeclaration) {
		Property property = new Property();
		property.setName(typeDeclaration.name());
		property.setType(typeDeclaration.type());
		property.isRequired(typeDeclaration.required());
		return property;
	}
	
	protected String getAnnotation(ObjectTypeDeclaration typeDeclaration, String annotationName) {
		return typeDeclaration.annotations().stream()
			.filter(a -> a.annotation().name().equals(annotationName))
			.findFirst()
			.map(a -> a.structuredValue().value().toString())
			.orElse("");
	}

}

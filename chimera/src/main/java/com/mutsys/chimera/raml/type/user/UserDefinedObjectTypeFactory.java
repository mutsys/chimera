package com.mutsys.chimera.raml.type.user;

import java.util.stream.Stream;

import org.raml.v2.api.model.v10.datamodel.ArrayTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.ObjectTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;

import com.mutsys.chimera.raml.type.BuiltInRamlType;
import com.mutsys.chimera.raml.type.RamlArrayType;
import com.mutsys.chimera.raml.type.RamlTypeFamily;
import com.mutsys.chimera.raml.type.RamlTypeIntrospector;
import com.mutsys.chimera.raml.type.RamlTypeReference;

public class UserDefinedObjectTypeFactory implements UserDefinedRamlTypeFactory {

	@Override
	public UserDefinedRamlType create(TypeDeclaration typeDeclaration) {
		UserDefinedObjectType objectType = new UserDefinedObjectType();
		objectType.setTypeName(typeDeclaration.name());
		objectType.setSuperType(typeDeclaration.type());
		ObjectTypeDeclaration objectTypeDeclaration = (ObjectTypeDeclaration) typeDeclaration;
		createProperties(objectTypeDeclaration).forEach(objectType::addProperty);
		objectType.setJavaClassType(getAnnotation(objectTypeDeclaration, "javaClassType"));
		objectType.setJavaClassName(getAnnotation(objectTypeDeclaration, "javaClassName"));
		objectType.setJavaPackageName(getAnnotation(objectTypeDeclaration, "javaPackageName"));
		return objectType;
	}
	
	protected Stream<RamlUserTypeProperty> createProperties(ObjectTypeDeclaration typeDeclaration) {
		return typeDeclaration.properties().stream().map(this::createProperty);
	}
	
	protected RamlUserTypeProperty createProperty(TypeDeclaration typeDeclaration) {		
		RamlUserTypeProperty property = new RamlUserTypeProperty();
		property.setName(typeDeclaration.name());
		property.isRequired(typeDeclaration.required());
		RamlTypeFamily typeFamily = RamlTypeIntrospector.getRamlTypeFamily(typeDeclaration);
		if (RamlTypeFamily.SCALAR.equals(typeFamily)) {
			if (RamlTypeIntrospector.isBuiltInRamlType(typeDeclaration)) {
				property.setType(BuiltInRamlType.getType(typeDeclaration.type()));
			} else {
				RamlTypeReference typeReference = new RamlTypeReference();
				typeReference.setReferencedTypeName(typeDeclaration.type());
				property.setType(typeReference);
			}
		}
		if (RamlTypeFamily.ARRAY.equals(typeFamily)) {
			ArrayTypeDeclaration arrayDeclaration = (ArrayTypeDeclaration) typeDeclaration;
			String memberTypeName = arrayDeclaration.items().name();
			RamlArrayType arrayType = new RamlArrayType();
			arrayType.setReferencedTypeName(memberTypeName);
			property.setType(arrayType);
		}
		if (RamlTypeFamily.OBJECT.equals(typeFamily)) {
			RamlTypeReference typeReference = new RamlTypeReference();
			typeReference.setReferencedTypeName(typeDeclaration.type());
			property.setType(typeReference);
		}
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

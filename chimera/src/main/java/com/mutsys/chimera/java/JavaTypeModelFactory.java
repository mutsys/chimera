package com.mutsys.chimera.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.type.JavaClassType;
import com.mutsys.chimera.java.type.JavaInterface;
import com.mutsys.chimera.java.type.JavaProperty;
import com.mutsys.chimera.java.type.JavaPropertyCardinality;
import com.mutsys.chimera.java.type.JavaTypeReference;
import com.mutsys.chimera.java.type.ProvidedJavaType;
import com.mutsys.chimera.java.type.RamlJavaType;
import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.type.BuiltInRamlType;
import com.mutsys.chimera.raml.type.RamlType;
import com.mutsys.chimera.raml.type.RamlTypeDefinition;
import com.mutsys.chimera.raml.type.RamlTypeReference;
import com.mutsys.chimera.raml.type.RamlArrayType;
import com.mutsys.chimera.raml.type.user.RamlUserTypeProperty;
import com.mutsys.chimera.raml.type.user.UserDefinedObjectType;
import com.mutsys.chimera.raml.type.user.UserDefinedRamlType;

public class JavaTypeModelFactory {
	
	public static JavaTypeModel create(RamlTypeModel ramlTypeModel) {
		JavaTypeModel javaTypeModel = new JavaTypeModel();
		createPackages(javaTypeModel, ramlTypeModel);
		createJavaTypes(javaTypeModel, ramlTypeModel);
		return javaTypeModel;
	}
	
	protected static void createPackages(JavaTypeModel javaTypeModel, RamlTypeModel ramlTypeModel) {
		ramlTypeModel.getTypes(false).stream()
			.filter(t -> t.isDefinition())
			.map(t -> (UserDefinedRamlType) t)
			.map(t -> t.getJavaPackageName())
			.distinct()
			.map(p -> p.split("\\."))
			.flatMap(p ->
				IntStream.rangeClosed(1, p.length)
					.mapToObj(i -> Arrays.copyOfRange(p, 0, i))
					.map(a -> String.join(".", a)))
			.distinct()
			.sorted()
			.forEach(p -> createPackage(javaTypeModel, p));
	}
	
	protected static void createPackage(JavaTypeModel javaTypeModel, String packageName) {
		String[] packageNameSegments = packageName.split("\\.");
		int numberOfSegments = packageNameSegments.length;
		if (numberOfSegments == 1) {
			JavaPackage javaPackage = new JavaPackage();
			javaPackage.setName(packageName);
			javaTypeModel.addSubPackage(javaPackage);
		} else {
			String lastPackageNameSegment = packageNameSegments[numberOfSegments-1];
			String parentPackageName = String.join(".", Arrays.copyOfRange(packageNameSegments, 0, numberOfSegments-1));
			JavaPackage parentPackage = javaTypeModel.getPackage(parentPackageName);
			JavaPackage javaPackage = new JavaPackage();
			javaPackage.setName(lastPackageNameSegment);
			parentPackage.addSubPackage(javaPackage);
		}
	}
	
	protected static void createJavaTypes(JavaTypeModel javaTypeModel, RamlTypeModel ramlTypeModel) {
		ramlTypeModel.getTypes(false).stream()
			.filter(t -> t.isDefinition())
			.map(t -> (UserDefinedRamlType) t)
			.forEach(t -> createJavaType(javaTypeModel, t));	
	}
	
	protected static void createJavaType(JavaTypeModel javaTypeModel, UserDefinedRamlType ramlType) {
		JavaClassType javaClassType = JavaClassType.getType(ramlType.getJavaClassType());
		if (javaClassType.equals(JavaClassType.INTERFACE)) {
			createJavaInterface(javaTypeModel, (UserDefinedObjectType) ramlType);
		}
	}
	
	protected static void createJavaInterface(JavaTypeModel javaTypeModel, UserDefinedObjectType ramlType) {
		JavaInterface javaInterface = new JavaInterface();
		javaInterface.setName(ramlType.getJavaClassName());
		javaTypeModel.getPackage(ramlType.getJavaPackageName()).addClass(javaInterface);
		for (RamlUserTypeProperty ramlProperty : getDefinedProperties(ramlType)) {
			javaInterface.addProperty(createJavaProperty(ramlProperty));
		}
		if (Objects.nonNull(ramlType.getSuperType())) {
			RamlTypeDefinition ramlSuperType = ramlType.getTypeRegistry().getType(ramlType.getSuperType());
			if (ramlSuperType.isDefinition()) {
				UserDefinedObjectType userDefinedSuperType = (UserDefinedObjectType) ramlSuperType;
				JavaTypeReference superInterface = new JavaTypeReference();
				superInterface.setName(userDefinedSuperType.getJavaClassName());
				superInterface.setPackageName(userDefinedSuperType.getJavaPackageName());
				javaInterface.addExtendedInterface(superInterface);
			}
		}
	}
	
	protected static List<RamlUserTypeProperty> getDefinedProperties(UserDefinedObjectType ramlType) {
		Set<String> inheritedProperties = getInheritedProperties(ramlType);
		List<String> definedProperties = ramlType.getPropertyNames();
		definedProperties.removeAll(inheritedProperties);
		return definedProperties.stream().map(p -> ramlType.getProperty(p)).collect(Collectors.toList());
	}
	
	protected static UserDefinedObjectType getSuperType(UserDefinedObjectType ramlType) {
		RamlType superType = ramlType.getTypeRegistry().getType(ramlType.getSuperType());
		if (superType.isDefinition()) {
			return (UserDefinedObjectType) superType;
		}
		return null;
	}
	
	protected static Set<String> getInheritedProperties(UserDefinedObjectType ramlType) {
		Set<String> inheritedProperties = new HashSet<>();
		UserDefinedObjectType superType = getSuperType(ramlType);
		while (superType != null && !superType.isBuiltInType()) {
			inheritedProperties.addAll(superType.getPropertyNames());
			superType = getSuperType(superType);
		}
		return inheritedProperties;
	}
	
	protected static JavaProperty createJavaProperty(RamlUserTypeProperty ramlProperty) {
		JavaProperty javaProperty = new JavaProperty();
		javaProperty.setName(ramlProperty.getName());
		RamlType ramlType = ramlProperty.getType();
		JavaPropertyCardinality cardinality = JavaPropertyCardinality.getCardinality(ramlType.getTypeFamily());
		javaProperty.setCardinality(cardinality);
		if (cardinality.equals(JavaPropertyCardinality.LIST)) {
			populateListProperty(javaProperty, (RamlArrayType) ramlType);
		} else {
			javaProperty.setJavaType(createJavaType(ramlType));
		}
		return javaProperty;
	}
	
	protected static void populateListProperty(JavaProperty javaProperty, RamlArrayType ramlType) {
		RamlType memberType = ramlType.getReferencedType();
		javaProperty.setJavaType(createJavaType(memberType));
	}
	
	protected static RamlJavaType createJavaType(RamlType ramlType) {
		if (ramlType.isBuiltInType()) {
			BuiltInRamlType builtInType = (BuiltInRamlType) ramlType;
			ProvidedJavaType propertyType = new ProvidedJavaType();
			propertyType.setProvidedClass(builtInType.getJavaClass());
			return propertyType;
		}
		if (ramlType.isDefinition()) {
			UserDefinedObjectType typeDefinition = (UserDefinedObjectType) ramlType;
			JavaTypeReference propertyType = new JavaTypeReference();
			propertyType.setName(typeDefinition.getJavaClassName());
			propertyType.setPackageName(typeDefinition.getJavaPackageName());
			return propertyType;
		}
		if (ramlType.isReference()) {
			RamlTypeReference typeReference = (RamlTypeReference) ramlType;
			return createJavaType(typeReference.getReferencedType());
		}
		return null;
	}

}

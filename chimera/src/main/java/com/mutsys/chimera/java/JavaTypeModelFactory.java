package com.mutsys.chimera.java;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.type.JavaClassType;
import com.mutsys.chimera.java.type.JavaInterface;
import com.mutsys.chimera.java.type.JavaProperty;
import com.mutsys.chimera.java.type.JavaTypeReference;
import com.mutsys.chimera.java.type.ProvidedJavaType;
import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.type.BuiltInRamlType;
import com.mutsys.chimera.raml.type.RamlType;
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
		for (RamlUserTypeProperty property : ramlType.getProperties()) {
			RamlType propertyType = property.getType();
			if (propertyType.isBuiltInType()) {
				javaInterface.addProperty(createBuiltInProperty(property.getName(), (BuiltInRamlType) propertyType));
			}
			if (propertyType.isDefinition()) {
				javaInterface.addProperty(createTypeReferenceProperty(property.getName(), (UserDefinedRamlType) propertyType));
			}
		}		
		
	}
	
	protected static JavaProperty createBuiltInProperty(String propertyName, BuiltInRamlType ramlType) {
		JavaProperty javaProperty = new JavaProperty();
		javaProperty.setName(propertyName);
		ProvidedJavaType propertyType = new ProvidedJavaType();
		propertyType.setProvidedClass(ramlType.getJavaClass());
		javaProperty.setJavaType(propertyType);
		return javaProperty;
	}
	
	protected static JavaProperty createTypeReferenceProperty(String propertyName, UserDefinedRamlType ramlType) {
		JavaProperty javaProperty = new JavaProperty();
		javaProperty.setName(propertyName);
		JavaTypeReference propertyType = new JavaTypeReference();
		propertyType.setName(ramlType.getJavaClassName());
		propertyType.setPackageName(ramlType.getJavaPackageName());
		javaProperty.setJavaType(propertyType);
		return javaProperty;
	}

}

package com.mutsys.chimera.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.jboss.forge.roaster._shade.org.eclipse.jdt.core.JavaCore;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster._shade.org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.source.Importer;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.forge.roaster.model.source.PropertyHolderSource;
import org.jboss.forge.roaster.model.source.PropertySource;
import org.jboss.forge.roaster.model.util.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mutsys.chimera.java.JavaTypeModel;
import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.type.JavaProperty;
import com.mutsys.chimera.java.type.RamlJavaType;
import com.mutsys.chimera.java.type.UserDefinedJavaType;

public class JavaCodeFactory {

	private final static Logger LOG = LoggerFactory.getLogger(JavaCodeFactory.class);
	
	public static void generateJavaCode(JavaTypeModel typeModel, String destDir) {
		Map<String,JavaSource<?>> generatedJavaTypeMap = new HashMap<>();
		createClasses(generatedJavaTypeMap, typeModel);
		writeGeneratedCode(generatedJavaTypeMap, destDir);
	}
	
	protected static void writeGeneratedCode(Map<String,JavaSource<?>> generatedJavaTypeMap, String destDir) {
		Properties codeFormatProps = new Properties();
		codeFormatProps.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_PACKAGE, "1");
		codeFormatProps.setProperty(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
		
		File outputDir = new File(destDir);
		outputDir.mkdirs();
		for (JavaSource<?> javaSource : generatedJavaTypeMap.values()) {
			String packageName = javaSource.getPackage();
			File packageDir = Paths.get(outputDir.getPath(), packageName.split("\\.")).toFile();
			packageDir.mkdirs();
			File sourceFile = Paths.get(packageDir.getPath(), javaSource.getName() + ".java").toFile();
			if (sourceFile.exists()) {
				sourceFile.delete();
			}
			try {
				BufferedWriter writer = Files.newBufferedWriter(sourceFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
				writer.write(Formatter.format(codeFormatProps, javaSource.toString()));
				writer.close();
			} catch (IOException e) {
				LOG.error("unable to create writer for " + javaSource.getQualifiedName() + ".java", e);
			}

			
		}
	}
	
	protected abstract static class JavaTypeModelVisitor<T> {
		
		private List<T> collectedStuff = new ArrayList<>();
		
		public List<T> visit(JavaTypeModel typeModel) {
			visit(typeModel.getPackages());
			return collectedStuff;
		}
		
		protected void visit(List<JavaPackage> packages) {
			for (JavaPackage pakkage : packages) {
				collect(collectedStuff, pakkage);
				visit(pakkage.getSubPackages());
			}
		}
		
		public abstract void collect(List<T> collection, JavaPackage pakkage);
		
	}
	
	protected static void createClasses(Map<String,JavaSource<?>> generatedJavaTypeMap, JavaTypeModel typeModel) {
		new JavaTypeModelVisitor<UserDefinedJavaType>() {

			@Override
			public void collect(List<UserDefinedJavaType> collection, JavaPackage pakkage) {
				for (UserDefinedJavaType javaType : pakkage.getClasses()) {
					collection.add(javaType);
				}
				
			}
			
		}.visit(typeModel).forEach(t -> {
			JavaSource<?> javaType = getUserDefinedType(generatedJavaTypeMap, t);
			populateImports(javaType, t);
			if (t.isInterface()) {
				populateProperties(generatedJavaTypeMap, (PropertyHolderSource<?>) javaType, t);
			}
		});
	}
	
	protected static void populateProperties(Map<String,JavaSource<?>> generatedJavaTypeMap, PropertyHolderSource<?> propertyHolder, UserDefinedJavaType userDefinedJavaType) {
		for (JavaProperty property : userDefinedJavaType.getProperties()) {
			RamlJavaType propertyType = property.getJavaType();
			PropertySource<?> propertySource;
			if (propertyType.isUserDefined()) {
				JavaType<?> generatedJavaType = getUserDefinedType(generatedJavaTypeMap, (UserDefinedJavaType) propertyType);
				propertySource = propertyHolder.addProperty(generatedJavaType, property.getName());
			} else {
				propertySource = propertyHolder.addProperty(propertyType.getName(), property.getName());
			}
			propertySource.getAccessor().addAnnotation(JsonProperty.class);
			propertySource.getMutator().addAnnotation(JsonProperty.class);
		}
	}
	
	protected static void populateImports(Importer<?> importer, UserDefinedJavaType userDefinedJavaType) {
		for (JavaProperty property : userDefinedJavaType.getProperties()) {
			RamlJavaType propertyType = property.getJavaType();
			String className = propertyType.getCanonicalName();
			if (importer.requiresImport(className) && !importer.hasImport(className)) {
				importer.addImport(className);
			}
		}
	}
	
	protected static JavaSource<?> getUserDefinedType(Map<String,JavaSource<?>> generatedJavaTypeMap, UserDefinedJavaType userDefinedJavaType) {
		JavaSource<?> javaType = generatedJavaTypeMap.get(userDefinedJavaType.getCanonicalName());
		if (Objects.nonNull(javaType)) {
			return javaType;
		}
		String userDefinedJavaTypeName = userDefinedJavaType.getName();
		String userDefinedJavaTypePackageName = userDefinedJavaType.getPackageName();
		if (userDefinedJavaType.isInterface()) {
			javaType = Roaster.create(JavaInterfaceSource.class);
		}
		if (userDefinedJavaType.isAbstractClass()) {
			javaType = Roaster.create(JavaClassSource.class).setAbstract(true);
		}
		if (userDefinedJavaType.isConcreteClass()) {
			javaType = Roaster.create(JavaClassSource.class).setAbstract(false);
		}
		javaType.setPublic().setPackage(userDefinedJavaTypePackageName).setName(userDefinedJavaTypeName);
		String userDefinedJavaTypeCanonicalName = userDefinedJavaType.getCanonicalName();
		generatedJavaTypeMap.put(userDefinedJavaTypeCanonicalName, javaType);
		return javaType;
	}
	
}

package com.mutsys.chimera.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster._shade.org.eclipse.jdt.core.JavaCore;
import org.jboss.forge.roaster._shade.org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.impl.MethodImpl;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.Importer;
import org.jboss.forge.roaster.model.source.InterfaceCapableSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.JavaSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;
import org.jboss.forge.roaster.model.source.PropertyHolderSource;
import org.jboss.forge.roaster.model.source.PropertySource;
import org.jboss.forge.roaster.model.util.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mutsys.chimera.java.JavaModel;
import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.resource.JavaResourceMethod;
import com.mutsys.chimera.java.resource.JavaResourceMethodArgument;
import com.mutsys.chimera.java.resource.JavaResourceModel;
import com.mutsys.chimera.java.type.JavaClass;
import com.mutsys.chimera.java.type.JavaInterface;
import com.mutsys.chimera.java.type.JavaProperty;
import com.mutsys.chimera.java.type.JavaPropertyCardinality;
import com.mutsys.chimera.java.type.JavaTypeModel;
import com.mutsys.chimera.java.type.JavaTypeReference;
import com.mutsys.chimera.java.type.RamlJavaType;
import com.mutsys.chimera.java.type.UserDefinedJavaType;

public class JavaCodeFactory {

	private final static Logger LOG = LoggerFactory.getLogger(JavaCodeFactory.class);
	
	public static void generateJavaCode(JavaTypeModel typeModel, String destDir) {
		Map<String,JavaSource<?>> generatedJavaTypeMap = new HashMap<>();
		createClasses(generatedJavaTypeMap, typeModel);
		writeGeneratedCode(generatedJavaTypeMap, destDir);
	}
	
	public static void generateJavaCode(JavaModel javaModel, String destDir) {
		Map<String,JavaSource<?>> generatedJavaTypeMap = new HashMap<>();
		createClasses(generatedJavaTypeMap, javaModel.getTypeModel());
		createResources(generatedJavaTypeMap, javaModel.getResourceModel());
		writeGeneratedCode(generatedJavaTypeMap, destDir);
	}
	
	protected static void writeGeneratedCode(Map<String,JavaSource<?>> generatedJavaTypeMap, String destDir) {
		Properties codeFormatProps = new Properties();
		codeFormatProps.setProperty(DefaultCodeFormatterConstants.FORMATTER_BLANK_LINES_AFTER_PACKAGE, "1");
		codeFormatProps.setProperty(DefaultCodeFormatterConstants.FORMATTER_TAB_CHAR, JavaCore.SPACE);
		
		File outputDir = new File(destDir);
		if (outputDir.exists()) {
			try {
				Files.walkFileTree(outputDir.toPath(), new FileVisitor<Path>() {
	
					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
						return FileVisitResult.CONTINUE;
					}
	
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						file.toFile().delete();
						return FileVisitResult.CONTINUE;
					}
	
					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}
	
					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						dir.toFile().delete();
						return FileVisitResult.CONTINUE;
					}
					
				});
			} catch (IOException e) {
				LOG.error("unable to clean code generation destination directory", e);
			}
		}
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
	
	protected static void createResources(Map<String,JavaSource<?>> generatedJavaTypeMap, JavaResourceModel resourceModel) {
		String resourceClassName = resourceModel.getName();
		String resourcePackageName = resourceModel.getPackageName();
		JavaInterfaceSource resourceClass = Roaster.create(JavaInterfaceSource.class);
		resourceClass.setPublic().setPackage(resourcePackageName).setName(resourceClassName);
		AnnotationSource<?> path = resourceClass.addAnnotation();
		path.setName(JAX_RS_PATH);
		path.setStringValue(resourceModel.getResourcePath());
		String resourceClassCanonicalName = resourceModel.getCanonicalName();
		generatedJavaTypeMap.put(resourceClassCanonicalName, resourceClass);
		addImport(resourceClass, JAX_RS_CORE_PACKAGE, JAX_RS_RESPONSE);
		addImport(resourceClass, JAX_RS_PACKAGE, JAX_RS_PATH);
		populateResourceMethods(resourceClass, resourceModel);
	}
	
	protected static void populateResourceMethods(JavaInterfaceSource resourceClass, JavaResourceModel resourceModel) {
		for (JavaResourceMethod resourceMethod : resourceModel.getResourceMethods()) {
			MethodSource<?> resourceMethodSource = createResourceMethod(resourceClass, resourceMethod);
			resourceClass.addMethod(resourceMethodSource);
		}
		for (JavaResourceModel subResourceModel : resourceModel.getSubResources()) {
			populateResourceMethods(resourceClass, subResourceModel);
		}
	}
	
	private final static String JAX_RS_PACKAGE = "javax.ws.rs";
	private final static String JAX_RS_CORE_PACKAGE = "javax.ws.rs.core";
	
	private final static String JAX_RS_RESPONSE = "Response";
	private final static String JAX_RS_CONSUMES = "Consumes";
	private final static String JAX_RS_PRODUCES = "Produces";
	private final static String JAX_RS_PATH = "Path";
	private final static String JAX_RS_PATH_PARAM = "PathParam";
	
	protected static MethodSource<?> createResourceMethod(JavaInterfaceSource resourceClass, JavaResourceMethod resourceMethod) {
		MethodImpl<JavaInterfaceSource> methodSource = new MethodImpl<>(resourceClass);
		methodSource.setName(resourceMethod.getName());
		methodSource.setReturnType(JAX_RS_RESPONSE);
		String httpMethodAnnotation = resourceMethod.getHttpMethod();
		methodSource.addAnnotation(httpMethodAnnotation);
		addImport(resourceClass, JAX_RS_PACKAGE, httpMethodAnnotation);
		JavaResourceModel methodResource = resourceMethod.getResource();
		if (!methodResource.isRootResource()) {
			AnnotationSource<?> path = methodSource.addAnnotation();
			path.setName(JAX_RS_PATH);
			path.setStringValue(methodResource.getResourcePath());
		}
		resourceMethod.getConsumesMediaType().ifPresent(mt -> {
			addImport(resourceClass, JAX_RS_PACKAGE, JAX_RS_CONSUMES);
			AnnotationSource<?> consumes = methodSource.addAnnotation();
			consumes.setName(JAX_RS_CONSUMES);
			consumes.setStringValue(mt);
		});
		resourceMethod.getProducesMediaType().ifPresent(mt -> {
			addImport(resourceClass, JAX_RS_PACKAGE, JAX_RS_PRODUCES);
			AnnotationSource<?> produces = methodSource.addAnnotation();
			produces.setName(JAX_RS_PRODUCES);
			produces.setStringValue(mt);
		});
		for (JavaResourceMethodArgument argument : resourceMethod.getArguments()) {
			String argumentType = argument.getArgumentJavaType().getName();
			String argumentName = argument.getArgumentName();
			ParameterSource<?> parameterSource = methodSource.addParameter(argumentType, argumentName);
			addImport(resourceClass, argument.getArgumentJavaType().getCanonicalName());
			if (argument.isPathParameter()) {
				addImport(resourceClass, JAX_RS_PACKAGE, JAX_RS_PATH_PARAM);
				AnnotationSource<?> pathParam = parameterSource.addAnnotation();
				pathParam.setName(JAX_RS_PATH_PARAM);
				pathParam.setStringValue(argumentName);
			}
		}
		return methodSource;
	}
	
	protected static void createClasses(Map<String,JavaSource<?>> generatedJavaTypeMap, JavaTypeModel typeModel) {
		List<UserDefinedJavaType> javaTypes =
			new JavaTypeModelVisitor<UserDefinedJavaType>() {
	
				@Override
				public void collect(List<UserDefinedJavaType> collection, JavaPackage pakkage) {
					for (UserDefinedJavaType javaType : pakkage.getClasses()) {
						collection.add(javaType);
					}
					
				}
				
			}.visit(typeModel);
		
		javaTypes.forEach(t -> getUserDefinedType(generatedJavaTypeMap, t));
				
		javaTypes.forEach(t -> {
			JavaSource<?> javaType = getUserDefinedType(generatedJavaTypeMap, t);
			populateImports(javaType, t);
		});
		
		javaTypes.forEach(t -> {
			JavaSource<?> javaType = getUserDefinedType(generatedJavaTypeMap, t);
			if (t.isInterface()) {
				populateProperties(generatedJavaTypeMap, (PropertyHolderSource<?>) javaType, t);
			}
		});
		
		javaTypes.forEach(t -> {
			JavaSource<?> javaType = getUserDefinedType(generatedJavaTypeMap, t);
			implementInterfaces((InterfaceCapableSource<?>) javaType, t);
		});
		
	}
	
	protected static void populateProperties(Map<String,JavaSource<?>> generatedJavaTypeMap, PropertyHolderSource<?> propertyHolder, UserDefinedJavaType userDefinedJavaType) {
		for (JavaProperty property : userDefinedJavaType.getProperties()) {
			RamlJavaType propertyType = property.getJavaType();
			PropertySource<?> propertySource;
			if (property.getCardinality().equals(JavaPropertyCardinality.OBJECT)) {
				JavaType<?> generatedJavaType = getUserDefinedType(generatedJavaTypeMap, (UserDefinedJavaType) propertyType);
				propertySource = propertyHolder.addProperty(generatedJavaType, property.getName());
			} else if (property.getCardinality().equals(JavaPropertyCardinality.LIST)) {
				JavaTypeReference memberTypeReference = (JavaTypeReference) propertyType;
				JavaType<?> memberJavaType = generatedJavaTypeMap.get(memberTypeReference.getCanonicalName());
				propertySource = propertyHolder.addProperty("List<" + memberJavaType.getName() + ">", property.getName());
			} else {
				propertySource = propertyHolder.addProperty(propertyType.getName(), property.getName());
			}
			propertySource.getAccessor().addAnnotation(JsonProperty.class);
			propertySource.getMutator().addAnnotation(JsonProperty.class);
		}
	}
	
	private static final String LIST_CLASS_NAME = "java.util.List";
	
	protected static void populateImports(Importer<?> importer, UserDefinedJavaType userDefinedJavaType) {
		for (JavaProperty property : userDefinedJavaType.getProperties()) {
			RamlJavaType propertyType = property.getJavaType();
			String className = propertyType.getCanonicalName();
			addImport(importer, className);
			if (property.getCardinality().equals(JavaPropertyCardinality.LIST)) {
				addImport(importer, LIST_CLASS_NAME);
			}
		}
	}
	
	protected static void implementInterfaces(InterfaceCapableSource<?> implementor, UserDefinedJavaType userDefinedJavaType) {
		if (userDefinedJavaType.isInterface()) {
			JavaInterface javaInterface = (JavaInterface) userDefinedJavaType;
			implementInterfaces(implementor, javaInterface.getExtendedInterfaces());
		}
		if (userDefinedJavaType.isAbstractClass() || userDefinedJavaType.isConcreteClass()) {
			JavaClass javaClass = (JavaClass) userDefinedJavaType;
			implementInterfaces(implementor, javaClass.getImplementedInterfaces());
		}
	}
	
	protected static void implementInterfaces(InterfaceCapableSource<?> implementor, List<JavaTypeReference> interfaces) {
		interfaces.forEach(i -> {
			implementor.addInterface(i.getCanonicalName());
		});
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
	
	protected static void addImport(Importer<?> importer, String className) {
		if (importer.requiresImport(className) && !importer.hasImport(className)) {
			importer.addImport(className);
		}
	}
	
	protected static void addImport(Importer<?> importer, String packageName, String shortClassName) {
		String className = new StringBuilder(packageName).append(".").append(shortClassName).toString();
		addImport(importer, className);
	}
	
}

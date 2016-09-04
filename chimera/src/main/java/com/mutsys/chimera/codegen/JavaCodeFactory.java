package com.mutsys.chimera.codegen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.EClassType;
import com.helger.jcodemodel.JClassAlreadyExistsException;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JMethod;
import com.helger.jcodemodel.JMod;
import com.mutsys.chimera.java.JavaTypeModel;
import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.type.JavaProperty;
import com.mutsys.chimera.java.type.JavaType;
import com.mutsys.chimera.java.type.UserDefinedJavaType;

public class JavaCodeFactory {
	
	private final static Logger LOG = LoggerFactory.getLogger(JavaCodeFactory.class);
	
	public static void generateJavaCode(JavaTypeModel typeModel, String destDir) {
		JCodeModel codeModel = new JCodeModel();
		createPackages(codeModel, typeModel);
		createClasses(codeModel, typeModel);
		try {
			codeModel.build(new File(destDir));
		} catch (IOException e) {
			LOG.error("unable to generate code from model", e);
		}
	}
	
	protected static void createPackages(JCodeModel codeModel, JavaTypeModel typeModel) {
		new JavaTypeModelVisitor<JavaPackage>() {

			@Override
			public void collect(List<JavaPackage> collection, JavaPackage pakkage) {
				if (pakkage.hasClasses()) {
					collection.add(pakkage);
				}
			}
			
		}.visit(typeModel).forEach(p -> codeModel._package(p.getCanonicalName()));
	}
	
	protected static void createClasses(JCodeModel codeModel, JavaTypeModel typeModel) {
		new JavaTypeModelVisitor<UserDefinedJavaType>() {

			@Override
			public void collect(List<UserDefinedJavaType> collection, JavaPackage pakkage) {
				for (UserDefinedJavaType javaType : pakkage.getClasses()) {
					collection.add(javaType);
				}
				
			}
			
		}.visit(typeModel).forEach(t -> {
			JDefinedClass javaClass = getUserDefinedType(codeModel, t);
			if (t.isInterface()) {
				for (JavaProperty property : t.getProperties()) {
					AbstractJType propertyType = getTypeReference(codeModel, property.getJavaType());
					JMethod getterMethod = javaClass.method(JMod.NONE, propertyType, getGetterName(property.getName()));
					getterMethod.annotate(JsonProperty.class);
					JMethod setterMethod = javaClass.method(JMod.NONE, codeModel.VOID, getSetterName(property.getName()));
					setterMethod.annotate(JsonProperty.class);
					setterMethod.param(propertyType, property.getName());
				}
			}
		});
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
	
	protected static String getGetterName(String propertyName) {
		return new StringBuilder("get").append(getMethodNameBaseForProperty(propertyName)).toString();
	}
	
	protected static String getSetterName(String propertyName) {
		return new StringBuilder("set").append(getMethodNameBaseForProperty(propertyName)).toString();
	}
	
	private static String getMethodNameBaseForProperty(String propertyName) {
		String firstLetter = propertyName.substring(0, 1);
		String theRestOfThePropertyName = propertyName.substring(1);
		return new StringBuilder(firstLetter.toUpperCase()).append(theRestOfThePropertyName).toString();
	}
	
	protected static JDefinedClass getUserDefinedType(JCodeModel codeModel, UserDefinedJavaType javaType) {
		JDefinedClass javaClass = codeModel._getClass(javaType.getCanonicalName());
		if (Objects.nonNull(javaClass)) {
			return javaClass;
		}
		try {
			if (javaType.isInterface()) {
				return codeModel._class(JMod.PUBLIC, javaType.getCanonicalName(), EClassType.INTERFACE);
			}
			if (javaType.isAbstractClass()) {
				return codeModel._class(JMod.PUBLIC | JMod.ABSTRACT, javaType.getCanonicalName(), EClassType.CLASS);
			}
			if (javaType.isConcreteClass()) {
				return codeModel._class(JMod.PUBLIC, javaType.getCanonicalName(), EClassType.CLASS);
			}
		} catch (JClassAlreadyExistsException e) {
			LOG.error("we were just told that a class already exists immediately after we were told it DOESN'T exist", e);
		}
		return null;
	}
	
	protected static AbstractJType getTypeReference(JCodeModel codeModel, JavaType javaType) {
		if (javaType.isPrimitiveType()) {
			return AbstractJType.parse(codeModel, javaType.getName());
		}
		if (javaType.isAutoboxedType() || javaType.isProvided()) {
			return codeModel.ref(javaType.getCanonicalName());
		}		
		return getUserDefinedType(codeModel, (UserDefinedJavaType) javaType);
	}

}

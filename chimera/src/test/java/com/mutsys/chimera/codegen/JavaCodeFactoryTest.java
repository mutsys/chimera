package com.mutsys.chimera.codegen;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.lang.reflect.Method;

import org.junit.Test;

import com.mutsys.chimera.codegen.compile.GeneratedCodeJavaCompiler;
import com.mutsys.chimera.java.JavaTypeModel;
import com.mutsys.chimera.java.JavaTypeModelFactory;
import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.RamlTypeModelFactory;


public class JavaCodeFactoryTest {
	
	@Test
	public void shouldGenerateJavaFromExampleOne() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
		
		JavaCodeFactory.generateJavaCode(javaTypeModel, "target/generated/java");
		
		GeneratedCodeJavaCompiler compiler = new GeneratedCodeJavaCompiler("target/generated/java");
		ClassLoader generatedCodeClassLoader = compiler.compileGeneratedSource();
		
		Class<?> generatedClass = generatedCodeClassLoader.loadClass("com.mutsys.exampleOne.Product");
		
		assertThat(generatedClass, is(not(nullValue())));
		assertThat(generatedClass.isInterface(), is(equalTo(true)));
		
		Method getName = generatedClass.getMethod("getName");
		
		assertThat(getName, is(not(nullValue())));
		
		Method setName = generatedClass.getMethod("setName", String.class);
		
		assertThat(setName, is(not(nullValue())));
		
		Method getProductId = generatedClass.getMethod("getProductId");
		
		assertThat(getProductId, is(not(nullValue())));
		
		Method setProductId = generatedClass.getMethod("setProductId", Long.class);
		
		assertThat(setProductId, is(not(nullValue())));
		
	}
	
	@Test
	public void shouldGenerateJavaFromExampleTwo() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-two.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
		
		JavaCodeFactory.generateJavaCode(javaTypeModel, "target/generated/java");
		
		GeneratedCodeJavaCompiler compiler = new GeneratedCodeJavaCompiler("target/generated/java");
		ClassLoader generatedCodeClassLoader = compiler.compileGeneratedSource();
		
		Class<?> personClass = generatedCodeClassLoader.loadClass("com.mutsys.exampleTwo.Person");
		
		assertThat(personClass, is(not(nullValue())));
		assertThat(personClass.isInterface(), is(equalTo(true)));
		
		Method getName = personClass.getMethod("getName");
		
		assertThat(getName, is(not(nullValue())));
		
		Method setName = personClass.getMethod("setName", String.class);
		
		assertThat(setName, is(not(nullValue())));
		
		Class<?> managerClass = generatedCodeClassLoader.loadClass("com.mutsys.exampleTwo.Manager");
		
		assertThat(managerClass, is(not(nullValue())));
		assertThat(managerClass.isInterface(), is(equalTo(true)));
		assertThat(personClass.isAssignableFrom(managerClass), is(equalTo(true)));
		
	}

}

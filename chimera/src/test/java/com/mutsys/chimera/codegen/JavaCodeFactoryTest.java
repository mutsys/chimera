package com.mutsys.chimera.codegen;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.lang.reflect.Method;

import org.junit.Test;

import com.mutsys.chimera.java.JavaTypeModel;
import com.mutsys.chimera.java.JavaTypeModelFactory;
import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.RamlTypeModelFactory;


public class JavaCodeFactoryTest {
	
	@Test
	public void shouldGenerateJavaClassFromJavaTypeModel() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
		
		JavaCodeFactory.generateJavaCode(javaTypeModel, "target/generated/java");
		
		Class<?> generatedClass = Class.forName("com.mutsys.foo.Product");
		
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

}

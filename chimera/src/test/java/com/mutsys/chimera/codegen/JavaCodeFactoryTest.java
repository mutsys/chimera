package com.mutsys.chimera.codegen;

import org.junit.Test;

import com.mutsys.chimera.java.JavaTypeModel;
import com.mutsys.chimera.java.JavaTypeModelFactory;
import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.RamlTypeModelFactory;

public class JavaCodeFactoryTest {
	
	@Test
	public void foo() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
		
		JavaCodeFactory.generateJavaCode(javaTypeModel, "target/generated/java");
		
	}

}

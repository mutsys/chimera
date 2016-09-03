package com.mutsys.chimera.raml.type;

import org.junit.Test;

import com.mutsys.chimera.raml.RamlModelFactory;
import com.mutsys.chimera.raml.RamlTypeRegistry;

public class RamlTypeFactoryTest {
	
	@Test
	public void foo() throws Exception {
		
		RamlTypeRegistry typeRegistry = RamlModelFactory.createRamlApi("com/mutsys/chimera/raml/type/raml-example-one.raml");
		RamlType ramlType = typeRegistry.getType("Product");
		
		UserDefinedObjectType productType = (UserDefinedObjectType) ramlType;
		System.out.println(productType.getJavaPackageName() + "." + productType.getJavaClassName());
		for (Property prop : productType.getProperties()) {
			System.out.println(prop.getName() + " : " + prop.getType());
		}
		
	}

}

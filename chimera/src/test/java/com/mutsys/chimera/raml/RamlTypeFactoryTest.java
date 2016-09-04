package com.mutsys.chimera.raml;

import org.junit.Test;

import com.mutsys.chimera.raml.RamlTypeModelFactory;
import com.mutsys.chimera.raml.type.RamlType;
import com.mutsys.chimera.raml.type.user.RamlUserTypeProperty;
import com.mutsys.chimera.raml.type.user.UserDefinedObjectType;
import com.mutsys.chimera.raml.RamlTypeModel;

public class RamlTypeFactoryTest {
	
	@Test
	public void foo() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		RamlType ramlType = ramlTypeModel.getType("Product");
		
		UserDefinedObjectType productType = (UserDefinedObjectType) ramlType;
		System.out.println(productType.getJavaClassType());
		System.out.println(productType.getJavaPackageName() + "." + productType.getJavaClassName());
		for (RamlUserTypeProperty prop : productType.getProperties()) {
			System.out.println(prop.getName() + " : " + prop.getType().getTypeName());
		}
		
	}

}

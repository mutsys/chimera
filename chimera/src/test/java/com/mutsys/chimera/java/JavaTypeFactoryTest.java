package com.mutsys.chimera.java;

import java.util.List;

import org.junit.Test;

import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.type.JavaProperty;
import com.mutsys.chimera.java.type.UserDefinedJavaType;
import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.RamlTypeModelFactory;

public class JavaTypeFactoryTest {
	
	@Test
	public void foo() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
		
		printPackages(javaTypeModel.getSubPackages());
		
	}
	
	protected static void printPackages(List<JavaPackage> packages) {
		for (JavaPackage pakkage : packages) {
			System.out.println(pakkage.getCanonicalName());
			for (UserDefinedJavaType javaType : pakkage.getClasses()) {
				System.out.println("  " + javaType.getName());
				for (JavaProperty property : javaType.getProperties()) {
					System.out.println("    " + property.getName() + " : " + property.getJavaType().getName());
				}
			}
			printPackages(pakkage.getSubPackages());
		}
	}

}

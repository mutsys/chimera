package com.mutsys.chimera.java;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.type.JavaInterface;
import com.mutsys.chimera.java.type.JavaProperty;
import com.mutsys.chimera.java.type.JavaPropertyCardinality;
import com.mutsys.chimera.java.type.ProvidedJavaType;
import com.mutsys.chimera.java.type.UserDefinedJavaType;
import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.RamlTypeModelFactory;

public class JavaTypeFactoryTest {
	
	@Test
	public void shouldCreateJavaTypeModelFromExampleOne() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
				
		JavaPackage comMutsysExampleOne = javaTypeModel.getPackage("com.mutsys.exampleOne");
		
		assertThat(comMutsysExampleOne, is(not(nullValue())));
		assertThat(comMutsysExampleOne.getClasses(), hasSize(1));
		
		assertThat(comMutsysExampleOne.getClassNames(), contains("Product"));
		
		UserDefinedJavaType product = comMutsysExampleOne.getClass("Product");
		assertThat(product, is(not(nullValue())));
		assertThat(product.getProperties(), hasSize(2));
		assertThat(product.getPropertyNames(), containsInAnyOrder("productId", "name"));
		
		JavaProperty productId = product.getProperty("productId");
		assertThat(productId, is(not(nullValue())));
		assertThat(productId.getJavaType().isProvided(), is(equalTo(true)));
		
		ProvidedJavaType productIdType = (ProvidedJavaType) productId.getJavaType();
		assertThat(productIdType.getProvidedClass(), is(equalTo(Long.class)));
		
		JavaProperty name = product.getProperty("name");
		assertThat(name, is(not(nullValue())));
		assertThat(name.getJavaType().isProvided(), is(equalTo(true)));
		
		ProvidedJavaType nameType = (ProvidedJavaType) name.getJavaType();
		assertThat(nameType.getProvidedClass(), is(equalTo(String.class)));
		
	}
	
	@Test
	public void shouldCreateJavaTypeModelFromExampleTwo() throws Exception {
	
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-two.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
		
		JavaPackage comMutsysExampleTwo = javaTypeModel.getPackage("com.mutsys.exampleTwo");
		
		assertThat(comMutsysExampleTwo, is(not(nullValue())));
		assertThat(comMutsysExampleTwo.getClasses(), hasSize(2));
		
		assertThat(comMutsysExampleTwo.getClassNames(), containsInAnyOrder("Person", "Manager"));
		
		UserDefinedJavaType person = comMutsysExampleTwo.getClass("Person");
		assertThat(person, is(not(nullValue())));
		assertThat(person.getProperties(), hasSize(1));
		assertThat(person.getPropertyNames(), contains("name"));
		
		JavaProperty name = person.getProperty("name");
		assertThat(name, is(not(nullValue())));
		assertThat(name.getJavaType().isProvided(), is(equalTo(true)));
		
		ProvidedJavaType nameType = (ProvidedJavaType) name.getJavaType();
		assertThat(nameType.getProvidedClass(), is(equalTo(String.class)));
		
		UserDefinedJavaType manager = comMutsysExampleTwo.getClass("Manager");
		assertThat(manager, is(not(nullValue())));
		assertThat(manager.isInterface(), is(equalTo(true)));
		
		JavaInterface managerInterface = (JavaInterface) manager;
		assertThat(managerInterface.getExtendedInterfaces(), hasSize(1));
		
		assertThat(manager.getProperties(), hasSize(1));
		assertThat(manager.getPropertyNames(), contains("manages"));
		
		JavaProperty manages = manager.getProperty("manages");
		assertThat(manages.getCardinality(), is(equalTo(JavaPropertyCardinality.LIST)));
		assertThat(manages.getJavaType().getName(), is(equalTo("Person")));
		
	}

}

package com.mutsys.chimera.raml;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import com.mutsys.chimera.raml.type.RamlType;
import com.mutsys.chimera.raml.type.RamlArrayType;
import com.mutsys.chimera.raml.type.user.RamlUserTypeProperty;
import com.mutsys.chimera.raml.type.user.UserDefinedObjectType;

public class RamlTypeFactoryTest {
	
	@Test
	public void shouldCreateRamlTypeModelFromExampleOne() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		RamlType ramlType = ramlTypeModel.getType("Product");
		
		assertThat(ramlType, is(not(nullValue())));
		assertThat(ramlType.isObject(), is(equalTo(true)));
		assertThat(ramlType.isDefinition(), is(equalTo(true)));
		
		UserDefinedObjectType productType = (UserDefinedObjectType) ramlType;
		
		assertThat(productType.getJavaClassType(), is(equalTo("interface")));
		assertThat(productType.getJavaPackageName(), is(equalTo("com.mutsys.exampleOne")));
		assertThat(productType.getJavaClassName(), is(equalTo("Product")));
		
		assertThat(productType.getProperties(), hasSize(2));
		assertThat(productType.getPropertyNames(), containsInAnyOrder("name", "productId"));
		
		RamlUserTypeProperty productId = productType.getProperty("productId");
		assertThat(productId.getType().getTypeName(), is(equalTo("integer")));
		
		RamlUserTypeProperty name = productType.getProperty("name");
		assertThat(name.getType().getTypeName(), is(equalTo("string")));
		
	}
	
	@Test
	public void shouldCreateRamlTypeModelFromExampleTwo() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-two.raml");
		
		RamlType personRamlType = ramlTypeModel.getType("Person");
		
		assertThat(personRamlType, is(not(nullValue())));
		assertThat(personRamlType.isObject(), is(equalTo(true)));
		assertThat(personRamlType.isDefinition(), is(equalTo(true)));
		
		UserDefinedObjectType personType = (UserDefinedObjectType) personRamlType;
		
		assertThat(personType.getJavaClassType(), is(equalTo("interface")));
		assertThat(personType.getJavaPackageName(), is(equalTo("com.mutsys.exampleTwo")));
		assertThat(personType.getJavaClassName(), is(equalTo("Person")));
		
		assertThat(personType.getProperties(), hasSize(1));
		assertThat(personType.getPropertyNames(), contains("name"));
		
		RamlUserTypeProperty personTypeName = personType.getProperty("name");
		assertThat(personTypeName.getType().getTypeName(), is(equalTo("string")));
		
		RamlType managerRamlType = ramlTypeModel.getType("Manager");
		
		assertThat(managerRamlType, is(not(nullValue())));
		assertThat(managerRamlType.isObject(), is(equalTo(true)));
		assertThat(managerRamlType.isDefinition(), is(equalTo(true)));
		
		UserDefinedObjectType managerType = (UserDefinedObjectType) managerRamlType;
		
		assertThat(managerType.getJavaClassType(), is(equalTo("interface")));
		assertThat(managerType.getJavaPackageName(), is(equalTo("com.mutsys.exampleTwo")));
		assertThat(managerType.getJavaClassName(), is(equalTo("Manager")));
		
		assertThat(managerType.getSuperType(), is(equalTo("Person")));
		
		assertThat(managerType.getProperties(), hasSize(2));
		assertThat(managerType.getPropertyNames(), containsInAnyOrder("name", "manages"));
		
		RamlUserTypeProperty managerTypeManages = managerType.getProperty("manages");
		RamlType managesType = managerTypeManages.getType();
		
		assertThat(managesType.isArray(), is(equalTo(true)));
		
		RamlArrayType managesArrayType = (RamlArrayType) managesType;
		
		assertThat(managesArrayType.getReferencedTypeName(), is(equalTo("Person")));
		
	}

}

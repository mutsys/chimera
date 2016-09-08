package com.mutsys.chimera.raml;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import com.mutsys.chimera.raml.type.RamlType;
import com.mutsys.chimera.raml.type.user.RamlUserTypeProperty;
import com.mutsys.chimera.raml.type.user.UserDefinedObjectType;

public class RamlTypeFactoryTest {
	
	@Test
	public void shouldCreateRamlTypeModelFromRamlSource() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		RamlType ramlType = ramlTypeModel.getType("Product");
		
		assertThat(ramlType, is(not(nullValue())));
		assertThat(ramlType.isObject(), is(equalTo(true)));
		assertThat(ramlType.isDefinition(), is(equalTo(true)));
		
		UserDefinedObjectType productType = (UserDefinedObjectType) ramlType;
		
		assertThat(productType.getJavaClassType(), is(equalTo("interface")));
		assertThat(productType.getJavaPackageName(), is(equalTo("com.mutsys.foo")));
		assertThat(productType.getJavaClassName(), is(equalTo("Product")));
		
		assertThat(productType.getProperties(), hasSize(2));
		assertThat(productType.getPropertyNames(), containsInAnyOrder("name", "productId"));
		
		RamlUserTypeProperty productId = productType.getProperty("productId");
		assertThat(productId.getType().getTypeName(), is(equalTo("integer")));
		
		RamlUserTypeProperty name = productType.getProperty("name");
		assertThat(name.getType().getTypeName(), is(equalTo("string")));
		
	}

}

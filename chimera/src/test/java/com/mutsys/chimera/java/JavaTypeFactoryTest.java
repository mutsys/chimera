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
import com.mutsys.chimera.java.type.JavaProperty;
import com.mutsys.chimera.java.type.ProvidedJavaType;
import com.mutsys.chimera.java.type.UserDefinedJavaType;
import com.mutsys.chimera.raml.RamlTypeModel;
import com.mutsys.chimera.raml.RamlTypeModelFactory;

public class JavaTypeFactoryTest {
	
	@Test
	public void shouldCreateJavaTypeModelFromRamlTypeModel() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
				
		JavaPackage comMutsysFoo = javaTypeModel.getPackage("com.mutsys.foo");
		
		assertThat(comMutsysFoo, is(not(nullValue())));
		assertThat(comMutsysFoo.getClasses(), hasSize(1));
		
		assertThat(comMutsysFoo.getClassNames(), contains("Product"));
		
		UserDefinedJavaType product = comMutsysFoo.getClass("Product");
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

}

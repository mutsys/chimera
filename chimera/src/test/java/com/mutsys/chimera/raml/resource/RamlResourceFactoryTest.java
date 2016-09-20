package com.mutsys.chimera.raml.resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import com.mutsys.chimera.raml.resource.method.RamlResourceMethod;

public class RamlResourceFactoryTest {
	
	@Test
	public void shouldCreateRamlResourceModelFromExampleThree() throws Exception {
		
		RamlResourceModel ramlResourceModel = RamlResourceModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-three.raml");
				
		assertThat(ramlResourceModel, is(not(nullValue())));
		
		assertThat(ramlResourceModel.getResources(), hasSize(1));
		
		RamlResource thingsCollection = ramlResourceModel.getResource("/things");
		assertThat(thingsCollection, is(not(nullValue())));
		assertThat(thingsCollection.getMethods(), hasSize(2));
		
		RamlResourceMethod thingsCollectionGetMethod = thingsCollection.getMethod("get");
		assertThat(thingsCollectionGetMethod, is(not(nullValue())));
		assertThat(thingsCollectionGetMethod.hasRequestBody(), is(equalTo(false)));
		assertThat(thingsCollectionGetMethod.getResponse("200"), is(not(nullValue())));
		
		RamlResourceMethod thingsCollectionPostMethod = thingsCollection.getMethod("post");
		assertThat(thingsCollectionPostMethod, is(not(nullValue())));
		assertThat(thingsCollectionPostMethod.hasRequestBody(), is(equalTo(true)));
		assertThat(thingsCollectionPostMethod.getResponse("201"), is(not(nullValue())));
		
		
		RamlResource thingCollectionSubResource = thingsCollection.getSubResource("/{code}");
		assertThat(thingCollectionSubResource, is(not(nullValue())));
		
		RamlResource thingItem = ramlResourceModel.getResource("/things/{code}");
		assertThat(thingItem, is(not(nullValue())));
		assertThat(thingItem.getMethods(), hasSize(3));
		
		RamlResourceMethod thingItemGetMethod = thingItem.getMethod("get");
		assertThat(thingItemGetMethod, is(not(nullValue())));
		assertThat(thingItemGetMethod.hasRequestBody(), is(equalTo(false)));
		assertThat(thingItemGetMethod.getResponse("200"), is(not(nullValue())));
		
		RamlResourceMethod thingItemPutMethod = thingItem.getMethod("put");
		assertThat(thingItemPutMethod, is(not(nullValue())));
		assertThat(thingItemPutMethod.hasRequestBody(), is(equalTo(true)));
		assertThat(thingItemPutMethod.getResponse("200"), is(not(nullValue())));
		
		RamlResourceMethod thingItemDeleteMethod = thingItem.getMethod("delete");
		assertThat(thingItemDeleteMethod, is(not(nullValue())));
		assertThat(thingItemDeleteMethod.hasRequestBody(), is(equalTo(false)));
		assertThat(thingItemDeleteMethod.getResponse("204"), is(not(nullValue())));
		
	}

}

package com.mutsys.chimera.java.resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import com.mutsys.chimera.java.JavaModel;
import com.mutsys.chimera.java.JavaModelFactory;
import com.mutsys.chimera.raml.RamlModel;
import com.mutsys.chimera.raml.RamlModelFactory;

public class JavaResourceFactoryTest {
	
	@Test
	public void shouldCreateJavaResourceModelFromExampleThree() throws Exception {
		
		RamlModel ramlModel = RamlModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-three.raml");
		
		JavaModel javaModel = JavaModelFactory.create(ramlModel);
		
		JavaResourceModel rootResource = javaModel.getResourceModel();
		
		assertThat(rootResource, is(not(nullValue())));
		assertThat(rootResource.isRootResource(), is(equalTo(true)));
		assertThat(rootResource.getName(), is(equalTo("ThingResource")));
		assertThat(rootResource.getPackage().getCanonicalName(), is(equalTo("com.mutsys.exampleThree.resource")));
		assertThat(rootResource.getResourcePath(), is(equalTo("/things")));
		
		assertThat(rootResource.getResourceMethods(), hasSize(2));
		
		JavaResourceMethod findThings = rootResource.getResourceMethod("get");
		assertThat(findThings, is(not(nullValue())));
		assertThat(findThings.getName(), is(equalTo("findThings")));
		assertThat(findThings.getConsumesMediaType().isPresent(), is(equalTo(false)));
		assertThat(findThings.getProducesMediaType().isPresent(), is(equalTo(true)));
		assertThat(findThings.getProducesMediaType().get(), is(equalTo("application/json")));
		assertThat(findThings.getArguments(), hasSize(0));
		
		JavaResourceMethod createThing = rootResource.getResourceMethod("post");
		assertThat(createThing, is(not(nullValue())));
		assertThat(createThing.getName(), is(equalTo("createThing")));
		assertThat(createThing.getConsumesMediaType().isPresent(), is(equalTo(true)));
		assertThat(createThing.getConsumesMediaType().get(), is(equalTo("application/json")));
		assertThat(createThing.getProducesMediaType().isPresent(), is(equalTo(true)));
		assertThat(createThing.getProducesMediaType().get(), is(equalTo("application/json")));
		assertThat(createThing.getArguments(), hasSize(1));
		
		JavaResourceMethodArgument createThingArgument = createThing.getArguments().get(0);
		assertThat(createThingArgument, is(not(nullValue())));
		assertThat(createThingArgument.getArgumentName(), is(equalTo("foo")));
		assertThat(createThingArgument.getArgumentJavaType().getName(), is(equalTo("Foo")));
		
		assertThat(rootResource.getSubResources(), hasSize(1));
		
		JavaResourceModel subResource = rootResource.getSubResource("/{code}");
		
		assertThat(subResource, is(not(nullValue())));
		assertThat(subResource.isRootResource(), is(equalTo(false)));
		assertThat(subResource.getName(), is(equalTo("code")));
		assertThat(subResource.getPackage(), is(nullValue()));
		assertThat(subResource.getResourcePath(), is(equalTo("/{code}")));
		
		assertThat(subResource.getResourceMethods(), hasSize(3));
		
		JavaResourceMethod readThing = subResource.getResourceMethod("get");
		assertThat(readThing, is(not(nullValue())));
		assertThat(readThing.getName(), is(equalTo("readThing")));
		assertThat(readThing.getConsumesMediaType().isPresent(), is(equalTo(false)));
		assertThat(readThing.getProducesMediaType().isPresent(), is(equalTo(true)));
		assertThat(readThing.getProducesMediaType().get(), is(equalTo("application/json")));
		assertThat(readThing.getArguments(), hasSize(1));
		
		JavaResourceMethodArgument readThingArgument = readThing.getArguments().get(0);
		assertThat(readThingArgument, is(not(nullValue())));
		assertThat(readThingArgument.getArgumentName(), is(equalTo("code")));
		assertThat(readThingArgument.getArgumentJavaType().getName(), is(equalTo("String")));
		
		JavaResourceMethod updateThing = subResource.getResourceMethod("put");
		assertThat(updateThing, is(not(nullValue())));
		assertThat(updateThing.getName(), is(equalTo("updateThing")));
		assertThat(updateThing.getConsumesMediaType().isPresent(), is(equalTo(true)));
		assertThat(updateThing.getConsumesMediaType().get(), is(equalTo("application/json")));
		assertThat(updateThing.getProducesMediaType().isPresent(), is(equalTo(true)));
		assertThat(updateThing.getProducesMediaType().get(), is(equalTo("application/json")));
		assertThat(updateThing.getArguments(), hasSize(2));
		
		JavaResourceMethodArgument updateThingCodeArgument = readThing.getArgument("code");
		assertThat(updateThingCodeArgument, is(not(nullValue())));
		assertThat(updateThingCodeArgument.isPathParameter(), is(equalTo(true)));
		assertThat(updateThingCodeArgument.getArgumentName(), is(equalTo("code")));
		assertThat(updateThingCodeArgument.getArgumentJavaType().getName(), is(equalTo("String")));
				
		JavaResourceMethodArgument updateThingFooArgument = updateThing.getArgument("foo");
		assertThat(updateThingFooArgument, is(not(nullValue())));
		assertThat(updateThingFooArgument.isRequestBody(), is(equalTo(true)));
		assertThat(updateThingFooArgument.getArgumentName(), is(equalTo("foo")));
		assertThat(updateThingFooArgument.getArgumentJavaType().getName(), is(equalTo("Foo")));
		
		JavaResourceMethod deleteThing = subResource.getResourceMethod("delete");
		assertThat(deleteThing, is(not(nullValue())));
		assertThat(deleteThing.getName(), is(equalTo("deleteThing")));
		assertThat(deleteThing.getConsumesMediaType().isPresent(), is(equalTo(false)));
		assertThat(deleteThing.getProducesMediaType().isPresent(), is(equalTo(false)));
		assertThat(deleteThing.getArguments(), hasSize(1));
		
		JavaResourceMethodArgument deleteThingArgument = deleteThing.getArguments().get(0);
		assertThat(deleteThingArgument, is(not(nullValue())));
		assertThat(deleteThingArgument.getArgumentName(), is(equalTo("code")));
		assertThat(deleteThingArgument.getArgumentJavaType().getName(), is(equalTo("String")));
		
	}

}

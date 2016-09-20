package com.mutsys.chimera.codegen;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.mutsys.chimera.codegen.compile.GeneratedCodeJavaCompiler;
import com.mutsys.chimera.java.JavaModel;
import com.mutsys.chimera.java.JavaModelFactory;
import com.mutsys.chimera.java.type.JavaTypeModel;
import com.mutsys.chimera.java.type.JavaTypeModelFactory;
import com.mutsys.chimera.raml.RamlModel;
import com.mutsys.chimera.raml.RamlModelFactory;
import com.mutsys.chimera.raml.type.RamlTypeModel;
import com.mutsys.chimera.raml.type.RamlTypeModelFactory;


public class JavaCodeFactoryTest {
	
	@Test
	public void shouldGenerateJavaFromExampleOne() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-one.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
		
		JavaCodeFactory.generateJavaCode(javaTypeModel, "target/generated/java");
		
		GeneratedCodeJavaCompiler compiler = new GeneratedCodeJavaCompiler("target/generated/java");
		ClassLoader generatedCodeClassLoader = compiler.compileGeneratedSource();
		
		Class<?> generatedClass = generatedCodeClassLoader.loadClass("com.mutsys.exampleOne.Product");
		
		assertThat(generatedClass, is(not(nullValue())));
		assertThat(generatedClass.isInterface(), is(equalTo(true)));
		
		Method getName = generatedClass.getMethod("getName");
		
		assertThat(getName, is(not(nullValue())));
		assertThat(getName.getReturnType(), is(equalTo(String.class)));
		
		Method setName = generatedClass.getMethod("setName", String.class);
		
		assertThat(setName, is(not(nullValue())));
		assertThat(setName.getReturnType(), is(equalTo(void.class)));
		
		Method getProductId = generatedClass.getMethod("getProductId");
		
		assertThat(getProductId, is(not(nullValue())));
		assertThat(getProductId.getReturnType(), is(equalTo(Long.class)));
		
		Method setProductId = generatedClass.getMethod("setProductId", Long.class);
		
		assertThat(setProductId, is(not(nullValue())));
		assertThat(setProductId.getReturnType(), is(equalTo(void.class)));
		
	}
	
	@Test
	public void shouldGenerateJavaFromExampleTwo() throws Exception {
		
		RamlTypeModel ramlTypeModel = RamlTypeModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-two.raml");
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel);
		
		JavaCodeFactory.generateJavaCode(javaTypeModel, "target/generated/java");
		
		GeneratedCodeJavaCompiler compiler = new GeneratedCodeJavaCompiler("target/generated/java");
		ClassLoader generatedCodeClassLoader = compiler.compileGeneratedSource();
		
		Class<?> personClass = generatedCodeClassLoader.loadClass("com.mutsys.exampleTwo.Person");
		
		assertThat(personClass, is(not(nullValue())));
		assertThat(personClass.isInterface(), is(equalTo(true)));
		
		Method personGetName = personClass.getMethod("getName");
		
		assertThat(personGetName, is(not(nullValue())));
		assertThat(personGetName.getReturnType(), is(equalTo(String.class)));
		
		Method personSetName = personClass.getMethod("setName", String.class);
		
		assertThat(personSetName, is(not(nullValue())));
		assertThat(personSetName.getReturnType(), is(equalTo(void.class)));
		
		Class<?> managerClass = generatedCodeClassLoader.loadClass("com.mutsys.exampleTwo.Manager");
		
		assertThat(managerClass, is(not(nullValue())));
		assertThat(managerClass.isInterface(), is(equalTo(true)));
		assertThat(personClass.isAssignableFrom(managerClass), is(equalTo(true)));
		
		Method managerGetName = managerClass.getMethod("getName");
		
		assertThat(managerGetName, is(not(nullValue())));
		assertThat(managerGetName.getReturnType(), is(equalTo(String.class)));
		
		Method managerSetName = managerClass.getMethod("setName", String.class);
		
		assertThat(managerSetName, is(not(nullValue())));
		assertThat(managerSetName.getReturnType(), is(equalTo(void.class)));
		
		Method managerGetManages = managerClass.getMethod("getManages");
		
		assertThat(managerGetManages, is(not(nullValue())));
		assertThat(managerGetManages.getReturnType(), is(equalTo(List.class)));		
		assertThat(((ParameterizedType) managerGetManages.getGenericReturnType()).getActualTypeArguments()[0], is(equalTo(personClass)));
		
		Method managerSetManages = managerClass.getMethod("setManages", List.class);
		
		assertThat(managerSetManages, is(not(nullValue())));
		assertThat(managerSetManages.getReturnType(), is(equalTo(void.class)));		
		
	}
	
	@Test
	public void shouldGenerateJavaFromExampleThree() throws Exception {
		
		RamlModel ramlModel = RamlModelFactory.createRamlApi("com/mutsys/chimera/raml/raml-example-three.raml");
		
		JavaModel javaModel = JavaModelFactory.create(ramlModel);
		
		JavaCodeFactory.generateJavaCode(javaModel, "target/generated/java");
		
		GeneratedCodeJavaCompiler compiler = new GeneratedCodeJavaCompiler("target/generated/java");
		ClassLoader generatedCodeClassLoader = compiler.compileGeneratedSource();
		
		Class<?> fooClass = generatedCodeClassLoader.loadClass("com.mutsys.exampleThree.Foo");
		
		assertThat(fooClass, is(not(nullValue())));
		assertThat(fooClass.isInterface(), is(equalTo(true)));
		
		Method getCode = fooClass.getMethod("getCode");
		
		assertThat(getCode, is(not(nullValue())));
		assertThat(getCode.getReturnType(), is(equalTo(String.class)));
		
		Method setCode = fooClass.getMethod("setCode", String.class);
		
		assertThat(setCode, is(not(nullValue())));
		assertThat(setCode.getReturnType(), is(equalTo(void.class)));
		
		Method getName = fooClass.getMethod("getName");
		
		assertThat(getName, is(not(nullValue())));
		assertThat(getName.getReturnType(), is(equalTo(String.class)));
		
		Method setName = fooClass.getMethod("setName", String.class);
		
		assertThat(setName, is(not(nullValue())));
		assertThat(setName.getReturnType(), is(equalTo(void.class)));		
		
		Class<?> resourceClass = generatedCodeClassLoader.loadClass("com.mutsys.exampleThree.resource.ThingResource");
		
		Path resourceClassPath = resourceClass.getAnnotation(Path.class);
		
		assertThat(resourceClassPath, is(not(nullValue())));
		assertThat(resourceClassPath.value(), is(equalTo("/things")));
		
		Method findThings = resourceClass.getDeclaredMethod("findThings");
		
		assertThat(findThings, is(not(nullValue())));
		assertThat(findThings.getReturnType(), is(equalTo(Response.class)));
		
		GET findThingsGET = findThings.getAnnotation(GET.class);
		
		assertThat(findThingsGET, is(not(nullValue())));
		
		Produces findThingsProduces = findThings.getAnnotation(Produces.class);
		
		assertThat(findThingsProduces, is(not(nullValue())));
		assertThat(findThingsProduces.value(), is(equalTo(new String[]{"application/json"})));
		
		Method createThing = resourceClass.getDeclaredMethod("createThing", fooClass);
		
		assertThat(createThing, is(not(nullValue())));
		assertThat(createThing.getReturnType(), is(equalTo(Response.class)));
		
		POST createThingPOST = createThing.getAnnotation(POST.class);
		
		assertThat(createThingPOST, is(not(nullValue())));
		
		Consumes createThingConsumes = createThing.getAnnotation(Consumes.class);
		
		assertThat(createThingConsumes, is(not(nullValue())));
		assertThat(createThingConsumes.value(), is(equalTo(new String[]{"application/json"})));
		
		Produces createThingProduces = createThing.getAnnotation(Produces.class);
		
		assertThat(createThingProduces, is(not(nullValue())));
		assertThat(createThingProduces.value(), is(equalTo(new String[]{"application/json"})));
		
		Method readThing = resourceClass.getDeclaredMethod("readThing", String.class);
		
		assertThat(readThing, is(not(nullValue())));
		assertThat(readThing.getReturnType(), is(equalTo(Response.class)));
		
		GET readThingGET = readThing.getAnnotation(GET.class);
		
		assertThat(readThingGET, is(not(nullValue())));
		
		Path readThingPath = readThing.getAnnotation(Path.class);
		
		assertThat(readThingPath, is(not(nullValue())));
		assertThat(readThingPath.value(), is(equalTo("/{code}")));
		
		Produces readThingProduces = readThing.getAnnotation(Produces.class);
		
		assertThat(readThingProduces, is(not(nullValue())));
		assertThat(readThingProduces.value(), is(equalTo(new String[]{"application/json"})));
		
		Method updateThing = resourceClass.getDeclaredMethod("updateThing", String.class, fooClass);
		
		assertThat(updateThing, is(not(nullValue())));
		assertThat(updateThing.getReturnType(), is(equalTo(Response.class)));
		
		PUT updateThingPUT = updateThing.getAnnotation(PUT.class);
		
		assertThat(updateThingPUT, is(not(nullValue())));
		
		Path updateThingPath = updateThing.getAnnotation(Path.class);
		
		assertThat(updateThingPath, is(not(nullValue())));
		assertThat(updateThingPath.value(), is(equalTo("/{code}")));
		
		Consumes updateThingConsumes = updateThing.getAnnotation(Consumes.class);
		
		assertThat(updateThingConsumes, is(not(nullValue())));
		assertThat(updateThingConsumes.value(), is(equalTo(new String[]{"application/json"})));
		
		Produces updateThingProduces = updateThing.getAnnotation(Produces.class);
		
		assertThat(updateThingProduces, is(not(nullValue())));
		assertThat(updateThingProduces.value(), is(equalTo(new String[]{"application/json"})));
		
		Method deleteThing = resourceClass.getDeclaredMethod("deleteThing", String.class);
		
		assertThat(deleteThing, is(not(nullValue())));
		assertThat(deleteThing.getReturnType(), is(equalTo(Response.class)));
		
		DELETE deleteThingDELETE = deleteThing.getAnnotation(DELETE.class);
		
		assertThat(deleteThingDELETE, is(not(nullValue())));
		
		Path deleteThingPath = readThing.getAnnotation(Path.class);
		
		assertThat(deleteThingPath, is(not(nullValue())));
		assertThat(deleteThingPath.value(), is(equalTo("/{code}")));
		
	}

}

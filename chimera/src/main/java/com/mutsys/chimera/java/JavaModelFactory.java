package com.mutsys.chimera.java;

import com.mutsys.chimera.java.resource.JavaResourceModel;
import com.mutsys.chimera.java.resource.JavaResourceModelFactory;
import com.mutsys.chimera.java.type.JavaTypeModel;
import com.mutsys.chimera.java.type.JavaTypeModelFactory;
import com.mutsys.chimera.raml.RamlModel;
import com.mutsys.chimera.raml.resource.RamlResourceModel;
import com.mutsys.chimera.raml.type.RamlTypeModel;

public class JavaModelFactory {
	
	public static JavaModel create(RamlModel ramlModel) {
		
		RamlTypeModel ramlTypeModel = ramlModel.getRamlTypeModel();
		RamlResourceModel ramlResourceModel = ramlModel.getRamlResourceModel();
		
		JavaTypeModel javaTypeModel = JavaTypeModelFactory.create(ramlTypeModel, ramlResourceModel);
		JavaResourceModel javaResourceModel = JavaResourceModelFactory.create(ramlResourceModel);
		String resourcePackageName  = ramlResourceModel.getResources().stream()
				.filter(r -> r.isRootResource())
				.map(r -> r.getJavaPackageName())
				.findFirst()
				.get();
		javaResourceModel.setPackage(javaTypeModel.getSubPackage(resourcePackageName));
		
		JavaModel javaModel = new JavaModel(javaTypeModel, javaResourceModel);
		
		return javaModel;
		
	}

}

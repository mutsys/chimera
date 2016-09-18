package com.mutsys.chimera.java;

import com.mutsys.chimera.java.resource.JavaResourceModel;
import com.mutsys.chimera.java.type.JavaTypeModel;

public class JavaModel {

	private final JavaTypeModel javaTypeModel;
	private final JavaResourceModel javaResourceModel;
	
	public JavaModel(JavaTypeModel javaTypeModel, JavaResourceModel javaResourceModel) {
		this.javaTypeModel = javaTypeModel;
		this.javaResourceModel = javaResourceModel;
	}

	public JavaTypeModel getTypeModel() {
		return javaTypeModel;
	}
	
	public JavaResourceModel getResourceModel() {
		return javaResourceModel;
	}

}

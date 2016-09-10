package com.mutsys.chimera.java.type;

import java.util.ArrayList;
import java.util.List;

public class JavaInterface extends AbstractUserDefinedJavaType {

	private List<JavaTypeReference> extendedInterfaces = new ArrayList<>();
	
	public JavaInterface() {
		super(JavaClassType.INTERFACE);
	}
	
	public List<JavaTypeReference> getExtendedInterfaces() {
		return extendedInterfaces;
	}
	
	public void addExtendedInterface(JavaTypeReference javaInterface) {
		extendedInterfaces.add(javaInterface);
	}

}

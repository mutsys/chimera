package com.mutsys.chimera.java.type;

import java.util.ArrayList;
import java.util.List;

public class JavaInterface extends AbstractUserDefinedJavaType {

	private List<JavaInterface> extendedInterfaces = new ArrayList<>();
	
	public JavaInterface() {
		super(JavaClassType.INTERFACE);
	}
	
	public List<JavaInterface> getExtendedInterfaces() {
		return extendedInterfaces;
	}
	
	public void addExtendedInterface(JavaInterface javaInterface) {
		extendedInterfaces.add(javaInterface);
	}

}

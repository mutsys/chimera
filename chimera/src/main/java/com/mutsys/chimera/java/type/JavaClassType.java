package com.mutsys.chimera.java.type;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum JavaClassType {
	
	INTERFACE      ("interface"),
	ABSTRACT_CLASS ("abstractClass"),
	CONCRETE_CLASS ("concreteClass");
	
	final static Map<String,JavaClassType> classTypeMap = Arrays.stream(values()).collect(Collectors.toMap(t -> t.getClassTypeName(), Function.identity()));
	
	final private String classTypeName;
	
	private JavaClassType(String classTypeName) {
		this.classTypeName = classTypeName;
	}
	
	public String getClassTypeName() {
		return classTypeName;
	}
	
	public static JavaClassType getType(String classTypeName) {
		return classTypeMap.get(classTypeName);
	}

}

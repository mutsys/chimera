package com.mutsys.chimera.java.type;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractJavaType implements RamlJavaType {

	private final static String JAVA = "java.";
	private final static String JAVAX = "javax.";
	
	private final static Set<String> PRIMITIVES = Stream.of("boolean", "byte", "short", "char", "int", "float", "long", "double").collect(Collectors.toSet());
	
	private final static Set<String> AUTOBOXED = Stream.of(Boolean.class, Byte.class, Short.class, Character.class, Integer.class,
			Float.class, Long.class, Double.class).map(c -> c.getCanonicalName()).collect(Collectors.toSet());

	@Override
	public boolean isJvmType() {
		String canonicalName = getCanonicalName();
		return canonicalName.startsWith(JAVA) || canonicalName.startsWith(JAVAX) || isPrimitiveType();
	}

	@Override
	public boolean isPrimitiveType() {
		return PRIMITIVES.contains(getName());
	}

	@Override
	public boolean isAutoboxedType() {
		return AUTOBOXED.contains(getCanonicalName());
	}

	@Override
	public boolean isProvided() {
		return !isUserDefined();
	}
	
}

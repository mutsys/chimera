package com.mutsys.chimera.codegen.compile;

import java.util.HashMap;
import java.util.Map;

public class GeneratedCodeClassLoader extends ClassLoader {
	
	private final Map<String,GeneratedCodeCompiledClass> compiledClasses = new HashMap<>();
	
	public GeneratedCodeClassLoader(ClassLoader parentClassLoader) {
		super(parentClassLoader);
	}
	
	public void addCompiledClass(GeneratedCodeCompiledClass compiledClass) {
		compiledClasses.put(compiledClass.getClassName(), compiledClass);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		if (compiledClasses.containsKey(name)) {
			GeneratedCodeCompiledClass compiledClass = compiledClasses.get(name);
			byte[] classBytes = compiledClass.getClassBytes();
			return super.defineClass(name, classBytes, 0, classBytes.length);
		}				
		return super.findClass(name);
	}

}

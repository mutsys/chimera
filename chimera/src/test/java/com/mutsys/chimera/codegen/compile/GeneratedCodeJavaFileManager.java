package com.mutsys.chimera.codegen.compile;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class GeneratedCodeJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

	private final GeneratedCodeClassLoader generatedCodeClassLoader = new GeneratedCodeClassLoader(getClass().getClassLoader());
	
	public GeneratedCodeJavaFileManager(JavaFileManager fileManager) {
		super(fileManager);
	}
	
	public ClassLoader getClassLoader() {
		return getClassLoader(null);
	}
	
	@Override
	public ClassLoader getClassLoader(Location location) {
		return generatedCodeClassLoader;
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
		try {
			GeneratedCodeCompiledClass compiledClass = new GeneratedCodeCompiledClass(className);
			generatedCodeClassLoader.addCompiledClass(compiledClass);
			return compiledClass;
		} catch (URISyntaxException e) {
			throw new IOException(e);
		}
	}
	
	
	
	

}

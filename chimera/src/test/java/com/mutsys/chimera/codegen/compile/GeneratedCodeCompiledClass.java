package com.mutsys.chimera.codegen.compile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

public class GeneratedCodeCompiledClass extends SimpleJavaFileObject {
	
	private static final Kind CLASS_KIND = JavaFileObject.Kind.CLASS;
	
	private final String className;
	private final ByteArrayOutputStream classBytes = new ByteArrayOutputStream();
	
	public GeneratedCodeCompiledClass(String className) throws URISyntaxException {
		super(new URI("file:///" + className.replace(".", File.separator) + CLASS_KIND.extension), CLASS_KIND);
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
	
	@Override
	public OutputStream openOutputStream() throws IOException {
		return classBytes;
	}
	
	public byte[] getClassBytes() {
		return classBytes.toByteArray();
	}
	
}

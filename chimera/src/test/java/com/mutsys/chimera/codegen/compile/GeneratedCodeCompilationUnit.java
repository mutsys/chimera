package com.mutsys.chimera.codegen.compile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

import javax.tools.SimpleJavaFileObject;

public class GeneratedCodeCompilationUnit extends SimpleJavaFileObject {

	private final File javaSourceFile;
	
	public GeneratedCodeCompilationUnit(File javaSourceFile) throws URISyntaxException {
		super(new URI("file://" + javaSourceFile.getAbsolutePath()), Kind.SOURCE);
		this.javaSourceFile = javaSourceFile;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		Files.readAllLines(javaSourceFile.toPath()).forEach(l -> {
			stringBuilder.append(l);
			stringBuilder.append(System.lineSeparator());
		});
		return stringBuilder;
	}

	@Override
	public InputStream openInputStream() throws IOException {
		return new FileInputStream(javaSourceFile);
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		return new FileOutputStream(javaSourceFile);
	}
	
	

}

package com.mutsys.chimera.codegen.compile;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratedCodeJavaCompiler {
	
	private static final Logger LOG = LoggerFactory.getLogger(GeneratedCodeJavaCompiler.class);
		
	private final String sourceDirectoryPath;
	
	public GeneratedCodeJavaCompiler(String sourceDirectoryPath) {
		this.sourceDirectoryPath = sourceDirectoryPath;
	}
	
	protected static List<String> getCompilerOptions(String sourceDirectoryPath) {
		String sourceDirectory = Paths.get(sourceDirectoryPath).normalize().toAbsolutePath().toString();
		return Arrays.asList("-verbose", "-parameters", "-source", "1.8", "-target", "1.8","-sourcepath", sourceDirectory);
	}
	
	public ClassLoader compileGeneratedSource() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		List<JavaFileObject> compilationUnits = getCompilationUnits(sourceDirectoryPath);
		StringWriter compilerMessageWriter = new StringWriter();
		List<String> compilerOptions = getCompilerOptions(sourceDirectoryPath);
		GeneratedCodeJavaFileManager fileManager = new GeneratedCodeJavaFileManager(compiler.getStandardFileManager(new CompilationDiagnosticListener(), null, null));
		CompilationTask compilationTask = compiler.getTask(compilerMessageWriter, fileManager, new CompilationDiagnosticListener(), compilerOptions, null, compilationUnits);
		compilationTask.call();
		return fileManager.getClassLoader();
	}
	
	protected List<JavaFileObject> getCompilationUnits(String sourceDirectoryPath) {
		List<JavaFileObject> compilationUnits = new ArrayList<>();
		File sourceDirectory = Paths.get(sourceDirectoryPath).normalize().toAbsolutePath().toFile();
		if (sourceDirectory.exists()) {
			try {
				Files.walkFileTree(sourceDirectory.toPath(), new FileVisitor<Path>() {
	
					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
						return FileVisitResult.CONTINUE;
					}
	
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						if (file.toString().endsWith(".java")) {
							try {
								compilationUnits.add(new GeneratedCodeCompilationUnit(file.toFile()));
							} catch (URISyntaxException e) {
								throw new IOException(e);
							}
						}
						return FileVisitResult.CONTINUE;
					}
	
					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}
	
					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						dir.toFile().delete();
						return FileVisitResult.CONTINUE;
					}
					
				});
			} catch (IOException e) {
				LOG.error("unable to examine generated code directory", e);
			}
		}
		return compilationUnits;
	}
	

}

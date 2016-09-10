package com.mutsys.chimera.codegen.compile;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

public class CompilationDiagnosticListener implements DiagnosticListener<JavaFileObject> {

	private final Writer err = new BufferedWriter(new PrintWriter(System.err));
	private final static String SEPARATOR = " : ";
	private final static Set<Kind> LOGGABLE_DIAGNOSTIC_KIND = Stream.of(Kind.WARNING, Kind.MANDATORY_WARNING, Kind.ERROR).collect(Collectors.toSet());
	
	@Override
	public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
		if (LOGGABLE_DIAGNOSTIC_KIND.contains(diagnostic.getKind())) {
			try {
				err.write(diagnostic.getKind().name());
				err.write(SEPARATOR);
				err.write(diagnostic.getMessage(null));
				err.write(System.lineSeparator());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

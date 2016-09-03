package com.mutsys.chimera.java;

import java.util.Deque;
import java.util.LinkedList;

class PackageHierarchyVisitor {
	
	Deque<JavaPackage> packages = new LinkedList<>();
	
	public void visit(JavaPackage pakkage) {
		packages.addFirst(pakkage);
		if (pakkage.getParent().isPackage()) {
			visit((JavaPackage) pakkage.getParent());
		}
	}
	
	public Deque<JavaPackage> getHierarchy() {
		return packages;
	}

}

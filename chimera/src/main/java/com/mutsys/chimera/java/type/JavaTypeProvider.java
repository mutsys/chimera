package com.mutsys.chimera.java.type;

import java.util.List;

import com.mutsys.chimera.java.pakkage.JavaPackage;

public interface JavaTypeProvider {

	List<JavaPackage> getPackages();

	JavaPackage getPackage(String packageName);

}
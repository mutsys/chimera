package com.mutsys.chimera.java;

import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.type.JavaClassType;
import com.mutsys.chimera.java.type.RamlJavaType;

public interface GeneratedJavaType extends RamlJavaType {

	JavaClassType getJavaClassType();

	JavaPackage getPackage();

	void setPackage(JavaPackage pakkage);

	void setName(String name);

}
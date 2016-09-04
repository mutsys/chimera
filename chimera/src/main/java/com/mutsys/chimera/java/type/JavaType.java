package com.mutsys.chimera.java.type;

import com.mutsys.chimera.java.JavaHierarchyMember;

public interface JavaType extends JavaHierarchyMember {

	boolean isJvmType();
	
	boolean isPrimitiveType();
	
	boolean isAutoboxedType();
	
	boolean isProvided();
	
	boolean isUserDefined();
	
	String getPackageName();
	
}

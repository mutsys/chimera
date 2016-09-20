package com.mutsys.chimera.java.resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.mutsys.chimera.java.GeneratedJavaType;
import com.mutsys.chimera.java.pakkage.JavaPackage;
import com.mutsys.chimera.java.type.JavaClassType;

public class JavaResourceModel implements GeneratedJavaType {
	
	private final static Map<String,Integer> createMethodOrderingMap() {
		Map<String,Integer> moMap = new HashMap<>();
		moMap.put("GET", 1);
		moMap.put("POST", 2);
		moMap.put("PUT", 3);
		moMap.put("DELETE", 4);
		return moMap;
	}
	
	private final static Comparator<JavaResourceMethod> methodComparator = new Comparator<JavaResourceMethod>() {

		private Map<String,Integer> methodOrderingMap = createMethodOrderingMap();
		
		@Override
		public int compare(JavaResourceMethod m1, JavaResourceMethod m2) {
			return methodOrderingMap.get(m1.getHttpMethod()).compareTo(methodOrderingMap.get(m2.getHttpMethod()));
		}
		
	};
	
	private JavaResourceModel parentResource = null;
	private Map<String,JavaResourceModel> subResources = new HashMap<>();
	private String resourcePath;
	private String name;
	private JavaPackage pakkage;
	
	private Map<String,JavaResourceMethod> resourceMethods = new HashMap<>();
	
	public List<JavaResourceMethod> getResourceMethods() {
		List<JavaResourceMethod> methods = new ArrayList<>(resourceMethods.values());
		methods.sort(methodComparator);
		return methods;
	}
	
	public JavaResourceMethod getResourceMethod(String httpMethod) {
		return resourceMethods.get(httpMethod.toUpperCase());
	}
	
	public void addResourceMethod(JavaResourceMethod resourceMethod) {
		resourceMethods.put(resourceMethod.getHttpMethod().toUpperCase(), resourceMethod);
		resourceMethod.setResource(this);
	}

	@Override
	public boolean isJvmType() {
		return false;
	}

	@Override
	public boolean isPrimitiveType() {
		return false;
	}

	@Override
	public boolean isAutoboxedType() {
		return false;
	}

	@Override
	public boolean isProvided() {
		return false;
	}

	@Override
	public boolean isUserDefined() {
		return false;
	}

	@Override
	public String getPackageName() {
		return pakkage.getCanonicalName();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getCanonicalName() {
		return new StringBuilder(pakkage.getCanonicalName())
			.append(".")
			.append(name)
			.toString();
	}

	@Override
	public JavaClassType getJavaClassType() {
		return JavaClassType.INTERFACE;
	}

	@Override
	public JavaPackage getPackage() {
		return pakkage;
	}

	@Override
	public void setPackage(JavaPackage pakkage) {
		this.pakkage = pakkage;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	
	public boolean isRootResource() {
		return Objects.isNull(parentResource);
	}

	public JavaResourceModel getParentResource() {
		return parentResource;
	}

	public void setParentResource(JavaResourceModel parentResource) {
		this.parentResource = parentResource;
	}

	public List<JavaResourceModel> getSubResources() {
		return new ArrayList<>(subResources.values());
	}
	
	public JavaResourceModel getSubResource(String subResourcePath) {
		return subResources.get(subResourcePath);
	}

	public void addSubResource(JavaResourceModel subResource) {
		subResources.put(subResource.getResourcePath(), subResource);
	}

}

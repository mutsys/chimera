package com.mutsys.chimera.raml.resource.method;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum RamlResourceMethodType {
	
	GET    ("get",    0, "read",   "find",   false),
	POST   ("post",   1, "create", "create", true),
	PUT    ("put",    2, "update", "update", true),
	DELETE ("delete", 3, "delete", "delete", false);
	
	private static final Map<String,RamlResourceMethodType> methodMap = Arrays.stream(values()).collect(Collectors.toMap(m -> m.getMethodName(), Function.identity()));
	
	private static final Comparator<RamlResourceMethodType> methodTypeComparator = new Comparator<RamlResourceMethodType>() {

		@Override
		public int compare(RamlResourceMethodType mt1, RamlResourceMethodType mt2) {
			return Integer.compare(mt1.getOrdinal(), mt2.getOrdinal());
		}
		
	};
	
	private static final List<RamlResourceMethodType> orderedMethodTypes = Arrays.stream(values()).sorted(methodTypeComparator).collect(Collectors.toList());
	
	private String methodName;
	private int ordinal;
	private String memberPrefix;
	private String collectionPrefix;
	private boolean requestHasBody;
	
	
	private RamlResourceMethodType(String methodName, int ordinal, String memberPrefix, String collectionPrefix, boolean requestHasBody) {
		this.methodName = methodName;
		this.ordinal = ordinal;
		this.memberPrefix = memberPrefix;
		this.collectionPrefix = collectionPrefix;
		this.requestHasBody = requestHasBody;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public int getOrdinal() {
		return ordinal;
	}
	
	public String getMemberPrefix() {
		return memberPrefix;
	}
	
	public String getCollectionPrefix() {
		return collectionPrefix;
	}
	
	public boolean requestHasBody() {
		return requestHasBody;
	}
	
	public static RamlResourceMethodType getMethodType(String methodName) {
		return methodMap.get(methodName);
	}
	
	public List<RamlResourceMethodType> getMethodTypes() {
		return orderedMethodTypes;
	}

}

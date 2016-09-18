package com.mutsys.chimera.java.resource;

import com.mutsys.chimera.java.type.RamlJavaType;

public abstract class AbstractJavaResourceMethodArgument implements JavaResourceMethodArgument {
	
	private final JavaResourceMethodArgumentType argumentType;
	private String argumentName;
	private RamlJavaType argumentJavaType;
	
	protected AbstractJavaResourceMethodArgument(JavaResourceMethodArgumentType argumentType) {
		this.argumentType = argumentType;
	}

	@Override
	public JavaResourceMethodArgumentType getArgumentType() {
		return argumentType;
	}

	@Override
	public boolean isRequestBody() {
		return JavaResourceMethodArgumentType.REQUEST_BODY.equals(argumentType);
	}

	@Override
	public boolean isPathParameter() {
		return JavaResourceMethodArgumentType.PATH_PARAMETER.equals(argumentType);
	}

	@Override
	public String getArgumentName() {
		return argumentName;
	}
	
	public void setArgumentName(String argumentName) {
		this.argumentName = argumentName;
	}

	@Override
	public RamlJavaType getArgumentJavaType() {
		return argumentJavaType;
	}

	public void setArgumentJavaType(RamlJavaType argumentJavaType) {
		this.argumentJavaType = argumentJavaType;
	}
	
}

package com.mutsys.chimera.raml.type.user;

import com.mutsys.chimera.raml.type.RamlTypeFamily;

public class UserDefinedArrayType extends AbstractUserDefinedRamlType {

	private String memberType;
	
	public UserDefinedArrayType() {
		super(RamlTypeFamily.ARRAY);
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
}

package com.mutsys.chimera.raml.type.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mutsys.chimera.raml.type.RamlTypeFamily;

public class UserDefinedObjectType extends AbstractUserDefinedRamlType {

	private Map<String,RamlUserTypeProperty> properties = new HashMap<>();
	
	public UserDefinedObjectType() {
		super(RamlTypeFamily.OBJECT);
	}
	
	public boolean hasProperty(String propertyName) {
		return properties.containsKey(propertyName);
	}
	
	public List<String> getPropertyNames() {
		return new ArrayList<>(properties.keySet());
	}
	
	public List<RamlUserTypeProperty> getProperties() {
		return new ArrayList<>(properties.values());
	}
	
	public RamlUserTypeProperty getProperty(String propertyName) {
		return properties.get(propertyName);
	}
	
	public void addProperty(RamlUserTypeProperty property) {
		properties.put(property.getName(), property);
	}

}

package com.mutsys.chimera.raml.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDefinedObjectType extends AbstractUserDefinedRamlType {

	private Map<String,Property> properties = new HashMap<>();
	
	public UserDefinedObjectType() {
		super(RamlTypeFamily.OBJECT);
	}
	
	public boolean hasProperty(String propertyName) {
		return properties.containsKey(propertyName);
	}
	
	public List<String> getPropertyNames() {
		return new ArrayList<>(properties.keySet());
	}
	
	public List<Property> getProperties() {
		return new ArrayList<>(properties.values());
	}
	
	public Property getProperty(String propertyName) {
		return properties.get(propertyName);
	}
	
	public void addProperty(Property property) {
		properties.put(property.getName(), property);
	}

}

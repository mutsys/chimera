package com.mutsys.chimera.raml;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mutsys.chimera.raml.type.BuiltInRamlType;
import com.mutsys.chimera.raml.type.RamlType;
import com.mutsys.chimera.raml.type.user.UserDefinedRamlType;

public class RamlTypeModel {
	
	protected final static Comparator<RamlType> dependencyOrdering = (a,b) -> {
		if (Objects.isNull(a.getSuperType())) {
			return 1;
		}
		if (Objects.isNull(b.getSuperType())) {
			return -1;
		}
		if (b.getSuperType().equals(a.getTypeName())) {
			return -1;
		}
		if (a.getSuperType().equals(b.getTypeName())) {
			return 1;
		}
		return 0;
	};
	
	protected static Predicate<RamlType> noBuiltInTypes = r -> !r.isBuiltInType();
	
	protected static Predicate<RamlType> allTypes = r -> true;
	
	private Map<String,RamlType> ramlTypeMap = new HashMap<>();
	
	public RamlTypeModel() {
		for (BuiltInRamlType builtInType : BuiltInRamlType.values()) {
			ramlTypeMap.put(builtInType.getTypeName(), builtInType);
		}
	}
	
	protected Stream<RamlType> getRamlTypes(boolean withBuiltInTypes) {
		Predicate<RamlType> predicate = withBuiltInTypes ? allTypes : noBuiltInTypes;
		return ramlTypeMap.values().stream()
				.filter(predicate)
				.sorted(dependencyOrdering);
	}
	
	public void addRamlType(UserDefinedRamlType ramlType) {
		ramlTypeMap.put(ramlType.getTypeName(), ramlType);
	}
	
	public List<String> getTypeNames(boolean withBuiltInTypes) {
		return getRamlTypes(withBuiltInTypes).map(t -> t.getTypeName()).collect(Collectors.toList());
	}
	
	public List<RamlType> getTypes(boolean withBuiltInTypes) {
		return getRamlTypes(withBuiltInTypes).collect(Collectors.toList());
	}
	
	public RamlType getType(String typeName) {
		return ramlTypeMap.get(typeName);
	}

}

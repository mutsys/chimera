package com.mutsys.chimera.java.type;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mutsys.chimera.raml.type.RamlTypeFamily;

public enum JavaPropertyCardinality {

	SCALAR (RamlTypeFamily.SCALAR),
	OBJECT (RamlTypeFamily.OBJECT),
	LIST   (RamlTypeFamily.ARRAY);
	
	private static final Map<RamlTypeFamily,JavaPropertyCardinality> carindalityMap = Arrays.stream(values()).collect(Collectors.toMap(c -> c.getRamlTypeFamily(), Function.identity()));
	
	private final RamlTypeFamily typeFamily;
	
	private JavaPropertyCardinality(RamlTypeFamily typeFamily) {
		this.typeFamily = typeFamily;
	}
	
	public RamlTypeFamily getRamlTypeFamily() {
		return typeFamily;
	}
	
	public static JavaPropertyCardinality getCardinality(RamlTypeFamily typeFamily) {
		return carindalityMap.get(typeFamily);
	}
	
}

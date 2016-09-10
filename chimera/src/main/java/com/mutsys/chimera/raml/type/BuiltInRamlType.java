package com.mutsys.chimera.raml.type;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum BuiltInRamlType implements RamlTypeDefinition {

	ANY           ("any",           RamlTypeFamily.ANY,    null,   Type.class,           false, false, false, false, false),
	BOOLEAN       ("boolean",       RamlTypeFamily.SCALAR, ANY,    Boolean.class,        true,  false, false, false, false),
	NUMBER        ("number",        RamlTypeFamily.SCALAR, ANY,    Double.class,         false, true,  false, false, false),
	INTEGER       ("integer",       RamlTypeFamily.SCALAR, NUMBER, Long.class,           false, true,  false, false, false),
	STRING        ("string",        RamlTypeFamily.SCALAR, ANY,    String.class,         false, false, true,  false, false),
	DATE_ONLY     ("date-only",     RamlTypeFamily.SCALAR, ANY,    LocalDate.class,      false, false, false, true,  false),
	TIME_ONLY     ("time-only",     RamlTypeFamily.SCALAR, ANY,    LocalTime.class,      false, false, false, true,  false),
	DATETIME_ONLY ("datetime-only", RamlTypeFamily.SCALAR, ANY,    LocalDateTime.class,  false, false, false, true,  false),
	DATETIME      ("datetime",      RamlTypeFamily.SCALAR, ANY,    OffsetDateTime.class, false, false, false, true,  false),
	OBJECT        ("object",        RamlTypeFamily.OBJECT, ANY,    Object.class,         false, false, false, false, false),
	NIL           ("nil",           RamlTypeFamily.SCALAR, ANY,    Void.class,           false, false, false, false, true),
	ARRAY         ("array",         RamlTypeFamily.ARRAY,  ANY,    List.class,           false, false, false, false, false);
	
	final private String ramlTypeName;
	final private RamlTypeFamily typeFamily;
	final private BuiltInRamlType superType;
	final private Class<?> javaClass;
	final private boolean bool;
	final private boolean numeric;
	final private boolean string;
	final private boolean date;
	final private boolean nil;
	
	final private static Map<String,BuiltInRamlType> typeMap = Arrays.stream(values()).collect(Collectors.toMap(pt -> pt.getTypeName(), Function.identity()));
	
	private BuiltInRamlType(String ramlTypeName, RamlTypeFamily typeFamily, BuiltInRamlType superType, Class<?> javaClass, boolean bool, boolean numeric, boolean string, boolean date, boolean nil) {
		this.ramlTypeName = ramlTypeName;
		this.typeFamily = typeFamily;
		this.superType = superType;
		this.javaClass = javaClass;
		this.bool = bool;
		this.numeric = numeric;
		this.string = string;
		this.date = date;
		this.nil = nil;
	}
	
	@Override
	public String getTypeName() {
		return ramlTypeName;
	}
	
	@Override
	public RamlTypeFamily getTypeFamily() {
		return typeFamily;
	}

	public Class<?> getJavaClass() {
		return javaClass;
	}
	
	@Override
	public boolean isScalar() {
		return typeFamily.equals(RamlTypeFamily.SCALAR);
	}
	
	@Override
	public boolean isObject() {
		return typeFamily.equals(RamlTypeFamily.OBJECT);
	}
	
	public boolean isBoolean() {
		return bool;
	}
	
	public boolean isNumeric() {
		return numeric;
	}
	
	public boolean isString() {
		return string;
	}
	
	public boolean isDate() {
		return date;
	}
	
	public boolean isNil() {
		return nil;
	}
	
	public boolean isAny() {
		return typeFamily.equals(RamlTypeFamily.ANY);
	}
	
	public boolean isArray() {
		return typeFamily.equals(RamlTypeFamily.ARRAY);
	}
	
	public static BuiltInRamlType getType(String typeName) {
		return typeMap.get(typeName);
	}

	@Override
	public boolean isBuiltInType() {
		return true;
	}

	@Override
	public String getSuperType() {
		return Objects.nonNull(superType) ? superType.getTypeName() : "";
	}

	@Override
	public boolean isReference() {
		return false;
	}

	@Override
	public boolean isDefinition() {
		return false;
	}
	
}

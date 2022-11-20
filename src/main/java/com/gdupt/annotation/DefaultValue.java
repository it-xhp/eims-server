package com.gdupt.annotation;

import java.lang.annotation.*;

/** 实体属性默认值
 * @author kiho
 * @date 2021/05/31
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DefaultValue {

    byte byteValue() default 0;

    short shortValue() default 0;

    int intValue() default 0;

    long longValue() default 0L;

    float floatValue() default 0.0f;

    double doubleValue() default 0.0d;

    char charValue() default '\u0000';

    boolean booleanValue() default false;

    String stringValue() default "";

}

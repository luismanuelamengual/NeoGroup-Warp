package org.neogroup.warp.controllers.routing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforePut {
    String[] value() default "*";
    int priority() default RoutingPriority.NORMAL;
}

package rzi.j17sandbox.modules.annotationModule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Initial Annotation, just use it and test presence
 * @author rene
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE})
public @interface MyInitialAnnotation {
  
}

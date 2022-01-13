package rzi.j17sandbox.modules.annotationModule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author rene
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE})

public @interface AppInfo {
  String value() default "AppInfo";
  String authorName() ;
  String status() default "Development";
  String lastEditDate();
  String version();
}

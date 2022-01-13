package rzi.j17sandbox.modules.annotationModule;

import java.lang.annotation.Annotation;
import rzi.j17sandbox.modules.BasPanel;

/**
 * To test and learn about Annotations.
 *
 * Incl. self defined Annotations
 *
 * @author rene
 */
@MyInitialAnnotation
public class AnnotationModule extends BasPanel {

  public AnnotationModule() {
    jInit();
  }

  private void jInit() {
    // Look for Annotations in this class
    write("Starting Annotation Test");
    Annotation[] annotations = getClass().getAnnotations();
    write("Number of annotations found in this class: " + annotations.length);
    for (Annotation annotation : annotations)
      write(annotation.toString());
    // Annotations in main-class
    write("Infos from Main-Class");
      String className = "rzi.j17sandbox.J17SandBox";
    try {
      Class mainClass = Class.forName(className);
      AppInfo appInfo = (AppInfo) mainClass.getAnnotation(AppInfo.class);
      write ("Value", appInfo.value());
      write ("Name of Author", appInfo.authorName());
      write ("Last edited at", appInfo.lastEditDate());
      write ("Version", appInfo.version());
      write ("Status", appInfo.status());
      var appInfoAnno = appInfo.annotationType();
      write (appInfoAnno.toString());
    } catch (ClassNotFoundException ex) {
      write("Cannot find class " + className);
    }

  }

}

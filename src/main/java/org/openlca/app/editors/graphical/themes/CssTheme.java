package org.openlca.app.editors.graphical.themes;

import java.io.File;

import com.helger.css.reader.CSSReader;
import com.helger.css.reader.CSSReaderSettings;

import org.eclipse.swt.graphics.Color;

public class CssTheme {

  private final String name;

  private CssTheme(String name) {
    this.name = name;
  }

  public static CssTheme read(File file) {
    if (file == null)
      return null;

    var name = file.getName();
    if (name.endsWith(".css")) {
      name.substring(0, name.length() - 4);
    }

    var settings = new CSSReaderSettings();
    var css = CSSReader.readFromFile(file, settings);
    if (css == null)
      return null;

    var theme =  new CssTheme(file.getName());


    return theme;
  }

  enum BoxType {
    UNIT_PROCESS,
    SYSTEM_PROCESS,
    SUB_SYSTEM,
    LIBRARY_PROCESS,
  }

  enum FlowType {
    PRODUCT,
    WASTE,
    ELEMENTARY
  }

  private static class BoxConfig {
    int borderWidth;
    Color borderColor;
    Color fontColor;
    Color backgroundColor;
  }

}

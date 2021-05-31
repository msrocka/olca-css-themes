package org.openlca.app.editors.graphical.themes;

import java.io.File;

import com.helger.css.reader.CSSReader;
import com.helger.css.reader.CSSReaderSettings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.openlca.app.util.Colors;

public class CssTheme {

  private final String name;

  private Color defaultFontColor;
  private Color defaultBackgroundColor;
  private Color defaultBorderColor;
  private Color defaultLinkColor;
  private Color infoFontColor;
  private int defaultBorderWidth;

  private CssTheme(String name) {
    this.name = name;
  }

  public Color defaultFontColor() {
    return defaultFontColor == null
        ? Colors.black()
        : defaultFontColor;
  }

  public Color defaultBackgroundColor() {
    return defaultBackgroundColor == null
        ? Colors.white()
        : defaultBackgroundColor;
  }

  public Color defaultBorderColor() {
    return defaultBorderColor == null
        ? Colors.black()
        : defaultBorderColor;
  }

  public Color defaultLinkColor() {
    return defaultLinkColor == null
        ? Colors.black()
        : defaultLinkColor;
  }

  public Color infoFontColor() {
    return infoFontColor == null
        ? Colors.black()
        : infoFontColor;
  }

  public Color defaultBorderWidth() {
    return defaultBorderWidth < 1
      ? 1
      : defaultBorderWidth;
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

    var theme = new CssTheme(file.getName());

    return theme;
  }

  private static Color color(String s) {
    if (s == null)
      return Colors.black();
    if (s.startsWith("#"))
      return Colors.fromHex(s);
    return switch (s.trim().toLowerCase()) {
    case "white" -> Colors.systemColor(SWT.COLOR_WHITE);
    case "black" -> Colors.systemColor(SWT.COLOR_BLACK);
    case "darkred" -> Colors.systemColor(SWT.COLOR_DARK_RED);
    case "darkgreen" -> Colors.systemColor(SWT.COLOR_DARK_GREEN);
    case "darkblue" -> Colors.systemColor(SWT.COLOR_DARK_BLUE);
    case "darkmagenta" -> Colors.systemColor(SWT.COLOR_DARK_MAGENTA);
    case "darkyellow" -> Colors.systemColor(SWT.COLOR_DARK_YELLOW);
    case "darkcyan" -> Colors.systemColor(SWT.COLOR_DARK_CYAN);
    case "gray" -> Colors.systemColor(SWT.COLOR_GRAY);
    case "grey" -> Colors.systemColor(SWT.COLOR_GRAY);
    case "darkgray" -> Colors.systemColor(SWT.COLOR_DARK_GRAY);
    case "darkgrey" -> Colors.systemColor(SWT.COLOR_DARK_GRAY);
    case "red" -> Colors.systemColor(SWT.COLOR_RED);
    case "green" -> Colors.systemColor(SWT.COLOR_GREEN);
    case "yellow" -> Colors.systemColor(SWT.COLOR_YELLOW);
    case "blue" -> Colors.systemColor(SWT.COLOR_BLUE);
    case "magenta" -> Colors.systemColor(SWT.COLOR_MAGENTA);
    case "cyan" -> Colors.systemColor(SWT.COLOR_CYAN);
    default -> Colors.black();
    };
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
    Color fontColor;
    Color backgroundColor;
    Color borderColor;
    int borderWidth;

    BoxConfig() {
      borderWidth = 1;
      borderColor = Colors.black();
      fontColor = Colors.black();
      backgroundColor = Colors.white();
    }
  }

}

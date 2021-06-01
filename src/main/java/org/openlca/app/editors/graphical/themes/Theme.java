package org.openlca.app.editors.graphical.themes;

import java.io.File;
import java.util.EnumMap;
import java.util.Optional;

import com.helger.css.reader.CSSReader;
import com.helger.css.reader.CSSReaderSettings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.openlca.app.util.Colors;
import org.openlca.core.model.FlowType;

public class Theme {

  private final String name;

  private boolean isDark;
  private Color defaultFontColor;
  private Color defaultBackgroundColor;
  private Color defaultBorderColor;
  private Color defaultLinkColor;
  private Color infoFontColor;
  private int defaultBorderWidth;

  private final EnumMap<FlowType, Color> flowLabelColors;
  private final EnumMap<FlowType, Color> linkColors;
  private final EnumMap<BoxType, BoxConfig> boxConfigs;

  private Theme(String name) {
    this.name = name;
    this.flowLabelColors = new EnumMap<>(FlowType.class);
    this.linkColors = new EnumMap<>(FlowType.class);
    this.boxConfigs = new EnumMap<>(BoxType.class);
  }

  public String name() {
    return name;
  }

  public boolean isDark() {
    return isDark;
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

  public int defaultBorderWidth() {
    return Math.max(defaultBorderWidth, 1);
  }

  public Color fontColorOf(BoxType type) {
    var config = boxConfigs.get(type);
    return config != null && config.fontColor != null
      ? config.fontColor
      : defaultFontColor();
  }

  public Color backgroundColorOf(BoxType type) {
    var config = boxConfigs.get(type);
    return config != null && config.backgroundColor != null
      ? config.backgroundColor
      : defaultBackgroundColor();
  }

  public Color borderColorOf(BoxType type) {
    var config = boxConfigs.get(type);
    return config != null && config.borderColor != null
      ? config.borderColor
      : defaultBorderColor();
  }

  public int borderWidthOf(BoxType type) {
    var config = boxConfigs.get(type);
    return config != null && config.borderWidth > 0
      ? config.borderWidth
      : defaultBorderWidth();
  }

  public Color fontColorOf(FlowType flowType) {
    var color = flowLabelColors.get(flowType);
    return color != null
      ? color
      : defaultFontColor();
  }

  public Color linkColorOf(FlowType flowType) {
    var color = linkColors.get(flowType);
    return color != null
      ? color
      : defaultLinkColor();
  }

  public static Theme defaults(String name) {
    return new Theme(name);
  }

  public static Optional<Theme> read(File file) {
    if (file == null)
      return Optional.empty();

    var name = file.getName();
    if (name.endsWith(".css")) {
      name.substring(0, name.length() - 4);
    }

    var settings = new CSSReaderSettings();
    var css = CSSReader.readFromFile(file, settings);
    if (css == null)
      return Optional.empty();

    var theme = new Theme(file.getName());

    for (var rule : css.getAllStyleRules()) {
      rule.getAllSelectors();
      for (var declaration : rule.getAllDeclarations()) {
        System.out.println(declaration.getProperty());
      }
    }

    return Optional.of(theme);
  }

  enum BoxType {
    UNIT_PROCESS,
    SYSTEM_PROCESS,
    SUB_SYSTEM,
    LIBRARY_PROCESS,
  }

  private static class BoxConfig {
    Color fontColor;
    Color backgroundColor;
    Color borderColor;
    int borderWidth;
  }

}

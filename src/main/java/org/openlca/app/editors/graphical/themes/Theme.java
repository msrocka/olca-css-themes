package org.openlca.app.editors.graphical.themes;

import java.io.File;
import java.util.EnumMap;
import java.util.Optional;

import com.helger.css.decl.CSSStyleRule;
import com.helger.css.reader.CSSReader;
import com.helger.css.reader.CSSReaderSettings;

import org.eclipse.swt.graphics.Color;
import org.openlca.app.util.Colors;
import org.openlca.core.model.FlowType;

public class Theme {

  private final String name;

  private boolean isDark;
  private Color graphBackgroundColor;
  private Color defaultLinkColor;
  private Color infoFontColor;

  private final EnumMap<FlowType, Color> flowLabelColors;
  private final EnumMap<FlowType, Color> linkColors;
  private final EnumMap<Box, BoxConfig> boxConfigs;

  private Theme(String name) {
    this.name = name;
    this.flowLabelColors = new EnumMap<>(FlowType.class);
    this.linkColors = new EnumMap<>(FlowType.class);
    this.boxConfigs = new EnumMap<>(Box.class);
  }

  public String name() {
    return name;
  }

  public boolean isDark() {
    return isDark;
  }

  public Color graphBackgroundColor() {
    return graphBackgroundColor == null
      ? Colors.white()
      : graphBackgroundColor;
  }

  public Color boxFontColor(Box box) {
    var config = boxConfigs.get(box);
    return config != null && config.fontColor != null
      ? config.fontColor
      : Colors.black();
  }

  public Color boxBackgroundColor(Box box) {
    var config = boxConfigs.get(box);
    return config != null && config.backgroundColor != null
      ? config.backgroundColor
      : graphBackgroundColor();
  }

  public Color boxBorderColor(Box box) {
    var config = boxConfigs.get(box);
    return config != null && config.borderColor != null
      ? config.borderColor
      : Colors.black();
  }

  public int boxBorderWidth(Box box) {
    var config = boxConfigs.get(box);
    return config != null
      ? Math.max(1, config.borderWidth)
      : 1;
  }

  public Color linkColor() {
    return defaultLinkColor == null
      ? Colors.black()
      : defaultLinkColor;
  }

  public Color linkColor(FlowType flowType) {
    var color = linkColors.get(flowType);
    return color != null
      ? color
      : linkColor();
  }

  public Color infoFontColor() {
    return infoFontColor == null
      ? Colors.black()
      : infoFontColor;
  }

  public Color fontColorOf(FlowType flowType) {
    var color = flowLabelColors.get(flowType);
    return color != null
      ? color
      : boxFontColor(Box.DEFAULT);
  }

  public static Theme defaults(String name) {
    return new Theme(name);
  }

  public static Optional<Theme> read(File file) {
    if (file == null)
      return Optional.empty();

    var settings = new CSSReaderSettings();
    var css = CSSReader.readFromFile(file, settings);
    if (css == null)
      return Optional.empty();

    // select the theme name
    var name = Css.themeNameOf(css).orElse(null);
    if (name == null || name.isBlank()) {
      name = file.getName();
      if (name.endsWith(".css")) {
        name = name.substring(0, name.length() - 4);
      }
    }
    var theme = defaults(name);

    theme.isDark = Css.hasDarkMode(css);

    for (int i = 0; i < css.getStyleRuleCount(); i++) {
      var rule = css.getStyleRuleAtIndex(i);

      // box config
      Css.boxOf(rule)
        .ifPresent(box -> theme.styleBox(box, rule));

      // root config
      if (Css.isRoot(rule)) {
        Css.getBackgroundColor(rule)
          .ifPresent(color -> theme.graphBackgroundColor = color);
      }

      // links
      if (Css.isLink(rule)) {
        var flowType = Css.flowTypeOf(rule);
        Css.getColor(rule).ifPresent(color -> {
          if (flowType.isPresent()) {
            theme.linkColors.put(flowType.get(), color);
          } else {
            theme.defaultLinkColor = color;
          }
        });
      }

    }


    return Optional.of(theme);
  }

  private void styleBox(Box box, CSSStyleRule rule) {
    var config = boxConfigs.computeIfAbsent(box, $ -> new BoxConfig());
    Css.getColor(rule)
      .ifPresent(color -> config.fontColor = color);
    Css.getBackgroundColor(rule)
      .ifPresent(color -> config.backgroundColor = color);
    Css.getBorderColor(rule)
      .ifPresent(color -> config.borderColor = color);
    Css.getBorderWidth(rule)
      .ifPresent(width -> config.borderWidth = width);
  }

  enum Box {
    DEFAULT,
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

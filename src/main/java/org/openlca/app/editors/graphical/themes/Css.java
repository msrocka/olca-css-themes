package org.openlca.app.editors.graphical.themes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;

import com.helger.css.decl.CSSStyleRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.openlca.app.util.Colors;
import org.openlca.core.model.FlowType;

class Css {

  private Css() {
  }

  static boolean isRoot(CSSStyleRule rule) {
    return isPresent(":root", rule);
  }

  static boolean isInfo(CSSStyleRule rule) {
    return isPresent(".info", rule);
  }

  static boolean isBox(CSSStyleRule rule) {
    return isPresent(".box", rule);
  }

  static boolean isLabel(CSSStyleRule rule) {
    return isPresent(".label", rule);
  }

  static Optional<FlowType> flowTypeOf(CSSStyleRule rule) {
    if (rule == null)
      return Optional.empty();
    if (isPresent(".product", rule))
      return Optional.of(FlowType.PRODUCT_FLOW);
    if (isPresent(".waste", rule))
      return Optional.of(FlowType.WASTE_FLOW);
    if (isPresent(".elementary-flow", rule))
      return Optional.of(FlowType.ELEMENTARY_FLOW);
    return Optional.empty();
  }

  static Optional<Theme.BoxType> boxTypeOf(CSSStyleRule rule) {
    if (!isBox(rule))
      return Optional.empty();
    if (isPresent(".unit-process", rule))
      return Optional.of(Theme.BoxType.UNIT_PROCESS);
    if (isPresent(".system-process", rule))
      return Optional.of(Theme.BoxType.SYSTEM_PROCESS);
    if (isPresent(".library-process", rule))
      return Optional.of(Theme.BoxType.LIBRARY_PROCESS);
    if (isPresent(".sub-system", rule))
      return Optional.of(Theme.BoxType.SUB_SYSTEM);
    return Optional.empty();
  }

  static boolean isPresent(String selector, CSSStyleRule rule) {
    if (rule == null)
      return false;
    for (int i = 0; i < rule.getSelectorCount(); i++) {
      var s = rule.getSelectorAtIndex(i);
      if (s == null)
        continue;
      for (int j = 0; j < s.getMemberCount(); j++) {
        var member = s.getMemberAtIndex(j);
        if (member == null)
          continue;
        if (Objects.equals(selector, member.getAsCSSString()))
          return true;
      }
    }
    return false;
  }

  static Optional<Color> getColor(CSSStyleRule rule) {
    return colorOf("color", rule);
  }

  static Optional<Color> getBackgroundColor(CSSStyleRule rule) {
    var color = colorOf("background", rule);
    return color.isPresent()
      ? color
      : colorOf("background-color", rule);
  }

  static Optional<Color> getBorderColor(CSSStyleRule rule) {
    var color = colorOf("border", rule);
    return color.isPresent()
      ? color
      : colorOf("border-color", rule);
  }

  private static Optional<Color> colorOf(String property, CSSStyleRule rule) {
    for (var value : valuesOf(property, rule)) {
      var color = swtColorOf(value);
      if (color.isPresent())
        return color;
    }
    return Optional.empty();
  }

  /**
   * Collect the expression values for the given property from the given
   * rule.
   */
  private static List<String> valuesOf(String property, CSSStyleRule rule) {
    if (rule == null)
      return Collections.emptyList();
    List<String> values = null;
    for (int i = 0; i < rule.getDeclarationCount(); i++) {
      var declaration = rule.getDeclarationAtIndex(i);
      if (declaration == null)
        continue;
      if (!Objects.equals(property, declaration.getProperty()))
        continue;
      var expression = declaration.getExpression();
      for (int j = 0; j < expression.getMemberCount(); j++) {
        var member = expression.getMemberAtIndex(j);
        if (member == null)
          continue;
        if (values == null) {
          values = new ArrayList<>();
        }
        values.add(member.getAsCSSString());
      }
    }
    return values == null
      ? Collections.emptyList()
      : values;
  }

  static Optional<Color> swtColorOf(String cssColor) {
    if (cssColor == null)
      return Optional.empty();
    var s = cssColor.trim();
    if (s.startsWith("#"))
      return Optional.ofNullable(Colors.fromHex(s));
    var color = switch (s.trim().toLowerCase()) {
      case "white" -> Colors.systemColor(SWT.COLOR_WHITE);
      case "black" -> Colors.systemColor(SWT.COLOR_BLACK);
      case "darkred" -> Colors.systemColor(SWT.COLOR_DARK_RED);
      case "darkgreen" -> Colors.systemColor(SWT.COLOR_DARK_GREEN);
      case "darkblue" -> Colors.systemColor(SWT.COLOR_DARK_BLUE);
      case "darkmagenta" -> Colors.systemColor(SWT.COLOR_DARK_MAGENTA);
      case "darkyellow" -> Colors.systemColor(SWT.COLOR_DARK_YELLOW);
      case "darkcyan" -> Colors.systemColor(SWT.COLOR_DARK_CYAN);
      case "gray", "grey" -> Colors.systemColor(SWT.COLOR_GRAY);
      case "darkgray", "darkgrey" -> Colors.systemColor(SWT.COLOR_DARK_GRAY);
      case "red" -> Colors.systemColor(SWT.COLOR_RED);
      case "green" -> Colors.systemColor(SWT.COLOR_GREEN);
      case "yellow" -> Colors.systemColor(SWT.COLOR_YELLOW);
      case "blue" -> Colors.systemColor(SWT.COLOR_BLUE);
      case "magenta" -> Colors.systemColor(SWT.COLOR_MAGENTA);
      case "cyan" -> Colors.systemColor(SWT.COLOR_CYAN);
      default -> null;
    };
    return Optional.ofNullable(color);
  }

  static String toHex(Color color) {
    if (color == null)
      return "#000000";
    IntFunction<String> fmt = c -> {
      var hex = Integer.toHexString(c);
      if (hex.length() < 2) {
        hex = "0" + hex;
      }
      if (hex.length() > 2) {
        hex = hex.substring(0, 2);
      }
      return hex;
    };

    var r = fmt.apply(color.getRed());
    var g = fmt.apply(color.getGreen());
    var b = fmt.apply(color.getBlue());
    return "#" + r + g + b;
  }

}

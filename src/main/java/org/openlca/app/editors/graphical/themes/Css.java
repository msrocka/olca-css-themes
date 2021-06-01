package org.openlca.app.editors.graphical.themes;

import java.util.Objects;
import java.util.Optional;

import com.helger.css.decl.CSSStyleRule;
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
}

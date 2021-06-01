package org.openlca.app.editors.graphical.themes;

import java.util.Optional;

import com.helger.css.decl.CSSStyleRule;
import org.openlca.core.model.FlowType;

class Css {

  private Css() {
  }

  static boolean isRoot(CSSStyleRule rule) {
    return isPresent(":root", rule);
  }

  public Optional<FlowType> flowTypeOf(CSSStyleRule rule) {
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

  static boolean isPresent(String selector, CSSStyleRule rule) {
    if (rule == null)
      return false;
    for (int i = 0; i < rule.getSelectorCount(); i++) {
      var s = rule.getSelectorAtIndex(i);
      if (s == null)
        continue;
      if (selector.equals(s.getAsCSSString()))
        return true;
    }
    return false;
  }


}

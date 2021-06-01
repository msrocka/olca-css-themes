package org.openlca.app.editors.graphical.themes;

import static org.junit.Assert.*;

import java.util.List;
import java.util.function.Function;

import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.reader.CSSReader;
import org.junit.Test;
import org.openlca.app.util.Colors;
import org.openlca.core.model.FlowType;
import org.openlca.util.Pair;

public class CssTest {

  @Test
  public void testSelectors() {
    Function<String, CSSStyleRule> rule = s -> {
      var css = CSSReader.readFromString(s + " { }", ECSSVersion.CSS30);
      assertNotNull(css);
      return css.getStyleRuleAtIndex(0);
    };

    assertTrue(Css.isRoot(rule.apply(":root")));
    assertTrue(Css.isInfo(rule.apply(".info")));
    assertTrue(Css.isBox(rule.apply(".box")));
    assertTrue(Css.isLabel(rule.apply(".label")));

    // flow types
    var flowPairs = List.of(
      Pair.of(".product", FlowType.PRODUCT_FLOW),
      Pair.of(".waste", FlowType.WASTE_FLOW),
      Pair.of(".elementary-flow", FlowType.ELEMENTARY_FLOW));
    for (var pair : flowPairs) {
      var flowType = Css.flowTypeOf(rule.apply(pair.first));
      assertTrue(flowType.isPresent());
      assertEquals(pair.second, flowType.get());
    }

    // box types
    var boxSelectors = new String[]{
      ".unit-process",
      ".system-process",
      ".library-process",
      ".sub-system",
    };
    var expectedBoxTypes = new Theme.BoxType[]{
      Theme.BoxType.UNIT_PROCESS,
      Theme.BoxType.SYSTEM_PROCESS,
      Theme.BoxType.LIBRARY_PROCESS,
      Theme.BoxType.SUB_SYSTEM,
    };
    for (int i = 0; i < boxSelectors.length; i++) {
      var r = rule.apply(".box " + boxSelectors[i]);
      assertTrue(Css.isBox(r));
      var boxType = Css.boxTypeOf(r);
      assertTrue("failed to get box-type for " + boxSelectors[i], boxType.isPresent());
      assertEquals(expectedBoxTypes[i], boxType.get());
    }
  }

  @Test
  public void testColor() {
    assertEquals("#ffffff", Css.toHex(Css.color("white")));
    assertEquals("#000000", Css.toHex(Css.color("black")));
    assertEquals("#123456", Css.toHex(Css.color("#123456")));
    assertEquals("#ff0011", Css.toHex(Css.color("#f01")));
  }

}

package org.openlca.app.editors.graphical.themes;

import static org.junit.Assert.*;

import java.util.List;
import java.util.function.Function;

import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.reader.CSSReader;
import org.eclipse.swt.graphics.Color;
import org.junit.Test;
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
  public void testSwtColorOf() {
    var input = new String[] {
      "white",
      "black",
      "#123456",
      "#f01"
    };
    var expected = new String[] {
      "#ffffff",
      "#000000",
      "#123456",
      "#ff0011",
    };
    for (int i = 0; i < input.length; i++) {
      var swt = Css.swtColorOf(input[i]);
      assertTrue(swt.isPresent());
      assertEquals(expected[i], Css.toHex(swt.get()));
    }
  }

  @Test
  public void testGetColors() {
    Function<String, CSSStyleRule> rule = prop -> {
      var s = ".box { " + prop + ": solid #123456 1px; }";
      return firstRuleOf(s);
    };

    var colors = new Color[] {
      Css.getColor(rule.apply("color")).orElseThrow(),
      Css.getBackgroundColor(rule.apply("background")).orElseThrow(),
      Css.getBackgroundColor(rule.apply("background-color")).orElseThrow(),
      Css.getBorderColor(rule.apply("border")).orElseThrow(),
      Css.getBorderColor(rule.apply("border-color")).orElseThrow(),
    };
    for (var color : colors) {
      assertEquals("#123456", Css.toHex(color));
    }
  }

  @Test
  public void testBorderWidth() {
    var style = ".box { border: 4px solid #fff; }";
    var rule = firstRuleOf(style);
    assertEquals(4, Css.getBorderWidth(rule).orElseThrow());
  }

  @Test
  public void testThemeNameOf() {
    var css = CSSReader.readFromString(
      ":root { --name: 'Example theme'; }",
      ECSSVersion.CSS30);
    assertEquals(
      "Example theme",
      Css.themeNameOf(css).orElseThrow());
  }


  private CSSStyleRule firstRuleOf(String css) {
    var parsedCss = CSSReader.readFromString(css, ECSSVersion.CSS30);
    assertNotNull(parsedCss);
    return parsedCss.getStyleRuleAtIndex(0);
  }

}

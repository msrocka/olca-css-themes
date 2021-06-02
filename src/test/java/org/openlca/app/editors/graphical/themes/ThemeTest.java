package org.openlca.app.editors.graphical.themes;

import java.io.File;
import java.util.function.Function;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openlca.app.util.Colors;
import org.openlca.core.model.FlowType;
import org.openlca.util.Pair;

public class ThemeTest {

  @Test
  public void testEmpty() {
    var theme = Theme.defaults("Default");
    assertEquals("Default", theme.name());
    assertFalse(theme.isDark());

    for (var box : Theme.Box.values()) {
      assertEquals(Colors.black(), theme.boxFontColor(box));
      assertEquals(Colors.white(), theme.boxBackgroundColor(box));
      assertEquals(Colors.black(), theme.boxBorderColor(box));
      assertEquals(1, theme.boxBorderWidth(box));
    }

    assertEquals(Colors.white(), theme.graphBackgroundColor());
    assertEquals(Colors.black(), theme.linkColor());
    assertEquals(Colors.black(), theme.infoLabelColor());

    for (var flowType : FlowType.values()) {
      assertEquals(Colors.black(), theme.labelColor(flowType));
      assertEquals(Colors.black(), theme.labelColor(flowType));
    }
  }

  @Test
  public void testDefaultTheme() {
    var themes = Themes.loadFrom(new File("./target/test-themes"));
    var theme = themes.stream()
      .filter(t -> t.name().equals("Default"))
      .findAny()
      .orElseThrow();

    assertFalse(theme.isDark());
    assertEquals("#ffffff", Css.toHex(theme.graphBackgroundColor()));

    // links
    assertEquals("#000000", Css.toHex(theme.linkColor()));
    checkFlowType(type -> Pair.of("#000000", Css.toHex(theme.linkColor(type))));

    // box font
    checkBox(box -> Pair.of("#000000", Css.toHex(theme.boxFontColor(box))));

    // border widths
    checkBox(box -> {
      int expected = switch (box) {
        case DEFAULT, UNIT_PROCESS -> 1;
        default -> 2;
      };
      int actual = theme.boxBorderWidth(box);
      return Pair.of(expected, actual);
    });

    // border colors
    checkBox(box -> {
      var expected = switch (box) {
        case LIBRARY_PROCESS -> "#ff9700";
        case SUB_SYSTEM -> "#125985";
        default -> "#800080";
      };
      return Pair.of(expected, Css.toHex(theme.boxBorderColor(box)));
    });

    // labels
    assertEquals("#bfbfbf", Css.toHex(theme.infoLabelColor()));
    checkFlowType(
      flowType -> Pair.of("#000000", Css.toHex(theme.labelColor(flowType))));
  }

  @Test
  public void testDarkTheme() {
    var themes = Themes.loadFrom(new File("./target/test-themes"));
    var theme = themes.stream()
      .filter(t -> t.name().equals("Dark"))
      .findAny()
      .orElseThrow();

    assertTrue(theme.isDark());
    assertEquals("#282a36", Css.toHex(theme.graphBackgroundColor()));

    // links
    assertEquals("#f2f2f2", Css.toHex(theme.linkColor()));
    checkFlowType(flowType -> {
      var expected = switch (flowType) {
        case PRODUCT_FLOW -> "#8be9fd";
        case WASTE_FLOW -> "#ffb86c";
        case ELEMENTARY_FLOW -> "#50fa7b";
      };
      return Pair.of(expected, Css.toHex(theme.linkColor(flowType)));
    });

    // labels
    assertEquals("#f2f2f2", Css.toHex(theme.infoLabelColor()));
    checkFlowType(flowType -> {
      var expexted = switch (flowType) {
        case PRODUCT_FLOW -> "#8be9fd";
        case WASTE_FLOW -> "#ffb86c";
        case ELEMENTARY_FLOW -> "#50fa7b";
      };
      return Pair.of(expexted, Css.toHex(theme.labelColor(flowType)));
    });
  }

  private <R> void checkBox(Function<Theme.Box, Pair<R, R>> fn) {
    for (var box : Theme.Box.values()) {
      var r = fn.apply(box);
      assertEquals("failed for Box=" + box, r.first, r.second);
    }
  }

  private <R> void checkFlowType(Function<FlowType, Pair<R, R>> fn) {
    for (var type : FlowType.values()) {
      var r = fn.apply(type);
      assertEquals("failed for FlowType=" + type, r.first, r.second);
    }
  }
}

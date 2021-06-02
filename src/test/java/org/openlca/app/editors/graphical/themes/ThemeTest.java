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
    assertEquals(Colors.black(), theme.defaultLinkColor());
    assertEquals(Colors.black(), theme.infoFontColor());

    for (var flowType : FlowType.values()) {
      assertEquals(Colors.black(), theme.fontColorOf(flowType));
      assertEquals(Colors.black(), theme.fontColorOf(flowType));
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
  }

  @Test
  public void testDarkTheme() {
    var themes = Themes.loadFrom(new File("./target/test-themes"));
    var theme = themes.stream()
      .filter(t -> t.name().equals("Dark"))
      .findAny()
      .orElseThrow();

    assertTrue(theme.isDark());
  }

  private <R> void checkBox(Function<Theme.Box, Pair<R, R>> fn) {
    for (var box : Theme.Box.values()) {
      var r = fn.apply(box);
      assertEquals("failed for Box=" + box, r.first, r.second);
    }
  }
}

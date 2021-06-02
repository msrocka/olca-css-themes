package org.openlca.app.editors.graphical.themes;

import java.io.File;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openlca.app.util.Colors;
import org.openlca.core.model.FlowType;

public class ThemeTest {

  @Test
  public void testEmpty() {
    var theme = Theme.defaults("Default");
    assertEquals("Default", theme.name());
    assertFalse(theme.isDark());

    for (var box : Theme.BoxType.values()) {
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
}

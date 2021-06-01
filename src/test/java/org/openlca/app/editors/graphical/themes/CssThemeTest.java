package org.openlca.app.editors.graphical.themes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openlca.app.util.Colors;
import org.openlca.core.model.FlowType;

public class CssThemeTest {

  @Test
  public void testDefaults() {
    var theme = CssTheme.defaults("Default");
    assertEquals("Default", theme.name());
    assertFalse(theme.isDark());
    assertEquals(Colors.black(), theme.defaultFontColor());
    assertEquals(Colors.white(), theme.defaultBackgroundColor());
    assertEquals(Colors.black(), theme.defaultBorderColor());
    assertEquals(Colors.black(), theme.defaultLinkColor());
    assertEquals(Colors.black(), theme.infoFontColor());
    assertEquals(1, theme.defaultBorderWidth());

    for (var boxType : CssTheme.BoxType.values()) {
      assertEquals(Colors.black(), theme.fontColorOf(boxType));
      assertEquals(Colors.white(), theme.backgroundColorOf(boxType));
      assertEquals(Colors.black(), theme.borderColorOf(boxType));
      assertEquals(1, theme.borderWidthOf(boxType));
    }

    for (var flowType : FlowType.values()) {
      assertEquals(Colors.black(), theme.fontColorOf(flowType));
      assertEquals(Colors.black(), theme.fontColorOf(flowType));
    }

  }
}

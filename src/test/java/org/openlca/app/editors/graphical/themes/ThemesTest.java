package org.openlca.app.editors.graphical.themes;

import java.io.File;

import static org.junit.Assert.*;

import org.junit.Test;

public class ThemesTest {

  @Test
  public void testLoadFrom() {
    var themes = Themes.loadFrom(new File("./target/test-themes"));
    assertTrue(themes.size() > 1);

    // find the Default theme
    var defaultTheme = themes.stream()
      .filter(theme -> theme.name().equals("Default"))
      .findAny();
    assertTrue(defaultTheme.isPresent());

    // find the Dark theme
    var darkTheme = themes.stream()
      .filter(theme -> theme.name().equals("Dark"))
      .findAny();
    assertTrue(darkTheme.isPresent());
  }

}

package org.openlca.app.editors.graphical.themes;

import java.io.File;
import java.util.Optional;

import com.helger.css.reader.CSSReader;
import com.helger.css.reader.CSSReaderSettings;

/**
 * Contains just the meta-data of a theme. Loading a full theme can allocate
 * resources like colors, fonts etc. Thus, for things like showing available
 * themes etc. it is better to just use instances of this class.
 */
public class ThemeInfo {

  private final String file;
  private final String name;

  private ThemeInfo(String file, String name) {
    this.file = file;
    this.name = name;
  }

  static Optional<ThemeInfo> readFrom(File file) {
    if (file == null)
      return Optional.empty();
    var settings = new CSSReaderSettings();
    var css = CSSReader.readFromFile(file, settings);
    if (css == null)
      return Optional.empty();
    // select the theme name
    var name = Css.themeNameOf(css).orElse(null);
    if (name == null || name.isBlank()) {
      name = file.getName();
      if (name.endsWith(".css")) {
        name = name.substring(0, name.length() - 4);
      }
    }
    var info = new ThemeInfo(file.getName(), name);
    return Optional.of(info);
  }

  public String file() {
    return file;
  }

  public String name() {
    return name;
  }
}

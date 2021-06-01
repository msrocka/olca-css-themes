package org.openlca.app.editors.graphical.themes;

import java.io.File;

import com.helger.css.decl.visit.CSSVisitor;
import com.helger.css.decl.visit.ICSSVisitor;
import com.helger.css.reader.CSSReader;
import com.helger.css.reader.CSSReaderSettings;

public class Main {

  public static void main(String[] args) {
    var settings = new CSSReaderSettings();
    var css = CSSReader.readFromFile(new File("Default.css"), settings);
    /*
    System.out.println(css.getStyleRuleCount());

    var rule = css.getStyleRuleAtIndex(0);
    System.out.println(rule.getDeclarationCount());
    var declaration = rule.getDeclarationAtIndex(0);
    System.out.println(declaration.getProperty());
    var expression = declaration.getExpression();
    System.out.println(expression.getMemberCount());
    var member = expression.getMemberAtIndex(0);
    System.out.println(member.getAsCSSString());
*/
    for (var rule : css.getAllStyleRules()) {
      for (var selector: rule.getAllSelectors()) {
        System.out.println(selector.getAsCSSString());
      }
    }

    /*
    for (var rule : css.getAllStyleRules()) {
      System.out.println(rule.getSelectorAtIndex(0).getAllMembers().iterator().next().getAsCSSString());
    }
    */
  }
}

# CSS styling for the graphical editor in openLCA

In openLCA 2, you can select a color themes for the graphical editor (via the
context menu of the editor > settings):

![](images/settings.png)

A theme can be configured in a CSS file. openLCA 2 stores these themes in the
`graph-themes` folder of the openLCA workspace (which is currently still
`~/openLCA-data-1.4`; on Windows `C:/Users/[YOU]/openLCA-data-1.4`):

![](images/workspace.png)

A new theme can be added by simply adding a CSS file to that folder:

![](images/theme-added-workspace.png)

The easiest way to create a new theme is to copy an existing one. The comments
in the [default theme CSS files](./src/main/resources/org/openlca/app/editors/graphical/themes/Dark.css)
should be helpful:

```css
/* basic attributes and graph settings */
:root {
  --name: 'Dark';         /* the name of the theme */
  --mode: 'dark';         /* this is a dark theme */
  background: #282a36;  /* the background color of the graph */
}

/* -- process boxes -- */

/* defaults */
.box {
  color: #f8f8f2;       /* font color */
  background: #282A36;  /* background color */
  border: 2px #ff79c6;  /* border width and color */
}

/* ... */
```




Note that we only support a small subset of CSS for the styling (e.g. just
hex-values for colors like `#123456` and a few color names like `blue` or
`red`). The best way to create a new theme is just to copy an existing theme and
to modify it. Here are some examples:

* The current [Default]() theme:

* The current [Dark]() theme:

* The current [Light]() theme:

* The current [Gray]() theme:

* The [Happy Unicorn]() theme (not meant that seriously; but shows all options):


https://download.eclipse.org/eclipse/downloads/drops4/R-4.19-202103031800/

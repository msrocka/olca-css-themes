# CSS styling for the graphical editor in openLCA

In openLCA 2 you can select different themes for the look and feel of the
graphical editor:

...

A theme can be configured in a CSS file. openLCA 2 stores these themes in the
`graph-themes` folder of the openLCA workspace (which is currently still
`~/openLCA-data-1.4`; on Windows `C:/Users/[YOU]/openLCA-data-1.4`):

...

A new theme can be added by simply adding a CSS file to that folder:

...

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

# Arrows

Search for solutions to an interesting puzzle which features an apparently random set of compass point 
aligned arrows in 2 4x4 grids. A counter is placed top left on each grid. The player chooses which counter
to move at each turn - either the one on grid 0, or grid 1. However, each counter can only move in 
the direction indicated by the arrow underneath the other counter. The goal is to find a way to
move both counters to the bottom right of their grids.

## Installation

Install a java development kit. The program is not fussy which you have.
[OpenJDK 8 or later](https://openjdk.java.net/install/) will do fine.

Install [Leiningen](https://leiningen.org/). This will also install [Clojure](https://clojure.org/)

## Setting up a REPL

Use a clojure REPL (Read, Eval Print, Loop) to explore the possibilities. Leiningen installs
one. Change directory to the project folder, and say
```
lein repl
```
The source code is in arrows/src/arrows/core.clj. You can edit that code while the REPL is
running to try out different functions. Here's a transcript of a REPL session.

```
nREPL server started on port 60256 on host 127.0.0.1 - nrepl://127.0.0.1:60256
REPL-y 0.4.3, nREPL 0.6.0
Clojure 1.10.0
Java HotSpot(TM) 64-Bit Server VM 1.8.0_152-b16
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

arrows.core=> (arrow-under 0 0 0)
:s
arrows.core=> (arrow-under 0 3 0)
:n
arrows.core=> (move-under 0 0 0)
[0 1]
arrows.core=> (move-under 0 3 0)
[0 -1]
arrows.core=> (find-route [[3 0] [3 0]] [[0 3] [0 3]])
No solution found
nil
arrows.core=> 
```

To print a solution to the problem, use:
```
(find-route [[3 0] [3 0]] [[0 3] [0 3]])
```

You can change the puzzle by redefining grid.
```
```

## License

Copyright © 2020 Mike Pearson 

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/4d/Hebrew_University_Logo.svg/1200px-Hebrew_University_Logo.svg.png" alt="Hebrew University" height="110">
</p>

<h1 align="center">Introduction to Object-Oriented Programming (Java)</h1>

<p align="center">
  Coursework repository from the <a href="https://new.huji.ac.il/">Hebrew University of Jerusalem</a>.
  This OOP course covered fundamentals & patterns using <b>Java</b>.
</p>

---

## Overview
This repository contains a series of six exercises completed for the university OOP course, including a final **SJava Compiler** exercise. The course focuses on OOP principles, design patterns, generics, collections, regex, and basic parsing.

### Learning goals
- **Core OOP:** encapsulation, inheritance, polymorphism, abstraction  
- **Design & architecture:** interfaces, composition, common patterns  
- **Collections & generics:** Lists/Sets/Maps, iterators, type safety  
- **Testing & robustness:** edge cases, exceptions, contracts  
- **Parsing & regex:** input processing, simple DSL concepts

---

## Repository structure
- **[Exercise 1 – ChatterBot](./Exercise%201%20-%20ChatterBot/)** – basic OOP structure, interfaces & behaviors  
- **[Exercise 2 – TicTacToe](./Exercise%202%20-%20TicTacToe/)** – modeling state, turn logic, simple AI hooks  
- **[Exercise 3 – Bricker](./Exercise%203%20-%20Bricker/)** – sprite/entities, collisions, game loop concepts  
- **[Exercise 4 – Ascii Art](./Exercise%204%20-%20Ascii%20Art/)** – I/O, regex/string processing, transformation pipeline  
- **[Exercise 5 – 2D Game](./Exercise%205%20-%202D%20Game/)** – scenes, composition, larger codebase organization  
- **[Exercise 6 – SJava Compiler](./Exercise%206%20-%20SJava%20Compiler/)** – parsing/validation of a simplified Java-like language

> Each exercise folder contains code and (when applicable) a `media/` directory with screenshots.

---

## Run instructions (generic)
> The exact entry point may differ per exercise. If an exercise contains a `main` class, you can run it directly via your IDE (IntelliJ recommended). For a simple terminal build:

```bash
# from the specific exercise folder
find . -name "*.java" > sources.txt
javac -d out @sources.txt
# replace MainClass with the exercise’s entry point (if applicable)
java -cp out MainClass
```

---

## Media

<!-- TicTacToe (one image) -->
<table>
  <tr>
    <td>
      <a href="./Exercise 2 - TicTacToe">
        <img src="./Exercise 2 - TicTacToe/media/1.png" alt="TicTacToe" width="320">
      </a>
    </td>
  </tr>
</table>

<!-- Bricker (two images) -->
<table>
  <tr>
    <td>
      <a href="./Exercise 3 - Bricker">
        <img src="./Exercise 3 - Bricker/media/1.png" alt="Bricker 1" width="320">
      </a>
    </td>
    <td width="16"></td>
    <td>
      <a href="./Exercise 3 - Bricker">
        <img src="./Exercise 3 - Bricker/media/2.png" alt="Bricker 2" width="320">
      </a>
    </td>
  </tr>
</table>

<!-- Ascii Art (two images) -->
<table>
  <tr>
    <td>
      <a href="./Exercise 4 - Ascii Art">
        <img src="./Exercise 4 - Ascii Art/media/AsciiArt.png" alt="ASCII before" width="320">
      </a>
    </td>
    <td width="16"></td>
    <td>
      <a href="./Exercise 4 - Ascii Art">
        <img src="./Exercise 4 - Ascii Art/media/noamWebPic.jpg" alt="ASCII after" width="320">
      </a>
    </td>
  </tr>
</table>

<!-- 2D Game (three images) -->
<table>
  <tr>
    <td>
      <a href="./Exercise 5 - 2D Game">
        <img src="./Exercise 5 - 2D Game/media/1.png" alt="2D Game 1" width="320">
      </a>
    </td>
    <td width="16"></td>
    <td>
      <a href="./Exercise 5 - 2D Game">
        <img src="./Exercise 5 - 2D Game/media/2.png" alt="2D Game 2" width="320">
      </a>
    </td>
    <td width="16"></td>
    <td>
      <a href="./Exercise 5 - 2D Game">
        <img src="./Exercise 5 - 2D Game/media/3.png" alt="2D Game 3" width="320">
      </a>
    </td>
  </tr>
</table>

---

## Contributing
Pull requests are welcome. For significant changes, please open an issue first to discuss the scope and approach.

## License
This project is licensed under the **MIT License**. See the [LICENSE](./LICENSE) file or learn more at the MIT template.

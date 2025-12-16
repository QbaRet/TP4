# UML Diagrams for Go Game

This directory contains PlantUML diagrams for the Go game implementation.

## Files

- `class-diagram.puml` - Complete class structure with Observer pattern
- `state-diagram.puml` - State machines for Server, Client, and GameSession
- `sequence-diagram.puml` - Full game flow from connection to game end

## How to Generate Images

### Option 1: VS Code with PlantUML Extension

1. Install the PlantUML extension (jebbs.plantuml)
2. Install Graphviz: `sudo apt install graphviz`
3. Open any `.puml` file
4. Press `Alt+D` to preview or `Ctrl+Shift+P` → "PlantUML: Export Current Diagram"

### Option 2: Online

Visit https://www.planttext.com/ and paste the content of any `.puml` file.

### Option 3: Command Line

```bash
# Install PlantUML
sudo apt install plantuml

# Generate all diagrams
plantuml uml/*.puml

# Or individually
plantuml uml/class-diagram.puml
plantuml uml/state-diagram.puml
plantuml uml/sequence-diagram.puml
```

This will generate PNG files in the same directory.

## Diagram Descriptions

### Class Diagram
Shows the complete object-oriented structure including:
- Core logic classes (Board, GameMechanics, Protocol)
- Observer pattern (GameObserver, GameModel)
- Client-server architecture (GoServer, GameSession, GoClient)
- UI abstraction (GameView, ConsoleView)
- Enums (Stone, Direction)

### State Diagram
Illustrates state transitions for:
- **Server**: waiting → player1 connected → game running → ended
- **Client**: connecting → ready → my turn ↔ opponent turn → game over
- **GameSession**: player1 turn ↔ player2 turn, with validation substates

### Sequence Diagram
Complete interaction flow showing:
1. Connection phase (both players joining)
2. Valid move execution with board updates
3. Invalid move handling
4. Pass command
5. Capture scenario with counter updates
6. Game ending via surrender

Each scenario demonstrates the full message flow between actors, clients, server, session, mechanics, and model.

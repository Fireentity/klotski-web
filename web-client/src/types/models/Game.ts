interface Point {
    x: number
    y: number
}

enum Direction {
    RIGHT,
    LEFT,
    UP,
    DOWN
}

interface Tile {
    id: number
    position: Point
    width: number,
    height: number
}

interface Move {
    tiles: Tile
    direction: Direction
}

export default interface Game {
    date: string
    duration: number
    moves: Move[]
}

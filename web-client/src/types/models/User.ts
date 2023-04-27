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

interface Game {
    date: number
    duration: number
    moves: Move[]
}

export default interface User {
    name: string
    games: Game[]
}
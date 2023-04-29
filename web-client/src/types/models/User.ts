import Game from "@/types/models/Game.ts";

export default interface User {
    name: string
    games: Game[]
}
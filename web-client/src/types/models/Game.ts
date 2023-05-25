import Board from "@/types/models/Board.ts";

export default class Game {
    id: number;
    startConfigurationId: number;
    date: string;
    finished: boolean;
    moves: number;
    board: Board;

    constructor(id: number, startConfigurationId: number, date: string, finished: boolean, moves: number, board: Board) {
        this.id = id;
        this.startConfigurationId = startConfigurationId;
        this.date = date;
        this.finished = finished;
        this.moves = moves;
        this.board = board;
    }
}
import Board from "@/types/models/Board.ts";

export default class Game {
    id: number;
    startConfigurationId: number;
    date: string;
    duration: number;
    finished: boolean;
    board: Board;

    constructor(id: number, startConfigurationId: number, date: string, duration: number, finished: boolean, board: Board) {
        this.id = id;
        this.startConfigurationId = startConfigurationId;
        this.date = date;
        this.duration = duration;
        this.finished = finished;
        this.board = board;
    }
}
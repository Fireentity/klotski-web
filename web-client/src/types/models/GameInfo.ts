export default class GameInfo {
    id: number;
    startConfigurationId: number;
    date: string;
    moves: number;
    duration: number;
    finished: boolean;

    constructor(id: number, startConfigurationId: number, date: string, moves: number, duration: number, finished: boolean) {
        this.id = id;
        this.startConfigurationId = startConfigurationId;
        this.date = date;
        this.moves = moves;
        this.duration = duration;
        this.finished = finished;
    }
}
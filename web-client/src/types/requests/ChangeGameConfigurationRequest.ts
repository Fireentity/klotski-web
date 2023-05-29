export default class ChangeGameConfigurationRequest {
    gameId: number;
    startConfigurationId: number;

    constructor(gameId: number, startConfigurationId: number) {
        this.gameId = gameId;
        this.startConfigurationId = startConfigurationId;
    }
}
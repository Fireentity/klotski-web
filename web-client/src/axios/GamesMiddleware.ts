import {AxiosInstance} from "axios";
import Tile from "@/types/models/Tile.ts";
import Config from "@/config/Config.ts";

export default class GamesMiddleware {
    /**
     * Crea un'istanza del middleware Games.
     * @param axios - L'istanza di Axios a cui il middleware deve essere applicato.
     */
    constructor(axios: AxiosInstance) {
        axios.interceptors.response.use(
            response => {
                // Verifica se la risposta non è relativa alle richieste di /games
                if (!response.config.url?.includes("/games")) {
                    return response;
                }
                // Verifica se la risposta non contiene dati
                if (response.data.length === 0) {
                    return response;
                }
                // Parsing dei tiles nella risposta
                const tiles: Tile[] = [];
                response.data.board.tiles.forEach((tile) => {
                    tiles.push(Config.typeAdapterFactory.parse(tile))
                })
                response.data.board.tiles = tiles
                return response;
            }
        );
    }
}
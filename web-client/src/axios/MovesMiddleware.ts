import {AxiosInstance} from "axios";
import Tile from "@/types/models/Tile.ts";
import Config from "@/config/Config.ts";

export default class MovesMiddleware {
    /**
     * Crea un'istanza del middleware Moves.
     * @param axios - L'istanza di Axios a cui il middleware deve essere applicato.
     */
    constructor(axios: AxiosInstance) {
        axios.interceptors.response.use(
            response => {
                // Verifica se la risposta non è relativa alle richieste di /moves
                if (!response.config.url?.includes("/moves")) {
                    return response;
                }
                // Parsing dei tiles nella risposta
                const tiles: Tile[] = [];
                response.data.tiles.forEach((tile) => {
                    tiles.push(Config.typeAdapterFactory.parse(tile))
                })
                response.data.tiles = tiles
                return response;
            }
        );
    }
}
import {AxiosInstance} from "axios";
import Tile from "@/types/models/Tile.ts";
import Config from "@/config/Config.ts";

export default class GamesMiddleware {
    constructor(axios: AxiosInstance) {
        axios.interceptors.response.use(
            response => {
                if (!response.config.url?.includes("/games")) {
                    return response;
                }
                if(response.data.length === 0) {
                    return response;
                }
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
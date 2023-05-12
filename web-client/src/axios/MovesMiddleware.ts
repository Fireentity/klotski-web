import {AxiosInstance} from "axios";
import Tile from "@/types/models/Tile.ts";
import Config from "@/config/Config.ts";

export default class MovesMiddleware {
    constructor(axios: AxiosInstance) {
        axios.interceptors.response.use(
            response => {
                if (!response.config.url?.includes("/moves")) {
                    return response;
                }
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
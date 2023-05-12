import {AxiosInstance} from "axios";
import Tile from "@/types/models/Tile.ts";
import Config from "@/config/Config.ts";

export default class StartConfigurationsMiddleware {
    constructor(axios: AxiosInstance) {
        axios.interceptors.response.use(
            response => {
                if (!response.config.url?.includes("/configurations")) {
                    return response;
                }
                response.data.forEach((configuration) => {
                    const tiles: Tile[] = [];
                    configuration.tiles.forEach((tile) => {
                        tiles.push(Config.typeAdapterFactory.parse(tile))
                    })
                    configuration.tiles = tiles;
                })
                return response;
            }
        );
    }
}
import {AxiosInstance} from "axios";
import Tile from "@/types/models/Tile.ts";
import Config from "@/config/Config.ts";

export default class StartConfigurationsMiddleware {
    /**
     * Crea un'istanza del middleware StartConfigurations.
     * @param axios - L'istanza di Axios a cui il middleware deve essere applicato.
     */
    constructor(axios: AxiosInstance) {
        axios.interceptors.response.use(
            response => {
                // Verifica se la risposta non è relativa alle richieste di /configurations
                if (!response.config.url?.includes("/configurations")) {
                    return response;
                }

                // Parsing delle configurazioni di partenza nella risposta
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
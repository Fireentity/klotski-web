import Tile from "@/types/models/Tile.ts";
import {ComponentPublicInstance} from "vue";

export default class Result {
    component: ComponentPublicInstance | null = null;
    tile: Tile | null = null;

}

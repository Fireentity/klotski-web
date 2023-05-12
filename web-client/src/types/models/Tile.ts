import {TileVisitor} from "@/types/visitors/TileVisitor.ts";

export default interface Tile {
    accept(visitor: TileVisitor)
}
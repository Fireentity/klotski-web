import RuntimeTypeAdapterFactory from "@/types/factory/RuntimeTypeAdapterFactory.ts";
import RectangularTile from "@/types/models/RectangularTile.ts";
import Tile from "@/types/models/Tile.ts";
import WinningTile from "@/types/models/WinningTile.ts";

const typeAdapterFactory = new RuntimeTypeAdapterFactory('type')
    .addType('rectangular', (data) => {
        return new RectangularTile(data.id, data.x, data.y, data.width, data.height)
    })
    .addType('winning', (data) => {
        return new WinningTile(data.id, data.x, data.y, data.width, data.height)
    });

const fallbackStartConfiguration = {
    boardWidth: 4,
    boardHeight: 5,
    id: 0,
    tiles: [
        {
            type: "rectangular",
            id: 0,
            x: 0,
            y: 0,
            width: 1,
            height: 2
        },
        {
            type: "winning",
            id: 1,
            x: 1,
            y: 0,
            width: 2,
            height: 2
        },
        {
            type: "rectangular",
            id: 2,
            x: 3,
            y: 0,
            width: 1,
            height: 2
        },
        {
            type: "rectangular",
            id: 3,
            x: 3,
            y: 0,
            width: 1,
            height: 2
        },
        {
            type: "rectangular",
            id: 4,
            x: 0,
            y: 2,
            width: 1,
            height: 2
        },
        {
            type: "rectangular",
            id: 5,
            x: 1,
            y: 2,
            width: 1,
            height: 1
        },
        {
            type: "rectangular",
            id: 6,
            x: 2,
            y: 2,
            width: 1,
            height: 1
        },
        {
            type: "rectangular",
            id: 7,
            x: 3,
            y: 2,
            width: 1,
            height: 2
        },
        {
            type: "rectangular",
            id: 8,
            x: 1,
            y: 3,
            width: 1,
            height: 1
        },
        {
            type: "rectangular",
            id: 9,
            x: 2,
            y: 3,
            width: 1,
            height: 1
        },
        {
            type: "rectangular",
            id: 10,
            x: 1,
            y: 4,
            width: 2,
            height: 1
        }
    ]
}

const tiles: Tile[] = [];
fallbackStartConfiguration.tiles.forEach(tile => {
    tiles.push(typeAdapterFactory.parse(tile))
})


const config = {
    publicPages: [
        '/', '/rules', '/home', '/register', '/login'
    ],
    fallbackStartConfiguration: {
        boardWidth: fallbackStartConfiguration.boardWidth,
        boardHeight: fallbackStartConfiguration.boardHeight,
        id: fallbackStartConfiguration.id,
        tiles: tiles
    },
    typeAdapterFactory: typeAdapterFactory,
    navElements: [
        {name: 'home'}, {name: 'rules'}, {name: 'play'}
    ]
}

export default config
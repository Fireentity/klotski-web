import RuntimeTypeAdapterFactory from '@/types/factory/RuntimeTypeAdapterFactory.ts';
import RectangularTile from '@/types/models/RectangularTile.ts';
import Tile from '@/types/models/Tile.ts';
import WinningTile from '@/types/models/WinningTile.ts';

/**
 * Factory per l'adattamento dei tipi durante la deserializzazione
 * @type {RuntimeTypeAdapterFactory}
 */
const typeAdapterFactory = new RuntimeTypeAdapterFactory('type')
    .addType('rectangular', (data) => {
        return new RectangularTile(data.id, data.x, data.y, data.width, data.height)
    })
    .addType('winning', (data) => {
        return new WinningTile(data.id, data.x, data.y, data.width, data.height)
    });

/**
 * Configurazione di fallback per l'avvio del gioco
 * @type {object}
 */
const fallbackStartConfiguration = {
    boardWidth: 4,
    boardHeight: 5,
    id: 0,
    tiles: [
        {
            type: 'rectangular',
            id: 0,
            x: 0,
            y: 0,
            width: 1,
            height: 2
        },
        {
            type: 'winning',
            id: 1,
            x: 1,
            y: 0,
            width: 2,
            height: 2
        },
        {
            type: 'rectangular',
            id: 2,
            x: 3,
            y: 0,
            width: 1,
            height: 2
        },
        {
            type: 'rectangular',
            id: 3,
            x: 3,
            y: 0,
            width: 1,
            height: 2
        },
        {
            type: 'rectangular',
            id: 4,
            x: 0,
            y: 2,
            width: 1,
            height: 2
        },
        {
            type: 'rectangular',
            id: 5,
            x: 1,
            y: 2,
            width: 1,
            height: 1
        },
        {
            type: 'rectangular',
            id: 6,
            x: 2,
            y: 2,
            width: 1,
            height: 1
        },
        {
            type: 'rectangular',
            id: 7,
            x: 3,
            y: 2,
            width: 1,
            height: 2
        },
        {
            type: 'rectangular',
            id: 8,
            x: 1,
            y: 3,
            width: 1,
            height: 1
        },
        {
            type: 'rectangular',
            id: 9,
            x: 2,
            y: 3,
            width: 1,
            height: 1
        },
        {
            type: 'rectangular',
            id: 10,
            x: 1,
            y: 4,
            width: 2,
            height: 1
        }
    ]
}

/**
 * Array di oggetti Tile deserializzati
 * @type {Array<Tile>}
 */
const tiles: Tile[] = [];
fallbackStartConfiguration.tiles.forEach(tile => {
    tiles.push(typeAdapterFactory.parse(tile))
})

/**
 * Configurazione generale dell'applicazione
 * @type {object}
 */
const config = {
    /**
     * Array delle pagine pubbliche
     * @type {Array<string>}
     */

    publicPages: [
        '/', '/rules', '/home', '/register', '/login', '/win'
    ],

    /**
     * Configurazione di fallback per l'avvio del gioco
     * @type {object}
     */
    fallbackStartConfiguration: {
        boardWidth: fallbackStartConfiguration.boardWidth,
        boardHeight: fallbackStartConfiguration.boardHeight,
        id: fallbackStartConfiguration.id,
        tiles: tiles
    },

    /**
     * Factory per l'adattamento dei tipi durante la deserializzazione
     * @type {RuntimeTypeAdapterFactory}
     */
    typeAdapterFactory: typeAdapterFactory,

    /**
     * Elementi di navigazione
     * @type {Array<object>}
     */
    navElements: [
        {name: 'Home', path: '/home'}, {name: 'Regole', path: '/rules'}, {name: 'Game', path: '/game'}
    ]
}

export default config
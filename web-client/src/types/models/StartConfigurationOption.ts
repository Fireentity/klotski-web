/**
 * @class StartConfigurationOption
 * @classdesc Rappresenta una opzione di configurazione iniziale.
 */
export default class StartConfigurationOption {
    /**
     * @member {string} name - Nome dell'opzione di configurazione.
     */
    name: string;

    /**
     * @member {number} id - ID dell'opzione di configurazione.
     */
    id: number;

    /**
     * @constructor
     * @param {string} label - Etichetta dell'opzione di configurazione.
     * @param {number} id - ID dell'opzione di configurazione.
     */
    constructor(label: string, id: number) {
        this.name = label;
        this.id = id;
    }
}
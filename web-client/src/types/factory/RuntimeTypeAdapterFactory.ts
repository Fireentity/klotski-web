export default class RuntimeTypeAdapterFactory {
    constructors: Map<string, (data: any) => any> = new Map<string, (data: any) => any>()
    typeFieldName: string

    /**
     * @constructor
     * @param {string} typeFieldName - Nome del campo utilizzato per il tipo.
     */
    constructor(typeFieldName: string) {
        this.typeFieldName = typeFieldName;
    }

    /**
     * Aggiunge un nuovo tipo con il suo costruttore alla mappa dei costruttori.
     *
     * @param {string} type - Il tipo da aggiungere.
     * @param {(data: any) => any} create - La funzione costruttrice per il tipo.
     * @returns {RuntimeTypeAdapterFactory} - L'istanza della classe RuntimeTypeAdapterFactory.
     */
    addType(type: string, create: (data: any) => any): RuntimeTypeAdapterFactory {
        this.constructors.set(type, create);
        return this;
    }

    /**
     * Parsa i dati e crea un oggetto in base al tipo.
     *
     * @param {any} data - I dati da parsare.
     * @returns {any} - L'oggetto creato in base al tipo.
     * @throws {Error} - Eccezione se non viene trovato un costruttore per il tipo specificato.
     */
    parse(data: any) {
        const type = Reflect.get(data, this.typeFieldName);
        const create = this.constructors.get(type);
        if(!create) {
            throw new Error(`Unable to find constructor for type ${data.type}`)
        }
        const createdData = create(data);
        Reflect.set(createdData, this.typeFieldName, type);
        return createdData;
    }
}
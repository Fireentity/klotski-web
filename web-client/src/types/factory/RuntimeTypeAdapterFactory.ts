export default class RuntimeTypeAdapterFactory {
    constructors: Map<string, (data: any) => any> = new Map<string, (data: any) => any>()
    typeFieldName: string

    constructor(typeFieldName: string) {
        this.typeFieldName = typeFieldName;
    }

    addType(type: string, create: (data: any) => any): RuntimeTypeAdapterFactory {
        this.constructors.set(type, create);
        return this;
    }

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
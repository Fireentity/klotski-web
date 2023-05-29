export default class StartConfigurationOption {
    name: string;
    id: number;

    constructor(label: string, id: number) {
        this.name = label;
        this.id = id;
    }
}
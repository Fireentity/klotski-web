/**
 * Adds support for vue components
 */

declare module '*.vue' {
    import {defineComponent} from "vue";
    const component: ReturnType<typeof defineComponent>
    // noinspection JSUnusedGlobalSymbols
    export default component
}
<!-- TODO sincronizzare con la partita attuale (se clicco nuova partita o la partita finisce mantiene lo stato di quella vecchia) -->
<script setup lang="ts">
import { Listbox, ListboxButton, ListboxLabel, ListboxOption, ListboxOptions } from '@headlessui/vue'
import {ChevronUpDownIcon, CheckIcon} from "@heroicons/vue/24/outline";
import { ref} from "vue";

const props = defineProps<{
    options: {name: string}[],
    header: string,
    onClick: (option) => {

    }
}>();

const selected = ref(props.options[0])

</script>

<template>
    <Listbox as="div" v-model="selected">
        <ListboxLabel class="block text-sm font-medium text-gray-700">Configurazione corrente</ListboxLabel>
        <div class="relative mt-1">
            <ListboxButton class="relative w-full cursor-default rounded-md border border-gray-300 bg-white py-2 pl-3 pr-10 text-left shadow-sm focus:border-primary-600 focus:outline-none focus:ring-1 focus:ring-primary-600 sm:text-sm">
                <span class="block truncate">{{ selected.name }}</span>
                <span class="pointer-events-none absolute inset-y-0 right-0 flex items-center pr-2">
          <ChevronUpDownIcon class="h-5 w-5 text-gray-400" aria-hidden="true" />
        </span>
            </ListboxButton>

            <transition leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100" leave-to-class="opacity-0">
                <ListboxOptions class="absolute z-10 mt-1 max-h-60 w-full overflow-auto rounded-md bg-white py-1 text-base shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none sm:text-sm">
                    <ListboxOption @click="onClick(option)" :value="option" as="template" v-for="option in props.options" v-slot="{ active, selected }">
                        <li :class="[active ? 'text-white bg-primary-600' : 'text-gray-900', 'relative cursor-default select-none py-2 pl-3 pr-9']">
                            <span :class="[selected ? 'font-semibold' : 'font-normal', 'block truncate']">{{
                                    option.name
                                }}</span>
                            <span v-if="selected" :class="[active ? 'text-white' : 'text-primary-600', 'absolute inset-y-0 right-0 flex items-center pr-4']">
                                <CheckIcon class="h-5 w-5" aria-hidden="true" />
                            </span>
                        </li>
                    </ListboxOption>
                </ListboxOptions>
            </transition>
        </div>
    </Listbox>
</template>
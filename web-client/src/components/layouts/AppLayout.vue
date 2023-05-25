<script setup lang="ts">
import {Disclosure, DisclosureButton, DisclosurePanel} from '@headlessui/vue'
import {Bars3Icon, BellIcon, XMarkIcon} from '@heroicons/vue/24/outline'
import {PuzzlePieceIcon} from '@heroicons/vue/24/solid'
import {useStore} from "vuex";
import Config from "@/config/Config.ts";
import {computed, reactive, ref} from "vue";
import AppLink from "@/components/AppLink.vue";

const store = useStore()
const logout = () => store.dispatch('logout')

const navElements = Config.navElements

const user = store.getters['getUser']

store.dispatch('isAuthenticated', () => {
});

</script>

<template>
    <Disclosure as="nav" class="bg-white shadow" v-slot="{ open }">
        <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
            <div class="flex h-16 justify-between">
                <div class="flex">
                    <div class="-ml-2 mr-2 flex items-center md:hidden">
                        <!-- Mobile menu button -->
                        <DisclosureButton
                                class="inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-gray-100 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-primary-600">
                            <span class="sr-only">Open main menu</span>
                            <Bars3Icon v-if="!open" class="block h-6 w-6" aria-hidden="true"/>
                            <XMarkIcon v-else class="block h-6 w-6" aria-hidden="true"/>
                        </DisclosureButton>
                    </div>
                    <div class="flex flex-shrink-0 items-center">
                        <PuzzlePieceIcon class="w-8 h-8 fill-primary-600"/>

                    </div>
                    <div class="hidden md:ml-6 md:flex md:space-x-8">
                        <AppLink :to="element.path" :href="element.path"
                                     v-for="element in navElements"
                                     inactive-class="inline-flex items-center border-b-2 border-transparent px-1 pt-1 text-sm font-medium text-gray-500 hover:border-gray-300 hover:text-gray-700"
                                     active-class="inline-flex items-center border-b-2 border-primary-600 px-1 pt-1 text-sm font-medium text-gray-900">
                            {{ element.name }}
                        </AppLink>
                    </div>
                </div>
                <div v-if="user.authenticated" class="flex items-center">
                    <div class="hidden md:ml-4 md:flex md:flex-shrink-0 md:items-center">
                        <div class="relative ml-3">
                            <a @click="logout" class="cursor-pointer font-medium text-gray-900">Esci</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <DisclosurePanel class="md:hidden">
            <div class="space-y-1 pt-2 pb-3">
                <AppLink :to="element.path" :href="element.path"
                         v-for="element in navElements"
                         inactive-class="block border-l-4 border-transparent py-2 pl-3 pr-4 text-base font-medium text-gray-500 hover:border-gray-300 hover:bg-gray-50 hover:text-gray-700 sm:pl-5 sm:pr-6"
                         active-class="block border-l-4 border-primary-600 bg-primary-300 py-2 pl-3 pr-4 text-base font-medium text-primary-600 sm:pl-5 sm:pr-6">
                    {{ element.name }}
                </AppLink>
            </div>
            <div v-if="user.authenticated" class="border-t border-gray-200 pt-4 pb-3">
                <div class="mt-3 space-y-1">
                    <DisclosureButton as="a" @click="logout"
                                      class="block px-4 py-2 text-base font-medium text-gray-500 hover:bg-gray-100 hover:text-gray-800 sm:px-6">
                        Esci
                    </DisclosureButton>
                </div>
            </div>
        </DisclosurePanel>
    </Disclosure>
</template>
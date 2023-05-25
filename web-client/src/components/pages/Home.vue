<script setup lang="ts">
import {ChevronRightIcon, StarIcon} from '@heroicons/vue/20/solid'
import router from "@/routes/Router.ts";
import klotski from "~/klotski.png"
import klotskiMixed from "~/klotski-mixed.png"

const redirect = () =>  {
    router.push('/game')
}

const klotskiUrl = klotski;
const klotskiMixedUrl = klotskiMixed;

</script>

<template>
    <div class="bg-white">
        <main>
            <!-- Hero section -->
            <div class="overflow-hidden pt-8 sm:pt-12 lg:relative lg:py-48">
                <div class="mx-auto max-w-md px-6 sm:max-w-3xl lg:grid lg:max-w-7xl lg:grid-cols-2 lg:gap-24 lg:px-8">
                    <div>
                        <div class="mt-20">
                            <div class="mt-6 sm:max-w-xl">
                                <h1 class="text-4xl font-bold tracking-tight text-gray-900 sm:text-5xl">Klotski</h1>
                                <p class="mt-6 text-xl text-gray-500">Sito web in cui potrete divertirvi con il famoso puzzle Klotski e sfidare i vostri amici</p>
                            </div>
                            <form @submit.prevent="redirect" class="mt-12 sm:flex sm:w-full sm:max-w-lg">
                                <div class="mt-4 sm:mt-0 sm:ml-3">
                                    <button type="submit"
                                            class="block w-full rounded-md border border-transparent bg-primary-600 px-5 py-3 text-base font-medium text-white shadow hover:bg-primary-900 focus:outline-none focus:ring-2 focus:ring-primary-600 focus:ring-offset-2 sm:px-10">
                                        Gioca ora
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="sm:mx-auto sm:max-w-3xl sm:px-6">
                    <div class="py-12 sm:relative sm:mt-12 sm:py-16 lg:absolute lg:inset-y-0 lg:right-0 lg:w-1/2">
                        <div class="hidden sm:block">
                            <div class="absolute inset-y-0 left-1/2 w-screen rounded-l-3xl bg-gray-50 lg:left-80 lg:right-0 lg:w-full"/>
                            <svg class="absolute top-8 right-1/2 -mr-3 lg:left-0 lg:m-0" width="404" height="392"
                                 fill="none" viewBox="0 0 404 392">
                                <defs>
                                    <pattern id="837c3e70-6c3a-44e6-8854-cc48c737b659" x="0" y="0" width="20"
                                             height="20" patternUnits="userSpaceOnUse">
                                        <rect x="0" y="0" width="4" height="4" class="text-gray-200"
                                              fill="currentColor"/>
                                    </pattern>
                                </defs>
                                <rect width="404" height="392" fill="url(#837c3e70-6c3a-44e6-8854-cc48c737b659)"/>
                            </svg>
                        </div>
                        <div class="relative -mr-40 pl-6 sm:mx-auto sm:max-w-3xl sm:px-0 lg:h-full lg:max-w-none lg:pl-12">
                            <img class="w-full rounded-md shadow-xl ring-1 ring-black ring-opacity-5 lg:h-full lg:w-auto lg:max-w-none"
                                 :src="klotskiUrl" alt=""/>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Testimonial/stats section -->
            <div class="relative mt-20">
                <div class="lg:mx-auto lg:grid lg:max-w-7xl lg:grid-cols-2 lg:items-start lg:gap-24 lg:px-8">
                    <div class="relative sm:py-16 lg:py-0">
                        <div aria-hidden="true" class="hidden sm:block lg:absolute lg:inset-y-0 lg:right-0 lg:w-screen">
                            <div class="absolute inset-y-0 right-1/2 w-full rounded-r-3xl bg-gray-50 lg:right-72"/>
                            <svg class="absolute top-8 left-1/2 -ml-3 lg:-right-8 lg:left-auto lg:top-12" width="404"
                                 height="392" fill="none" viewBox="0 0 404 392">
                                <defs>
                                    <pattern id="02f20b47-fd69-4224-a62a-4c9de5c763f7" x="0" y="0" width="20"
                                             height="20" patternUnits="userSpaceOnUse">
                                        <rect x="0" y="0" width="4" height="4" class="text-gray-200"
                                              fill="currentColor"/>
                                    </pattern>
                                </defs>
                                <rect width="404" height="392" fill="url(#02f20b47-fd69-4224-a62a-4c9de5c763f7)"/>
                            </svg>
                        </div>
                        <div class="relative mx-auto max-w-md px-6 sm:max-w-3xl lg:max-w-none lg:px-0 lg:py-20">
                            <!-- Testimonial card-->
                            <div class="relative overflow-hidden rounded-2xl pt-64 pb-10 shadow-xl">
                                <img class="absolute inset-0 h-full w-full object-cover"
                                     :src="klotskiUrl"
                                     alt=""/>
                            </div>
                        </div>
                    </div>

                    <div class="relative mx-auto max-w-md px-6 sm:max-w-3xl lg:px-0">
                        <!-- Content area -->
                        <div class="pt-12 sm:pt-16 lg:pt-20">
                            <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Di cosa si tratta</h2>
                            <div class="mt-6 space-y-6 text-gray-500">
                                <p class="text-lg">Klotski è costituito da una griglia  di dimensioni
                                    5x4 dove alcune caselle sono occupate da blocchi di forme e dimensioni diverse. Questi possono essere spostati in
                                    verticale o in orizzontale, ma non sollevati. L'obiettivo è spostare i blocchi in modo da
                                    liberare quello rosso portandolo alla fine del tabellone. Il gioco è un ottimo modo per
                                    esercitare la logica e la risoluzione dei problemi, poiché richiede la pianificazione strategica dei movimenti
                                    per raggiungere l'obiettivo finale.</p>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </main>
    </div>
</template>
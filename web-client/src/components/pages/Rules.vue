<script setup>
import { ArrowPathIcon, ArrowUturnLeftIcon, LightBulbIcon } from '@heroicons/vue/20/solid'
import ruleImg from "~/rulesImg.png"
</script>


<template>
    <div class="h-full relative isolate overflow-hidden bg-white px-6 py-24 sm:py-32 lg:overflow-visible lg:px-0">
        <div class="absolute inset-0 -z-10 overflow-hidden">
            <svg class="absolute left-[max(50%,25rem)] top-0 h-[64rem] w-[128rem] -translate-x-1/2 stroke-gray-200 [mask-image:radial-gradient(64rem_64rem_at_top,white,transparent)]" aria-hidden="true">
                <defs>
                    <pattern id="e813992c-7d03-4cc4-a2bd-151760b470a0" width="200" height="200" x="50%" y="-1" patternUnits="userSpaceOnUse">
                        <path d="M100 200V.5M.5 .5H200" fill="none" />
                    </pattern>
                </defs>
                <svg x="50%" y="-1" class="overflow-visible fill-gray-50">
                    <path d="M-100.5 0h201v201h-201Z M699.5 0h201v201h-201Z M499.5 400h201v201h-201Z M-300.5 600h201v201h-201Z" stroke-width="0" />
                </svg>
                <rect width="100%" height="100%" stroke-width="0" fill="url(#e813992c-7d03-4cc4-a2bd-151760b470a0)" />
            </svg>
        </div>
        <div class="mx-auto grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 lg:mx-0 lg:max-w-none lg:grid-cols-2 lg:items-start lg:gap-y-10">
            <div class="lg:col-span-2 lg:col-start-1 lg:row-start-1 lg:mx-auto lg:grid lg:w-full lg:max-w-7xl lg:grid-cols-2 lg:gap-x-8 lg:px-8">
                <div class="lg:pr-4">
                    <div class="lg:max-w-lg">
                        <h1 class="mt-2 text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Regole del gioco</h1>
                        <p class="mt-6 text-xl leading-8 text-gray-700">Come altri puzzle a blocchi scorrevoli, blocchi di dimensioni diverse sono posizionati all'interno di una scatola, che normalmente ha dimensioni 4 × 5. Tra i blocchi ce n'è uno speciale (di solito il più grande) che deve essere spostato in un'area speciale designata dal tabellone di gioco. </p>
                    </div>
                </div>
            </div>
            <div class="lg:col-span-2 lg:col-start-1 lg:row-start-2 lg:mx-auto lg:grid lg:w-full lg:max-w-7xl lg:grid-cols-2 lg:gap-x-8 lg:px-8">
                <div class="lg:pr-4">
                    <div class="max-w-xl text-base leading-7 text-gray-700 lg:max-w-lg">
                        <p>Il giocatore non è autorizzato a rimuovere i blocchi e può far scorrere i blocchi solo orizzontalmente e verticalmente. L'obiettivo è risolvere il puzzle con un numero minimo di mosse o in un tempo minimo.</p>
                        <ul role="list" class="mt-8 space-y-8 text-gray-600">
                            <li class="flex gap-x-3">
                                <ArrowPathIcon class="mt-1 h-5 w-5 flex-none text-primary-600" aria-hidden="true" />
                                <span><strong class="font-semibold text-gray-900">Reset.</strong> Resetta tutte le mosse fatte fin'ora senza possibilità di tornare indietro</span>
                            </li>
                            <li class="flex gap-x-3">
                                <ArrowUturnLeftIcon class="mt-1 h-5 w-5 flex-none text-primary-600" aria-hidden="true" />
                                <span><strong class="font-semibold text-gray-900">Undo.</strong> Torna indietro di una mossa senza possibilità di recuperarla in futuro</span>
                            </li>
                            <li class="flex gap-x-3">
                                <LightBulbIcon class="mt-1 h-5 w-5 flex-none text-primary-600" aria-hidden="true" />
                                <span><strong class="font-semibold text-gray-900">Hint.</strong> Esegue la migliore mossa da eseguire a partire dalla configurazione attuale della board</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

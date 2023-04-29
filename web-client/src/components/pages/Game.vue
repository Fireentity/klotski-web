<script setup lang="ts">
import Card from "@/components/Card.vue";
import GridTile from "@/components/GridTile.vue";
import User from "@/types/models/User.ts";
import GameInfo from "@/components/GameInfo.vue";

const props = defineProps<{
    user: User
}>();
</script>

<template>
    <div class="min-h-full">
        <main class="py-10">
            <!-- Page header -->
            <div class="mx-auto max-w-3xl px-4 sm:px-6 md:flex md:items-center md:justify-between md:space-x-5 lg:max-w-7xl lg:px-8">
                <div class="flex items-center grow w-full max-w-md">
                    <Card class="w-full">
                        <h2 id="applicant-information-title"
                            class="text-lg font-medium leading-6 text-gray-900">Current Game</h2>
                        <p class="mt-1 max-w-2xl text-sm text-gray-500">Time passed: 00:30</p>
                    </Card>
                </div>
                <div class="justify-stretch mt-6 flex flex-col-reverse space-y-4 space-y-reverse sm:flex-row-reverse sm:justify-end sm:space-y-0 sm:space-x-3 sm:space-x-reverse md:mt-0 md:flex-row md:space-x-3">
                    <button type="button"
                            class="inline-flex items-center justify-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 focus:ring-offset-gray-100">
                        New Game
                    </button>
                    <button type="button"
                            class="inline-flex items-center justify-center rounded-md border border-transparent bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 focus:ring-offset-gray-100">
                        Solve
                    </button>
                </div>
            </div>

            <div class="mx-auto mt-8 grid max-w-3xl grid-cols-1 gap-6 sm:px-6 lg:max-w-7xl lg:grid-flow-col-dense lg:grid-cols-3">
                <div class="space-y-6 lg:col-span-2 lg:col-start-1">
                    <!-- Description list-->
                    <section aria-labelledby="applicant-information-title">
                        <Card>
                            <div class="flex justify-center w-full h-full">
                                <div class="col-span-1 w-full flex justify-center h-full">
                                    <div class="grid grid-cols-5 grid-rows-5 gap-4 w-full aspect-square h-full">
                                        <GridTile class="row-span-2 col-span-1 aspect-square"/>
                                        <GridTile class="row-span-1 col-span-2 aspect-horizontal"/>
                                        <GridTile class="row-span-1 col-span-2 aspect-horizontal"/>
                                        <GridTile class="row-span-1 col-span-2 aspect-horizontal"/>
                                        <GridTile class="row-span-3 col-span-2 aspect-horizontal"/>
                                        <GridTile class="row-span-1 col-span-3 aspect-horizontal"/>
                                        <GridTile class="row-span-1 col-span-2 aspect-horizontal"/>
                                    </div>
                                </div>
                            </div>
                        </Card>
                    </section>
                </div>

                <section aria-labelledby="timeline-title" class="lg:col-span-1 lg:col-start-3">
                    <div class="bg-white px-4 py-5 shadow sm:rounded-lg sm:px-6">
                        <h2 id="timeline-title" class="text-lg font-medium text-gray-900">Partite</h2>

                        <!-- Activity Feed -->
                        <div class="mt-6 flow-root" v-if="props.user.games.length != 0">
                            <ul role="list" class="-mb-8">
                                <li v-for="(game, itemIndex) in props.user.games" :key="itemIndex">
                                    <GameInfo :game="game" :is-last="itemIndex === props.user.games.length-1"></GameInfo>
                                </li>
                            </ul>
                        </div>
                        <div class="justify-stretch mt-6 flex flex-col">
                            <button type="button"
                                    class="inline-flex items-center justify-center rounded-md border border-transparent bg-blue-600 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
                                Advance to offer
                            </button>
                        </div>
                    </div>
                </section>
            </div>
        </main>
    </div>
</template>


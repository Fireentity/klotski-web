<script setup lang="ts">
import {PlayIcon} from "@heroicons/vue/20/solid";
import GameInfo from "@/types/models/GameInfo.ts";

const props = defineProps<{
    game: GameInfo
    isLast: boolean
}>();
</script>

<template>
    <div class="relative pb-8">
        <span v-if="!props.isLast"
              class="absolute top-4 left-4 -ml-px h-full w-0.5 bg-gray-200"
              aria-hidden="true"/>
        <div class="relative flex space-x-3">
            <div>
                <span class="h-10 w-10 rounded-full flex items-center justify-center ring-8 ring-white bg-sky-200">
                    <PlayIcon class="h-5 w-5"/>
                </span>
            </div>
            <div class="flex min-w-0 flex-1 justify-between space-x-4 pt-1.5">
                <div class="flex flex-row">
                    <p class="text-sm text-gray-500 ml-5 mr-10">
                        Mosse: <strong>{{ props.game.moves }}</strong>
                    </p>
                </div>
                <div class="whitespace-nowrap text-right text-sm text-gray-500">
                    <strong><time :datetime="props.game.date">{{ props.game.date }}</time></strong>
                </div>
            </div>
        </div>
    </div>

</template>


<style scoped>

</style>
<script setup lang="ts">
import config from '@/config/Config.ts'
import {ChevronDownIcon, ChevronRightIcon, ChevronLeftIcon, ChevronUpIcon} from "@heroicons/vue/20/solid";

const color = config.tilesColors[Math.floor(Math.random() * config.tilesColors.length)];
</script>

<template>
    <div :style="'background-color: ' + color" class="rounded-lg w-full h-full shadow">
        <div class="transition-all duration-100	 flex flex-row opacity-0 hover:opacity-100 justify-between text-gray-600 h-full w-full">
            <ChevronLeftIcon class="w-7"></ChevronLeftIcon>
            <div class="flex flex-col justify-between">
                <ChevronUpIcon class="h-min w-7"></ChevronUpIcon>
                <ChevronDownIcon class="h-min w-7"></ChevronDownIcon>
            </div>
            <ChevronRightIcon class="w-7"></ChevronRightIcon>
        </div>
    </div>
</template>

<style scoped>

</style>
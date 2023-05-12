<script setup lang="ts">
import config from '@/config/Config.ts'
import {ChevronDownIcon, ChevronRightIcon, ChevronLeftIcon, ChevronUpIcon} from "@heroicons/vue/20/solid";
import RectangularTile from "@/types/models/RectangularTile.ts";
import {useStore} from "vuex";
import Tile from "@/types/models/Tile.ts";
import Direction from "@/types/requests/Direction.ts";
import MoveRequest from "@/types/requests/MoveRequest.ts";

const store = useStore()

const props = defineProps<{
    tile: RectangularTile
    tiles: Tile[]
}>()

const move = (direction) => {
    store.dispatch('move', new MoveRequest(props.tile, direction, store.getters['getCurrentGame'].id, props.tiles))
}
</script>

<template>
    <div class="bg-tile_orange rounded-lg w-full h-full shadow"
         :class="'col-start-' + (props.tile.x + 1) + ' col-span-' + props.tile.width + ' row-start-' + (props.tile.y+1) + ' row-span-' + props.tile.height">
        <div class="transition-all duration-100	 flex flex-row opacity-0 hover:opacity-100 justify-between text-gray-600 h-full w-full">
            <ChevronLeftIcon @click="move(Direction.LEFT)" class="w-7"></ChevronLeftIcon>
            <div class="flex flex-col justify-between">
                <ChevronUpIcon @click="move(Direction.TOP)" class="h-min w-7"></ChevronUpIcon>
                <ChevronDownIcon @click="move(Direction.DOWN)" class="h-min w-7"></ChevronDownIcon>
            </div>
            <ChevronRightIcon @click="move(Direction.RIGHT)" class="w-7"></ChevronRightIcon>
        </div>
    </div>
</template>

<style scoped>

</style>
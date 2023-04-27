<script setup lang="ts">
import config from '@/config/config.ts'

const color = config.tilesColors[Math.floor(Math.random() * config.tilesColors.length)];
</script>

<template>
    <div :style="'background-color: ' + color" class="rounded-lg w-full h-full shadow"></div>
</template>

<style scoped>

</style>
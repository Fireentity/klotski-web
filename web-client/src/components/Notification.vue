<script setup lang="ts">
import {ref} from 'vue'
import {ExclamationCircleIcon} from '@heroicons/vue/24/outline'
import {XMarkIcon} from '@heroicons/vue/20/solid'

const props = defineProps<{
    title: string,
    message: string,
}>();

let show = ref(false)

const close = () => {
    show.value = false;
}

const open = () => {
    show.value = true;
    setInterval(() => {
        close();
    }, 10000);
}

defineExpose({
    open
})
</script>

<template>
  <!-- Notification panel, dynamically insert this into the live region when it needs to be displayed -->
    <transition enter-active-class="transform ease-out duration-300 transition"
                enter-from-class="translate-y-2 opacity-0 sm:translate-y-0 sm:translate-x-2"
                enter-to-class="translate-y-0 opacity-100 sm:translate-x-0"
                leave-active-class="transition ease-in duration-100" leave-from-class="opacity-100"
                leave-to-class="opacity-0">
        <div v-if="show"
             class="pointer-events-auto w-full max-w-sm overflow-hidden rounded-lg bg-white shadow-lg ring-1 ring-black ring-opacity-5">
            <div class="p-4">
                <div class="flex items-start">
                    <div class="flex-shrink-0">
                        <ExclamationCircleIcon class="h-6 w-6 text-red-400" aria-hidden="true"/>
                    </div>
                    <div class="ml-3 w-0 flex-1 pt-0.5">
                        <p class="text-sm font-medium text-gray-900">{{ props.title }}</p>
                        <p class="mt-1 text-sm text-gray-500">{{ props.message }}</p>
                    </div>
                    <div class="ml-4 flex flex-shrink-0">
                        <button type="button" @click="close()"
                                class="inline-flex rounded-md bg-white text-gray-400 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                            <span class="sr-only">Close</span>
                            <XMarkIcon class="h-5 w-5" aria-hidden="true"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>
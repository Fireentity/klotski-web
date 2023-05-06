<script setup>
import { onMounted, ref } from 'vue';

defineProps({
    modelValue: String,
});

defineEmits(['update:modelValue']);

const input = ref(null);

onMounted(() => {
    if (input.value.hasAttribute('autofocus')) {
        input.value.focus();
    }
});

defineExpose({ focus: () => input.value.focus() });
</script>

<template>
    <input
            ref="input"
            class="block w-full appearance-none rounded-md border border-gray-300 px-3 py-2 placeholder-gray-400 shadow-sm focus:border-sism_blue-600 focus:outline-none focus:sism_blue-600 sm:text-sm disabled:bg-gray-100"
            :value="modelValue"
            @input="$emit('update:modelValue', $event.target.value)"
    >
</template>

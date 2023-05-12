<script lang="ts">

import {defineComponent} from "vue";
import AppLayout from "@/components/layouts/AppLayout.vue";
import store from "@/store/Store.ts";

store.dispatch('csrf')

export default defineComponent({
    components: {AppLayout}
})

</script>
<template>
    <AppLayout/>
    <router-view/>
</template>
<script lang="ts">

import {defineComponent} from "vue";
import AppLayout from "@/components/layouts/AppLayout.vue";
import store from "@/store/Store.ts";
import Footer from "@/components/Footer.vue";

store.dispatch('csrf')

export default defineComponent({
    components: {Footer, AppLayout}
})

</script>
<template>
    <AppLayout/>
    <router-view/>
    <Footer/>
</template>
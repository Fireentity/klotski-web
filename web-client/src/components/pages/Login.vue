<script setup lang="ts">
import LoginRequest from "@/types/requests/LoginRequest.ts";
import {useStore} from 'vuex';
import router from "@/routes/Router.ts";
import {reactive, UnwrapNestedRefs} from "vue";
import TextInput from "@/components/TextInput.vue";
import InputLabel from "@/components/InputLabel.vue";
import InputError from "@/components/InputError.vue";

const store = useStore()

interface Form {
    email: string;
    password: string;
    error: string
}

const form: UnwrapNestedRefs<Form> = reactive({
    email: '',
    password: '',
    error: ''
});

const login = (request: LoginRequest) => store.dispatch('login', {
    form: request,
    then: () => {
        router.push({path: "/game"})
    },
    catch: (reason) => {
        if (reason.response.status == 401) {
            form.error = "Username o password errati"
        }
    }
})


const submit = () => {
    login({
        email: form.email,
        password: form.password
    })
}

</script>

<template>
    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Sign in to your
                account</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">

            <form class="space-y-6" @submit.prevent="submit">
                <div class="space-y-1">
                    <InputLabel for="email" value="Email"/>
                    <div class="mt-1">
                        <TextInput
                                id="email"
                                name="email"
                                v-model="form.email"
                                type="email"
                                required
                                autocomplete="email"
                        />
                    </div>
                </div>

                <div class="space-y-1">
                    <InputLabel for="password" value="Password"/>
                    <div class="mt-1">
                        <TextInput
                                id="password"
                                name="password"
                                v-model="form.password"
                                type="password"
                                required
                                autocomplete="password"
                        />
                    </div>
                    <InputError class="mt-2" :message="form.error"/>
                </div>

                <div>
                    <button type="submit"
                            class="flex w-full justify-center rounded-md bg-primary-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-primary-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-primary-600">
                        Sign in
                    </button>
                </div>
            </form>

            <p class="mt-10 text-center text-sm text-gray-500">
                Non hai un account?
                <a href="/register"
                   class="font-semibold leading-6 text-primary-600 hover:text-primary-600">Registrati</a>
            </p>
        </div>
    </div>
</template>
<script setup lang="ts">
import LoginRequest from "@/types/requests/LoginRequest.ts";
import {useStore} from 'vuex';
import InputLabel from "@/components/InputLabel.vue";
import TextInput from "@/components/TextInput.vue";
import InputError from "@/components/InputError.vue";
import {reactive, UnwrapNestedRefs} from "vue";
import router from "@/routes/Router.ts";

const store = useStore()

interface Form {
    password: string;
    password_confirmation: string;
    email: string;
    errors: Errors
}

interface Errors {
    password_confirmation: string
}

const form: UnwrapNestedRefs<Form> = reactive({
    email: '',
    password: '',
    password_confirmation: '',
    errors: reactive({
        password_confirmation: ''
    })
})

const register = (request: LoginRequest) => store.dispatch('register', {
    form: request,
    then: (response) => {
        router.push('/login')
    },
    catch: (reason) => {
        if(reason.response.status == 409) {
            form.errors.password_confirmation = "Email già registrata"
        }
    }

})

const submit = () => {
    if (form.password !== form.password_confirmation) {
        form.errors.password_confirmation = "Le password non coincidono"
        return
    }
    register({
        email: form.email,
        password: form.password
    })
}

</script>

<template>
    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Registra il tuo account</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form class="space-y-6" @submit.prevent="submit">

                <div class="space-y-1">
                    <InputLabel for="email" value="Email address"/>
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
                                autocomplete="new-password"
                        />
                    </div>
                </div>

                <div class="space-y-1">
                    <InputLabel for="password_confirmation" value="Conferma password"/>
                    <div class="mt-1">
                        <TextInput
                                id="password_confirmation"
                                name="password_confirmation"
                                v-model="form.password_confirmation"
                                type="password"
                                required
                                autocomplete="new-password"
                        />
                    </div>
                    <InputError class="mt-2" :message="form.errors.password_confirmation"/>
                </div>

                <div>
                    <button type="submit"
                            class="flex w-full justify-center rounded-md bg-primary-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-primary-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-primary-600">
                        Registrati
                    </button>
                </div>
            </form>
        </div>
    </div>

</template>
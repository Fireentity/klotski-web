<script setup lang="ts">
import LoginRequest from "@/types/models/LoginRequest.ts";
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
    then: () => {
        router.push({path: '/game'})
    },
    catch: (reason) => {
        if(reason.response.status == 409) {
            form.errors.password_confirmation = "Email already registered"
        }
    }

})

const submit = () => {
    if (form.password !== form.password_confirmation) {
        form.errors.password_confirmation = "Passwords do not match"
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
            <img class="mx-auto h-10 w-auto" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
                 alt="Your Company">
            <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Sign in to your
                account</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form class="space-y-6" @submit.prevent="submit">
                <div>
                    <label for="email" class="block text-sm font-medium leading-6 text-gray-900">Email address</label>
                    <div class="mt-2">
                        <input id="email" name="email" type="email" v-model="form.email" autocomplete="email"
                               required
                               class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
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
                            class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                        Register
                    </button>
                </div>
            </form>
        </div>
    </div>

</template>
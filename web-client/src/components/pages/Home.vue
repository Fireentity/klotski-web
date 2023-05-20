<script setup lang="ts">
import {ChevronRightIcon, StarIcon} from '@heroicons/vue/20/solid'

interface FooterElement {
    name: string,
    href: string,
    icon?: string
}

const stats = [
    {label: 'Founded', value: '2021'},
    {label: 'Employees', value: '5'},
    {label: 'Beta Users', value: '521'},
    {label: 'Raised', value: '$25M'},
]
const logos = [
    {name: 'Transistor', url: 'https://tailwindui.com/img/logos/transistor-logo-gray-400.svg'},
    {name: 'Mirage', url: 'https://tailwindui.com/img/logos/mirage-logo-gray-400.svg'},
    {name: 'Tuple', url: 'https://tailwindui.com/img/logos/tuple-logo-gray-400.svg'},
    {name: 'Laravel', url: 'https://tailwindui.com/img/logos/laravel-logo-gray-400.svg'},
    {name: 'StaticKit', url: 'https://tailwindui.com/img/logos/statickit-logo-gray-400.svg'},
    {name: 'Workcation', url: 'https://tailwindui.com/img/logos/workcation-logo-gray-400.svg'},
]
const footerNavigation: {
    main: FooterElement[]
    social: FooterElement[]
} = {
    main: [],
    social: []
}

</script>

<template>
    <div class="bg-white">
        <main>
            <!-- Hero section -->
            <div class="overflow-hidden pt-8 sm:pt-12 lg:relative lg:py-48">
                <div class="mx-auto max-w-md px-6 sm:max-w-3xl lg:grid lg:max-w-7xl lg:grid-cols-2 lg:gap-24 lg:px-8">
                    <div>
                        <div class="mt-20">
                            <div class="mt-6 sm:max-w-xl">
                                <h1 class="text-4xl font-bold tracking-tight text-gray-900 sm:text-5xl">Klotski</h1>
                                <p class="mt-6 text-xl text-gray-500">Sito web in cui potrete divertirvi con il famoso puzzle Klotski e sfidare i vostri amici</p>
                            </div>
                            <form action="#" class="mt-12 sm:flex sm:w-full sm:max-w-lg">
                                <div class="mt-4 sm:mt-0 sm:ml-3">
                                    <button type="submit"
                                            class="block w-full rounded-md border border-transparent bg-primary-600 px-5 py-3 text-base font-medium text-white shadow hover:bg-primary-900 focus:outline-none focus:ring-2 focus:ring-rose-500 focus:ring-offset-2 sm:px-10">
                                        Gioca ora
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="sm:mx-auto sm:max-w-3xl sm:px-6">
                    <div class="py-12 sm:relative sm:mt-12 sm:py-16 lg:absolute lg:inset-y-0 lg:right-0 lg:w-1/2">
                        <div class="hidden sm:block">
                            <div class="absolute inset-y-0 left-1/2 w-screen rounded-l-3xl bg-gray-50 lg:left-80 lg:right-0 lg:w-full"/>
                            <svg class="absolute top-8 right-1/2 -mr-3 lg:left-0 lg:m-0" width="404" height="392"
                                 fill="none" viewBox="0 0 404 392">
                                <defs>
                                    <pattern id="837c3e70-6c3a-44e6-8854-cc48c737b659" x="0" y="0" width="20"
                                             height="20" patternUnits="userSpaceOnUse">
                                        <rect x="0" y="0" width="4" height="4" class="text-gray-200"
                                              fill="currentColor"/>
                                    </pattern>
                                </defs>
                                <rect width="404" height="392" fill="url(#837c3e70-6c3a-44e6-8854-cc48c737b659)"/>
                            </svg>
                        </div>
                        <div class="relative -mr-40 pl-6 sm:mx-auto sm:max-w-3xl sm:px-0 lg:h-full lg:max-w-none lg:pl-12">
                            <img class="w-full rounded-md shadow-xl ring-1 ring-black ring-opacity-5 lg:h-full lg:w-auto lg:max-w-none"
                                 src="https://tailwindui.com/img/component-images/task-app-rose.jpg" alt=""/>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Testimonial/stats section -->
            <div class="relative mt-20">
                <div class="lg:mx-auto lg:grid lg:max-w-7xl lg:grid-cols-2 lg:items-start lg:gap-24 lg:px-8">
                    <div class="relative sm:py-16 lg:py-0">
                        <div aria-hidden="true" class="hidden sm:block lg:absolute lg:inset-y-0 lg:right-0 lg:w-screen">
                            <div class="absolute inset-y-0 right-1/2 w-full rounded-r-3xl bg-gray-50 lg:right-72"/>
                            <svg class="absolute top-8 left-1/2 -ml-3 lg:-right-8 lg:left-auto lg:top-12" width="404"
                                 height="392" fill="none" viewBox="0 0 404 392">
                                <defs>
                                    <pattern id="02f20b47-fd69-4224-a62a-4c9de5c763f7" x="0" y="0" width="20"
                                             height="20" patternUnits="userSpaceOnUse">
                                        <rect x="0" y="0" width="4" height="4" class="text-gray-200"
                                              fill="currentColor"/>
                                    </pattern>
                                </defs>
                                <rect width="404" height="392" fill="url(#02f20b47-fd69-4224-a62a-4c9de5c763f7)"/>
                            </svg>
                        </div>
                        <div class="relative mx-auto max-w-md px-6 sm:max-w-3xl lg:max-w-none lg:px-0 lg:py-20">
                            <!-- Testimonial card-->
                            <div class="relative overflow-hidden rounded-2xl pt-64 pb-10 shadow-xl">
                                <img class="absolute inset-0 h-full w-full object-cover"
                                     src="https://images.unsplash.com/photo-1521510895919-46920266ddb3?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&fp-x=0.5&fp-y=0.6&fp-z=3&width=1440&height=1440&sat=-100"
                                     alt=""/>
                                <div class="absolute inset-0 bg-rose-500 mix-blend-multiply"/>
                                <div class="absolute inset-0 bg-gradient-to-t from-rose-600 via-rose-600 opacity-90"/>
                                <div class="relative px-8">
                                    <div>
                                        <img class="h-12"
                                             src="https://tailwindui.com/img/logos/workcation.svg?color=white"
                                             alt="Workcation"/>
                                    </div>
                                    <blockquote class="mt-8">
                                        <div class="relative text-lg font-medium text-white md:flex-grow">
                                            <svg class="absolute top-0 left-0 h-8 w-8 -translate-x-3 -translate-y-2 transform text-rose-400"
                                                 fill="currentColor" viewBox="0 0 32 32" aria-hidden="true">
                                                <path d="M9.352 4C4.456 7.456 1 13.12 1 19.36c0 5.088 3.072 8.064 6.624 8.064 3.36 0 5.856-2.688 5.856-5.856 0-3.168-2.208-5.472-5.088-5.472-.576 0-1.344.096-1.536.192.48-3.264 3.552-7.104 6.624-9.024L9.352 4zm16.512 0c-4.8 3.456-8.256 9.12-8.256 15.36 0 5.088 3.072 8.064 6.624 8.064 3.264 0 5.856-2.688 5.856-5.856 0-3.168-2.304-5.472-5.184-5.472-.576 0-1.248.096-1.44.192.48-3.264 3.456-7.104 6.528-9.024L25.864 4z"/>
                                            </svg>
                                            <p class="relative">Tincidunt integer commodo, cursus etiam aliquam neque,
                                                et. Consectetur pretium in volutpat, diam. Montes, magna cursus nulla
                                                feugiat dignissim id lobortis amet.</p>
                                        </div>

                                        <footer class="mt-4">
                                            <p class="text-base font-semibold text-rose-200">Sarah Williams, CEO at
                                                Workcation</p>
                                        </footer>
                                    </blockquote>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="relative mx-auto max-w-md px-6 sm:max-w-3xl lg:px-0">
                        <!-- Content area -->
                        <div class="pt-12 sm:pt-16 lg:pt-20">
                            <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Di cosa si tratta</h2>
                            <div class="mt-6 space-y-6 text-gray-500">
                                <p class="text-lg">Klotski è costituito da una griglia  di dimensioni
                                    5x4 dove alcune caselle sono occupate da blocchi di forme e dimensioni diverse. Questi possono essere spostati in
                                    verticale o in orizzontale, ma non sollevati. L'obiettivo è spostare i blocchi in modo da
                                    liberare quello rosso portandolo alla fine del tabellone. Il gioco è un ottimo modo per
                                    esercitare la logica e la risoluzione dei problemi, poiché richiede la pianificazione strategica dei movimenti
                                    per raggiungere l'obiettivo finale.</p>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </main>

        <!-- Footer section -->
        <footer class="mt-24 bg-gray-900 sm:mt-12">
            <div class="mx-auto max-w-md overflow-hidden py-12 px-6 sm:max-w-3xl lg:max-w-7xl lg:px-8">
                <nav class="-mx-5 -my-2 flex flex-wrap justify-center" aria-label="Footer">
                    <div v-for="item in footerNavigation.main" :key="item.name" class="px-5 py-2">
                        <a :href="item.href" class="text-base text-gray-400 hover:text-gray-300">{{ item.name }}</a>
                    </div>
                </nav>
                <div class="mt-8 flex justify-center Cigaia Davidspace-x-6">
                    <a v-for="item in footerNavigation.social" :key="item.name" :href="item.href"
                       class="text-gray-400 hover:text-gray-300">
                        <span class="sr-only">{{ item.name }}</span>
                        <component :is="item.icon?.toString()" class="h-6 w-6" aria-hidden="true"/>
                    </a>
                </div>
                <!-- TODO: controllare il nome di Cigaia -->
                <p class="mt-8 text-center text-base text-gray-400">Made by: Bottari Alberto, Cigaia David, Croce Lorenzo, Pavanetto Marco</p>
            </div>
        </footer>
    </div>
</template>
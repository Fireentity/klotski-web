<script setup lang="ts">
import Card from "@/components/Card.vue";
import GameInfo from "@/components/GameInfo.vue";
import store from "@/store/Store.ts";
import RectangularTileGridVisitor from "@/types/visitors/RectangularTileGridVisitor.ts";
import Tile from "@/types/models/Tile.ts";
import Result from "@/types/visitors/Result.ts";
import WinningTileGridVisitor from "@/types/visitors/WinningTileGridVisitor.ts";
import Game from "@/types/models/Game.ts";
import {ArrowPathIcon, ArrowUturnLeftIcon, LightBulbIcon} from "@heroicons/vue/24/outline";
import UndoRequest from "@/types/requests/UndoRequest.ts";
import SolveRequest from "@/types/requests/SolveRequest.ts";
import Dropdown from "@/components/Dropdown.vue";
import StartConfigurationOption from "@/types/models/StartConfigurationOption.ts";
import Notification from "@/components/Notification.vue";
import {ref} from "vue";
import ChangeGameConfigurationRequest from "@/types/requests/ChangeGameConfigurationRequest.ts";

const currentGame: Game = store.getters['getCurrentGame']
const games = store.getters['getGames'];
const nextBestMoveNotification = ref();
const undoNotification = ref();
const changeConfigurationNotification = ref()

const createGame = () => {
    store.dispatch('createGame')
}

store.dispatch('getGameInfo', (response) => {
    store.dispatch('getLastUnfinished', (response) => {
        if (response.data.length === 0) {
            createGame();
        }
    })
});
store.dispatch('getStartConfiguration');

const result = new Result()
const visitors = [new RectangularTileGridVisitor(result), new WinningTileGridVisitor(result)]
const visit = (tile: Tile) => {
    visitors.forEach(visitor => {
        tile.accept(visitor)
    })
    return result;
}

const reset = () => {
    store.dispatch('reset', store.getters['getCurrentGame'].id)
}

const undo = () => {
    store.dispatch('undo', {
        undoRequest: new UndoRequest(store.getters['getCurrentGame'].id,
            store.getters['getCurrentGame'].board.tiles),
        catch: (error) => {
            if (error.response.status === 409) {
                undoNotification.value.open()
            }
        }
    })
}

const nextBestMove = () => {
    store.dispatch('nextBestMove', {
        solveRequest: new SolveRequest(store.getters['getCurrentGame'].board.tiles),
        catch: (error) => {
            console.log(error)
            if (error.response.status === 404) {
                nextBestMoveNotification.value.open()
            }
        }
    });
}

const changeConfiguration = (option) => {
    store.dispatch('changeGameConfiguration', {
        changeRequest: new ChangeGameConfigurationRequest(store.getters['getCurrentGame'].id, option.id),
        catch: (error) => {
            if (error.response.status === 409) {
                changeConfigurationNotification.value.open()
            }
        }
    })
}

// 30 -> 2
// 23 -> 1
// 32 -> 3
// 44 -> 4
const options = [
    new StartConfigurationOption("semplice", 0),
    new StartConfigurationOption("media", 1),
    new StartConfigurationOption("difficile", 2),
    new StartConfigurationOption("impossibile", 3)
]

</script>

<template>
  <!-- Global notification live region, render this permanently at the end of the document -->
    <div aria-live="assertive" class="pointer-events-none fixed inset-0 flex items-end px-4 py-6 sm:items-start sm:p-6">
        <div class="flex w-full flex-col items-center space-y-4 sm:items-end">
            <Notification ref="changeConfigurationNotification" title="Errore"
                          message="Impossibile cambiare la configurazione iniziale quando la partita è già iniziata"/>
            <Notification ref="nextBestMoveNotification" title="Errore"
                          message="Impossibile calcolare la prossima mossa migliore"/>
            <Notification ref="undoNotification" title="Errore"
                          message="Impossibile tornare ancora indientro con le mosse"/>
        </div>
    </div>
    <div class="min-h-full">
        <main class="py-10">
            <!-- Page header -->
            <div class="mx-auto px-4 sm:px-6 md:flex md:items-center md:justify-between md:space-x-5 lg:max-w-7xl lg:px-8">
                <div class="grid lg:grid-cols-3 grid-cols-1 w-full gap-4 items-center justify-items-center">
                    <Card class="w-full col-span-1">
                        <div class="grid grid-cols-2 gap-4 content-center">
                            <div>
                                <h2 id="applicant-information-title"
                                    class="text-lg font-medium leading-6 text-gray-900">Partita corrente</h2>
                                <p class="mt-1 max-w-2xl text-sm text-gray-500">Mosse: {{ currentGame.moves }}</p>
                            </div>
                            <div class="flex flex-row mx-3 max-w-xxs justify-between">
                                <ArrowPathIcon @click="reset" class="w-7 my-auto sm:mx-1"/>
                                <ArrowUturnLeftIcon @click="undo" class="w-7 my-auto sm:mx-1"/>
                                <LightBulbIcon @click="nextBestMove" class="w-7 my-auto sm:mx-1"/>
                            </div>
                        </div>
                    </Card>
                    <Dropdown header="Scegli la configurazione: " :options="options"
                              :onClick="changeConfiguration"></Dropdown>
                </div>
            </div>

            <div class="mx-auto mt-8 grid max-w-3xl grid-cols-1 gap-6 sm:px-6 lg:max-w-7xl lg:grid-flow-col-dense lg:grid-cols-3">

                <div class="space-y-6 lg:col-span-2 lg:col-start-1">
                    <!-- Description list-->
                    <section aria-labelledby="applicant-information-title">
                        <Card>
                            <div class="flex justify-center w-full h-full">
                                <div class="col-span-1 w-full flex justify-center h-full">
                                    <div :class="`grid grid-cols-${currentGame.board.boardWidth} grid-rows-${currentGame.board.boardHeight} gap-4 w-full aspect-4/5 h-full`">
                                        <component v-for="tile in currentGame.board.tiles" :is="visit(tile).component"
                                                   :tile="visit(tile).tile" :tiles="currentGame.board.tiles"/>
                                        <div class="bg-gradient-to-t self-end opacity-50 rounded-t-lg from-rose-600 from-2% to-100% col-start-2 col-span-2 row-start-5 col-span-1 -mb-6 h-6">

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </Card>
                    </section>
                </div>

                <section v-if="games.length != 0" aria-labelledby="timeline-title" class="lg:col-span-1 lg:col-start-3">
                    <div class="shadow sm:rounded-lg">
                        <div class="bg-white px-4 py-5 sm:rounded-t-lg sm:px-6">
                            <h2 id="timeline-title" class="text-lg font-medium text-gray-900">Partite</h2>

                            <!-- Activity Feed -->
                            <div class="mt-6 flow-root" v-if="games.length != 0">
                                <ul role="list" class="-mb-8">
                                    <li v-for="(game, itemIndex) in games" :key="itemIndex">
                                        <GameInfo :game="game" :is-last="itemIndex === games.length-1"></GameInfo>
                                    </li>
                                </ul>
                            </div>

                        </div>
                        <button type="button" @click="createGame"
                                class="w-full inline-flex items-center justify-center rounded-b-md border-t border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 focus:ring-offset-gray-100">
                            Nuova partita
                        </button>
                    </div>
                </section>
            </div>
        </main>
    </div>
</template>


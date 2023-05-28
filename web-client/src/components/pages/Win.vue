<script setup lang="ts">
// Array di colori per i coriandoli
import router from "@/routes/Router.js";

const colors = ['#ff6384', '#36a2eb', '#ffce56', '#4bc0c0', '#9966ff'];

// Funzione per creare un coriandolo
function createConfetti() {
    const container = document.getElementById('container');
    const confetti = document.createElement('div');
    confetti.className = 'confetti';
    confetti.style.left = `${Math.random() * 100}vw`;
    confetti.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
    container.appendChild(confetti);

    // Rimuove il coriandolo dopo un certo periodo di tempo
    setTimeout(() => {
        container.removeChild(confetti);
    }, 3000);
    // Rimuove il coriandolo dopo un certo periodo di tempo
}

// Genera i coriandoli
function generateConfetti() {
    setInterval(() => {
        createConfetti();
    }, 50);
}

// Richiama la funzione per generare i coriandoli
generateConfetti();

const redirect = () => {
    router.push('/game')
}
</script>

<template>
    <div @click="redirect" id="container" class="h-full width-full">
        <div @click="redirect" class="win-text grid content-center pb-20 text-3xl absolute w-full h-full text-center">
            <p @click="redirect">HAI VINTO!</p>
            <p @click="redirect" class="text-lg"><strong>Clicca ovunque per continuare</strong></p>
        </div>

        <div @click="redirect" class="confetti"></div>
    </div>
</template>

<style>

.confetti {
    position: absolute;
    width: 10px;
    height: 10px;
    background-color: transparent;
    border-radius: 50%;
    pointer-events: none;
    animation: confettiAnimation 3s infinite;
}

@keyframes confettiAnimation {
    0% {
        transform: translateY(0);
    }
    100% {
        transform: translateY(100vh);
    }
}
</style>
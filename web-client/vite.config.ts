import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'

export default ({mode}) => {
    process.env = {...process.env, ...loadEnv(mode, process.cwd())};
    return defineConfig({


        plugins: [vue({
            template: {
                transformAssetUrls: {
                    base: null,
                    includeAbsolute: false,
                },
            },
        })],
        server: {
            host: '0.0.0.0',
            port: parseInt(process.env.VITE_PORT),
            hmr: {
                host: process.env.VITE_EXTERNAL_HOST
            },
            watch: {
                usePolling: true,
            }
        },
        resolve: {
            alias: {
                '@': '/src',
                '~': '/resources'
            }
        }
    })
}

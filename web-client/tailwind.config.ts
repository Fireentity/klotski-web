const defaultTheme = require('tailwindcss/defaultTheme');

module.exports = {
    purge: {
        content: [
            "./index.html",
            './src/**/*.vue',
            './src/**/*.ts',
            './src/**/*.js',
            './src/**/*.css'
        ],
        options: {
            safelist: ['col-start-1', 'col-start-2', 'col-start-3', 'col-start-4',
                'row-start-1', 'row-start-2', 'row-start-3', 'row-start-4', 'row-start-5',
                'col-span-1', 'col-span-2', 'col-span-3', 'col-span-4',
                'row-span-1', 'row-span-2', 'row-span-3', 'row-span-4',
                'grid-cols-1', 'grid-cols-2', 'grid-cols-3', 'grid-cols-4', 'grid-cols-4', 'grid-cols-5',
                'grid-rows-1', 'grid-rows-2', 'grid-rows-3', 'grid-rows-4', 'grid-rows-4', 'grid-rows-5',
                'aspect-square'
            ],
        }
    },
    plugins: [
        require('@tailwindcss/aspect-ratio'),
        require('@tailwindcss/typography'),
        require('@tailwindcss/forms'),
    ],
    theme: {
        extend: {
            maxWidth: {
                'xxs': '12rem',
            },
            fontSize: {
                '3xl': '80px'
            },
            colors: {
                tile_orange: '#FFDBA4',
                tile_red: '#FD8A8A',
                primary: {
                    '300': '#CBDBF6',
                    '600': '#387BC9',
                    '900': '#1D3464',
                },
            },
            aspectRatio: {
                '4/5': '4 / 5',
            },
        },
    },
};

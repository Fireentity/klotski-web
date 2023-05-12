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
            colors: {
                tile_orange: '#FFDBA4',
                tile_red: '#FD8A8A'
            },
            aspectRatio: {
                '4/5': '4 / 5',
            },
        },
    },
};

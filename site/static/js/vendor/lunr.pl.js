/**
 * export the module via AMD, CommonJS or as a browser global
 * Export code from https://github.com/umdjs/umd/blob/master/returnExports.js
 */
;(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(factory)
    } else if (typeof exports === 'object') {
        /**
         * Node. Does not work with strict CommonJS, but
         * only CommonJS-like environments that support module.exports,
         * like Node.
         */
        module.exports = factory()
    } else {
        // Browser globals (root is window)
        factory()(root.lunr);
    }
}(this, function () {
    /**
     * Just return a value to define the module export.
     * This example returns an object, but the module
     * can return a function as the exported value.
     */
    return function(lunr) {
        /* throw error if lunr is not yet included */
        if ('undefined' === typeof lunr) {
            throw new Error('Lunr is not present. Please include / require Lunr before this script.');
        }

        /* throw error if lunr stemmer support is not yet included */
        if ('undefined' === typeof lunr.stemmerSupport) {
            throw new Error('Lunr stemmer support is not present. Please include / require Lunr stemmer support before this script.');
        }{{consoleWarning}}

        /* register specific locale function */
        lunr.pl = function () {
            this.pipeline.reset();
            this.pipeline.add(
                lunr.pl.trimmer,
                lunr.pl.stopWordFilter,
                lunr.pl.stemmer
            );

            // for lunr version 2
            // this is necessary so that every searched word is also stemmed before
            // in lunr <= 1 this is not needed, as it is done using the normal pipeline
            if (this.searchPipeline) {
                this.searchPipeline.reset();
                this.searchPipeline.add(lunr.pl.stemmer)
            }
        };

        /* lunr trimmer function */
        lunr.pl.wordCharacters = "A-Za-z\u00F3\u00D3\u0104-\u0107\u0118\u0119\u0141-\u0144\u015A\u015B\u0179-\u017C";
        lunr.pl.trimmer = lunr.trimmerSupport.generateTrimmer(lunr.pl.wordCharacters);

        lunr.Pipeline.registerFunction(lunr.pl.trimmer, 'trimmer-pl');

        /* lunr stemmer function */
        lunr.pl.stemmer = (function() {
            /* create the wrapped stemmer object */
            var Among = lunr.stemmerSupport.Among,
                SnowballProgram = lunr.stemmerSupport.SnowballProgram,
                st = new function PolishStemmer() {
                    var sbp = new SnowballProgram();
                    this.setCurrent = function(word) {
                        sbp.setCurrent(word);
                    };

                    this.getCurrent = function() {
                        return sbp.getCurrent();
                    };

                    this.stem = function() {

                    };
                };

            /* and return a function that stems a word for the current locale */
            return function(token) {
                // for lunr version 2
                if (typeof token.update === "function") {
                    return token.update(function (word) {
                        st.setCurrent(word);
                        st.stem();
                        return st.getCurrent();
                    })
                } else { // for lunr version <= 1
                    st.setCurrent(token);
                    st.stem();
                    return st.getCurrent();
                }
            }
        })();

        lunr.Pipeline.registerFunction(lunr.pl.stemmer, 'stemmer-pl');

        lunr.pl.stopWordFilter = lunr.generateStopWordFilter('a aby ach acz aczkolwiek aj albo ale alez ależ ani az aż bardziej bardzo beda bedzie bez deda będą bede będę będzie bo bowiem by byc być byl byla byli bylo byly był była było były bynajmniej cala cali caly cała cały ci cie ciebie cię co cokolwiek cos coś czasami czasem czemu czy czyli daleko dla dlaczego dlatego do dobrze dokad dokąd dosc dość duzo dużo dwa dwaj dwie dwoje dzis dzisiaj dziś gdy gdyby gdyz gdyż gdzie gdziekolwiek gdzies gdzieś go i ich ile im inna inne inny innych iz iż ja jak jakas jakaś jakby jaki jakichs jakichś jakie jakis jakiś jakiz jakiż jakkolwiek jako jakos jakoś ją je jeden jedna jednak jednakze jednakże jedno jego jej jemu jesli jest jestem jeszcze jeśli jezeli jeżeli juz już kazdy każdy kiedy kilka kims kimś kto ktokolwiek ktora ktore ktorego ktorej ktory ktorych ktorym ktorzy ktos ktoś która które którego której który których którym którzy ku lat lecz lub ma mają mało mam mi miedzy między mimo mna mną mnie moga mogą moi moim moj moja moje moze mozliwe mozna może możliwe można mój mu musi my na nad nam nami nas nasi nasz nasza nasze naszego naszych natomiast natychmiast nawet nia nią nic nich nie niech niego niej niemu nigdy nim nimi niz niż no o obok od około on ona one oni ono oraz oto owszem pan pana pani po pod podczas pomimo ponad poniewaz ponieważ powinien powinna powinni powinno poza prawie przeciez przecież przed przede przedtem przez przy roku rowniez również sam sama są sie się skad skąd soba sobą sobie sposob sposób swoje ta tak taka taki takie takze także tam te tego tej ten teraz też to toba tobą tobie totez toteż totobą trzeba tu tutaj twoi twoim twoj twoja twoje twój twym ty tych tylko tym u w wam wami was wasz wasza wasze we według wiele wielu więc więcej wlasnie właśnie wszyscy wszystkich wszystkie wszystkim wszystko wtedy wy z za zaden zadna zadne zadnych zapewne zawsze ze zeby zeznowu zł znow znowu znów zostal został żaden żadna żadne żadnych że żeby'.split(' '));

        lunr.Pipeline.registerFunction(lunr.pl.stopWordFilter, 'stopWordFilter-pl');
    };
}))

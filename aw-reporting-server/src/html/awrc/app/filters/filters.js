(function () {

	awrcApp.filter("formatccid", function () {
        return function (str) {
            if (typeof str != 'string' && typeof str != 'number') return str;
            return format_ccid(str);
        };
    });

})();

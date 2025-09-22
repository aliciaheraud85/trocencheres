function changeLocale() {
    document.querySelector("#locales").addEventListener('selectionchange', function() {
        const selectedOption = this.value;
        if (selectedOption !== '') {
            window.location.redirect(window.location.pathname + '?lang=' + selectedOption);
        }
    })
}

document.querySelector("#locales").addEventListener('change', function() {
    const selectedOption = this.value;
    console.log(selectedOption)
    if (selectedOption !== '') {
        window.location.replace(window.location.pathname + '?lang=' + selectedOption);
    }
})

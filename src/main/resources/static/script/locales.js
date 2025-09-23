document.querySelector("#locales").addEventListener('change', function() {
    const selectedOption = this.value;
    if (selectedOption !== '') {
        let searchParams = new URLSearchParams(window.location.search);
        searchParams.set("lang", selectedOption);
        window.location.search = searchParams.toString();
    }
})

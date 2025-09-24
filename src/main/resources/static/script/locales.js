const localeSelect = document.querySelector("#locales");

document.addEventListener('DOMContentLoaded', function() {
    if (!localeSelect) return;
    const urlSearchParams = new URLSearchParams(window.location.search);
    localeSelect.value = urlSearchParams.get("lang");
    localeSelect.addEventListener('change', function(event) {
        const selectedOption = event.target.value;
        if (!selectedOption) return;
        try {
            const url = new URL(window.location);
            url.searchParams.set('lang', selectedOption);
            window.history.pushState({}, '', url.toString());
            setTimeout(() => {
                window.location.reload();
            }, 100);
        } catch (error) {
            console.error("Failed to update URL:", error);
        }
    });
});

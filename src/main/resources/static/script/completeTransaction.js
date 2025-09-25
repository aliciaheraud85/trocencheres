document.addEventListener('DOMContentLoaded', function () {
    const completeButton = document.getElementById("getCredits");

    if (completeButton != null) {
        const modal = document.getElementById("modal");
        const modalBackground = document.querySelector(".cancel");
        const modalCloseButton = document.querySelector(".close");
        modalCloseButton.addEventListener("click", () => {
            modal.style.visibility = "hidden";
        });
        modalBackground.addEventListener("click", () => {
            modal.style.visibility = "hidden";
        });
        document.addEventListener("click", () => async function getData() {
            let searchParams = new URLSearchParams(window.location.search);
            const url = "/auction/complete?id=" + searchParams.get("id");
            try {
                const response = await fetch(url);
                if (!response.ok) {
                    document.querySelectorAll(".transact-error").forEach(e => e.style.setAttribute("visibility", "visible"));
                }
                else {
                    document.querySelectorAll(".transact-success").forEach(e => e.style.setAttribute("visibility", "visible"));
                }
                modal.setAttribute("visibility", "visible")
            } catch (error) {
                console.error(error.message);
            }
        })
    }
})

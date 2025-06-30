document.addEventListener('DOMContentLoaded', function () {
    const menuToggle = document.getElementById('menu-toggle');
    const menu = document.getElementById('menu');
    if (menuToggle && menu) {
        menuToggle.addEventListener('click', function () {
            menu.classList.toggle('hidden');
        });
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const draggables = document.querySelectorAll("[draggable='true']");
    const containers = document.querySelectorAll(".droppable-container");
    draggables.forEach((draggable) => {
        draggable.addEventListener("dragstart", (e) => {
            e.dataTransfer.setData("text/plain", e.target.id);
        });
    });
    containers.forEach((container) => {
        container.addEventListener("drver", (e) => {
            e.preventDefault();
        });
        container.addEventListener("drop", (e) => {
            e.preventDefault();
            const id = e.dataTransfer.getData("text/plain");
            const draggedElement = document.getElementById(id);
            container.appendChild(draggedElement);
        });
    });
});
document.addEventListener('DOMContentLoaded', function () {
    const toasts = document.querySelectorAll('.toast-message');
    toasts.forEach(toast => {
        setTimeout(() => {
            toast.classList.add('opacity-0', 'transition-opacity', 'duration-500');
            setTimeout(() => {
                toast.remove();
                if (toast.parentNode) {
                    toast.parentNode.removeChild(toast);
                }
            }, 500);
        }, 4000);
    });
});
function openModalU(id) {
    document.getElementById(`modal-${id}`).classList.remove('hidden');
}

function closeModalU(id) {
    document.getElementById(`modal-${id}`).classList.add('hidden');
}

function toggleSubmenu(id) {
    const submenu = document.getElementById(id);
    const icon = document.getElementById(id + '-icon');
    submenu.classList.toggle('hidden');
    icon.classList.toggle('rotate-180');
}

document.addEventListener('DOMContentLoaded', () => {
    const button = document.getElementById('userMenuButton');
    const dropdown = document.getElementById('userDropdown');

    if (button && dropdown) {
        button.addEventListener('click', (e) => {
            e.stopPropagation();
            dropdown.classList.toggle('opacity-0');
            dropdown.classList.toggle('invisible');
        });

        document.addEventListener('click', (e) => {
            if (!button.contains(e.target) && !dropdown.contains(e.target)) {
                dropdown.classList.add('opacity-0');
                dropdown.classList.add('invisible');
            }
        });
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const mensaje = localStorage.getItem("mensajeExito");
    if (mensaje) {
        activarToasts();
    }
});

//script for modal in users/fragments
function openModal() {
    const modal = document.getElementById('editUserModal');
    modal.classList.remove('hidden');
    modal.classList.add('flex');
}

function closeModal() {
    const modal = document.getElementById('editUserModal');
    modal.classList.add('hidden');
    modal.classList.remove('flex');
}


document.addEventListener('DOMContentLoaded', () => {
    const button = document.getElementById('mobile-menu-button');
    const menu = document.getElementById('mobile-menu');
    if (button && menu) {
        button.addEventListener('click', function () {
            menu.classList.toggle('hidden');
        });
    }
});
function openCreateModal() {
    document.getElementById('createAppointmentModal').classList.remove('hidden');
    document.body.classList.add('overflow-hidden');
}

function closeCreateModal() {
    document.getElementById('createAppointmentModal').classList.add('hidden');
    document.body.classList.remove('overflow-hidden');
}



// Script for pop up, basically alerts
function fadeInOut(id) {
    const alert = document.getElementById(id);
    if (alert) {
        setTimeout(() => alert.classList.remove("opacity-0"), 100);
        setTimeout(() => {
            alert.classList.add("opacity-0");
            setTimeout(() => alert.style.display = 'none', 500);
        }, 3000);
    }
}


fadeInOut("successAlert");
fadeInOut("errorAlert");
fadeInOut("deleteAlert");

function closeAlert()
{
    const alert = document.getElementById("successAlert");
    if (alert) {
        alert.classList.add("opacity-0");
        setTimeout(() => alert.style.display = "none", 500);
    }
}


function closeErrorAlert() {
    const alert = document.getElementById("errorAlert");
    if (alert) {
        alert.classList.add("opacity-0");
        setTimeout(() => alert.style.display = "none", 500);
    }
}


function closeDeleteAlert() {
    const alert = document.getElementById("deleteAlert");
    if (alert) {
        alert.classList.add("opacity-0");
        setTimeout(() => alert.style.display = "none", 500);
    }
}


function buscarUsuarioPorIdentificacion() {
    const identificacion = document.querySelector('input[name="identification"]').value;
    fetch(`/api/users/search?identification=${identificacion}`)
            .then(response => {
                if (!response.ok)
                    throw new Error("Usuario no encontrado");
                return response.json();
            })
            .then(data => {
                document.querySelector('input[name="fullName"]').value = data.fullName;
            })
            .catch(error => {
                console.log("No se encontró el usuario:", error);
                document.querySelector('input[name="fullName"]').value = "";
            });
}


function toggleMenu(button) {
    // Cierra todos los menús abiertos
    document.querySelectorAll('.menuRO').forEach(menu => {
        if (menu !== button.nextElementSibling) {
            menu.classList.add('hidden');
        }
    });

    // Alterna el menú actual
    const menu = button.nextElementSibling;
    menu.classList.toggle('hidden');
}


// Cierra el menú si se hace clic fuera
document.addEventListener('click', function (event) {
    const isMenuButton = event.target.closest('button');
    const isMenu = event.target.closest('.menuRO');
    if (!isMenuButton && !isMenu) {
        document.querySelectorAll('.menuRO').forEach(menu => menu.classList.add('hidden'));
    }
});

function activarToasts()
{
    const toasts = document.querySelectorAll('.toast-message');
    toasts.forEach(toast => {
        setTimeout(() => {
            toast.classList.add('opacity-0', 'transition-opacity', 'duration-500');
            setTimeout(() => {
                toast.remove();
            }, 500);
        }, 4000);
    });
}

document.addEventListener("DOMContentLoaded", () => {
    const mensaje = localStorage.getItem("mensajeExito");
    if (mensaje) {
        const toast = document.createElement("div");
        toast.className = "toast-message bg-green-500 text-white px-4 py-3 rounded shadow-lg fixed top-5 right-5 z-50 animate-fade-in-out";
        toast.innerText = mensaje;
        document.body.appendChild(toast);

        setTimeout(() => {
            toast.remove();
        }, 3000);

        localStorage.removeItem("mensajeExito");
    }
});




function openEditModal(id) {
    fetch(`/garage/editAdmin/${id}`)
            .then(response => response.text())
            .then(html => {
                const modalContainer = document.getElementById('modal-content');
                modalContainer.innerHTML = html;
                document.getElementById('editOrderModal').classList.remove('hidden');
                attachAllModalFormListeners();
            })
            .catch(error => {
                console.error('Error al cargar el formulario:', error);
            });
}

function closeEditModal() {
    document.getElementById('editOrderModal').classList.add('hidden');
    document.getElementById('modal-content').innerHTML = '';
}

function handleModalFormSubmit(form) {
    form.addEventListener("submit", function (e) {
        e.preventDefault();
        const formData = new FormData(form);
        const expectsJson = form.dataset.response === "json";

        fetch(form.action, {
            method: "POST",
            headers: {
                "X-Requested-With": "XMLHttpRequest",
                "Accept": expectsJson ? "application/json" : "text/html"
            },
            body: formData
        })
                .then(response => {
                    const contentType = response.headers.get("content-type");
                    if (expectsJson && contentType && contentType.includes("application/json")) {
                        return response.json().then(data => {
                            if (data.redirectUrl) {
                                localStorage.setItem("mensajeExito", data.mensajeExito || "Operación exitosa.");
                                closeEditModal();

                                const toast = document.createElement("div");
                                toast.className = "toast-message bg-green-500 text-white px-4 py-3 rounded shadow-lg fixed top-5 right-5 z-50 animate-fade-in-out";
                                toast.innerText = data.mensajeExito || "Operación exitosa.";
                                document.body.appendChild(toast);

                                setTimeout(() => {
                                    window.location.href = data.redirectUrl;
                                }, 2000);
                            }
                        });
                    } else {
                        return response.text().then(html => {
                            const modalContent = document.getElementById("modal-content");
                            modalContent.innerHTML = html;
                            activarToasts();
                            attachAllModalFormListeners();
                        });
                    }
                })
                .catch(error => {
                    console.error("Error al enviar el formulario:", error);
                    alert("Ocurrió un error al procesar la solicitud.");
                });
    });

    // Si es un formulario de toggle-subtask, interceptar el cambio del checkbox
    if (form.classList.contains("toggle-subtask-form")) {
        const checkbox = form.querySelector("input[type='checkbox']");
        if (checkbox) {
            checkbox.addEventListener("change", function () {
                const formData = new FormData(form);

                fetch(form.action, {
                    method: "POST",
                    headers: {
                        "X-Requested-With": "XMLHttpRequest",
                        "Accept": "text/html"
                    },
                    body: formData
                })
                        .then(response => response.text())
                        .then(html => {
                            const modalContent = document.getElementById("modal-content");
                            modalContent.innerHTML = html;
                            activarToasts();
                            attachAllModalFormListeners();
                        })
                        .catch(error => {
                            console.error("Error al alternar subtarea:", error);
                        });
            });
        }
    }
}



function attachAllModalFormListeners() {
    const modalContent = document.getElementById("modal-content");
    if (!modalContent)
        return;

    const allForms = modalContent.querySelectorAll("form");
    allForms.forEach(form => handleModalFormSubmit(form));
}



function openCreateEmployeeModal() {
    document.getElementById('createEmployeeModal').classList.remove('hidden');
    document.body.classList.add('overflow-hidden');
}

function closeCreateEmployeeModal() {
    document.getElementById('createEmployeeModal').classList.add('hidden');
    document.body.classList.remove('overflow-hidden');
}

function openEditEmployeeModal(id) {
    document.getElementById('editEmployeeModal-' + id).classList.remove('hidden');
    document.body.classList.add('overflow-hidden');
}

function closeEditEmployeeModal(id) {
    document.getElementById('editEmployeeModal-' + id).classList.add('hidden');
    document.body.classList.remove('overflow-hidden');
}

document.addEventListener("click", function (e) {
    const modal = document.getElementById("editOrderModal");
    const content = document.getElementById("modal-content");

    if (modal && content && !modal.classList.contains("hidden") && !content.contains(e.target)) {
        closeEditModal();
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const searchBtn = document.getElementById("searchPlateBtn") || document.getElementById("searchPlateBtnAdmin");

    if (searchBtn) {
        searchBtn.addEventListener("click", function () {
            const plate = document.getElementById("licensePlateSearch").value;
            const isAdmin = searchBtn.id === "searchPlateBtnAdmin";
            const endpoint = isAdmin ? "/garage/admin/search" : "/garage/search";

            const errorElement = document.getElementById("searchErrorAdmin");
            if (errorElement)
                errorElement.classList.add("hidden");

            fetch(`${endpoint}?plate=${plate}`)
                    .then(response => {
                        if (!response.ok)
                            throw new Error("Moto no encontrada");
                        return response.json();
                    })
                    .then(data => {
                        document.querySelector('[name="brand"]').value = data.brand;
                        document.querySelector('[name="modelName"]').value = data.modelName;
                        document.querySelector('[name="year"]').value = data.year;
                        document.querySelector('[name="licensePlate"]').value = data.licensePlate;

                        if (isAdmin) {
                            document.querySelector('[name="brand"]').value = data.brand;
                            document.querySelector('[name="modelName"]').value = data.modelName;
                            document.querySelector('[name="year"]').value = data.year;
                            document.querySelector('[name="licensePlate"]').value = data.licensePlate;
                        }
                    })
                    .catch(error => {
                        if (isAdmin && errorElement)
                            errorElement.classList.remove("hidden");
                        else
                            alert("No se encontró una motocicleta con esa placa.");
                    });
        });
    }

});

    function openDeleteModal(id) {
        document.getElementById('modal-' + id).classList.remove('hidden');
    }

    function closeDeleteModal(id) {
        document.getElementById('modal-' + id).classList.add('hidden');
    }



















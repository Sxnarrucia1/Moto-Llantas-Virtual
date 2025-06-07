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



// script for dropdown user in layout/layoutAdmin
// script for dropdown user in layout/layoutAdmin
document.addEventListener("DOMContentLoaded", function () {
    const mensaje = localStorage.getItem("mensajeExito");
    if (mensaje) {
        mostrarToast(mensaje, "success");
        localStorage.removeItem("mensajeExito");
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


// Script to hidde layout/layoutAdmin
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

document.addEventListener("DOMContentLoaded", function () {
    const mensaje = localStorage.getItem("mensajeExito");
    if (mensaje) {
        const toastContainer = document.getElementById("toastContainer");
        const toast = document.createElement("div");

        toast.className = "toast-message bg-green-500 text-white px-4 py-3 rounded shadow-lg mb-4";
        toast.innerHTML = `<p>${mensaje}</p>`;
        toastContainer.appendChild(toast);

        activarToasts();
        localStorage.removeItem("mensajeExito");
    }
});



function openEditModal(orderId) {
    fetch(`/garage/editAdmin/${orderId}`)
            .then(response => response.text())
            .then(html => {
                document.getElementById('modalContent').innerHTML = html;
                document.getElementById('editOrderModal').classList.remove('hidden');
            })
            .catch(error => {
                console.error('Error al cargar el formulario:', error);
            });
}

function closeEditModal()
{
    document.getElementById('editOrderModal').classList.add('hidden');
    document.getElementById('modalContent').innerHTML = '';
}


document.addEventListener('submit', function (e) {
    if (e.target && e.target.id === 'editOrderForm') {
        e.preventDefault();

        const form = e.target;
        const formData = new FormData(form);

        fetch(form.action, {
            method: 'POST',
            headers: {
                'Accept': 'application/json'
            },
            body: formData
        })
                .then(response => {
                    const contentType = response.headers.get("content-type");
                    if (contentType && contentType.includes("application/json")) {
                        return response.json().then(data => {
                            if (data.redirectUrl) {
                                localStorage.setItem("mensajeExito", data.mensajeExito);
                                closeEditModal();
                                window.location.href = data.redirectUrl;
                            }
                        });
                    } else {
                        return response.text().then(html => {
                            document.getElementById('modalContent').innerHTML = html;
                            activarToasts();
                        });
                    }
                })
                .catch(error => {
                    console.error('Error al enviar el formulario:', error);
                });

    }
});

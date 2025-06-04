    document.addEventListener('DOMContentLoaded', function () {
            const menuToggle = document.getElementById('menu-toggle');
            const menu = document.getElementById('menu');

            menuToggle.addEventListener('click', function () {
                    menu.classList.toggle('hidden');
            });
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

                // Limpia el contenido del mensaje para evitar que se vuelva a renderizar
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
            if (!button.contains(e.target)) {
                dropdown.classList.add('opacity-0');
                dropdown.classList.add('invisible');
            }
        });
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

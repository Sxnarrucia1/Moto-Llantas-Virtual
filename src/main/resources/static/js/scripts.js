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

function openModal(id) {
    document.getElementById(`modal-${id}`).classList.remove('hidden');
}

function closeModal(id) {
    document.getElementById(`modal-${id}`).classList.add('hidden');
}

function toggleSubmenu(id) {
    const submenu = document.getElementById(id);
    const icon = document.getElementById(id + '-icon');
    submenu.classList.toggle('hidden');
    icon.classList.toggle('rotate-180');
}





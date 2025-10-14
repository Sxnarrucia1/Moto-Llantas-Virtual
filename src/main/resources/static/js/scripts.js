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

function initializeProductForm(scope) {
    const addButton = scope.querySelector('#add-product-btn');
    const usageList = scope.querySelector('#product-usage-list');
    const optionsTemplate = scope.querySelector('#product-options-template');

    if (!addButton || !usageList || !optionsTemplate) {
        console.warn('Elementos del formulario de productos no encontrados.');
        return;
    }

    addButton.addEventListener('click', () => {
        const index = usageList.children.length;
        const productOptions = optionsTemplate.innerHTML;

        const row = document.createElement('div');
        row.className = 'product-row grid grid-cols-4 gap-4 mb-2 items-center';
        row.innerHTML = `
            <select name="usedProducts[${index}].product.id" class="form-select rounded-md border-gray-300">
                ${productOptions}
            </select>
            <input type="number" min="1" name="usedProducts[${index}].quantityUsed" class="form-input rounded-md border-gray-300" placeholder="Cantidad usada" />
            <input type="text" name="usedProducts[${index}].notes" class="form-input rounded-md border-gray-300" placeholder="Notas (opcional)" />
            <button type="button" class="remove-product-btn text-red-600 hover:text-red-800 font-bold text-xl leading-none">&times;</button>
        `;
        usageList.appendChild(row);
    });

    usageList.addEventListener('click', (event) => {
        if (event.target.classList.contains('remove-product-btn')) {
            event.preventDefault();
            const row = event.target.closest('.product-row');
            if (row)
                row.remove();
        }
    });
}

function attachAllModalFormListeners() {
    const modalContent = document.getElementById("modal-content");
    if (!modalContent)
        return;

    const allForms = modalContent.querySelectorAll("form");
    allForms.forEach(form => handleModalFormSubmit(form));

    initializeProductForm(modalContent);
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

document.addEventListener("DOMContentLoaded", () => {
    const stars = document.querySelectorAll("#star-container .star");
    const ratingInput = document.getElementById("rating");

    if (!ratingInput) {
        console.error("Elemento con id='rating' no encontrado.");
        return;
    }

    let selectedRating = parseInt(ratingInput.value) || 0;

    function highlightStars(rating) {
        stars.forEach((star, index) => {
            if (index < rating) {
                star.classList.add("text-yellow-400");
                star.classList.remove("text-gray-300");
            } else {
                star.classList.remove("text-yellow-400");
                star.classList.add("text-gray-300");
            }
        });
    }

    stars.forEach((star, index) => {
        const value = index + 1;

        star.addEventListener("mouseenter", () => {
            highlightStars(value);
        });

        star.addEventListener("mouseleave", () => {
            highlightStars(selectedRating);
        });

        star.addEventListener("click", () => {
            selectedRating = value;
            ratingInput.value = selectedRating;
            highlightStars(selectedRating);
        });
    });

    if (selectedRating > 0) {
        highlightStars(selectedRating);
    }
});


function openIncomeModal(id) {
    const modal = document.getElementById(id);
    modal.classList.remove('hidden');
    modal.classList.add('flex');
}

function closeIncomeModal(id) {
    const modal = document.getElementById(id);
    modal.classList.remove('flex');
    modal.classList.add('hidden');
}

function openIncomeEditModal(id, date, amount, description, category) {
    document.getElementById('edit-id').value = id;
    document.getElementById('edit-date').value = date;
    document.getElementById('edit-amount').value = amount;
    document.getElementById('edit-description').value = description;
    document.getElementById('edit-category').value = category;
    openIncomeModal('editIncomeModal');
}

document.addEventListener('DOMContentLoaded', () => {
    ['createIncomeModal', 'editIncomeModal'].forEach(modalId => {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.addEventListener('click', () => closeIncomeModal(modalId));
        }
    });
});

function handleEditClick(button) {
    const id = button.getAttribute('data-id');
    const date = button.getAttribute('data-date');
    const amount = button.getAttribute('data-amount');
    const description = button.getAttribute('data-description');
    const category = button.getAttribute('data-category');

    openIncomeEditModal(id, date, amount, description, category);
}

function openConfirmModal(id) {
    document.getElementById('modal-' + id).classList.remove('hidden');
}

function closeConfirmModal(id) {
    document.getElementById('modal-' + id).classList.add('hidden');
}

function openExpenseModal(id) {
    const modal = document.getElementById(id);
    modal.classList.remove('hidden');
    modal.classList.add('flex');
}

function closeExpenseModal(id) {
    const modal = document.getElementById(id);
    modal.classList.remove('flex');
    modal.classList.add('hidden');
}

function openExpenseEditModal(id, date, amount, description, category) {
    document.getElementById('edit-expense-id').value = id;
    document.getElementById('edit-expense-date').value = date;
    document.getElementById('edit-expense-amount').value = amount;
    document.getElementById('edit-expense-description').value = description;
    document.getElementById('edit-expense-category').value = category;
    openExpenseModal('editExpenseModal');
}

document.addEventListener('DOMContentLoaded', () => {
    ['createExpenseModal', 'editExpenseModal'].forEach(modalId => {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.addEventListener('click', () => closeExpenseModal(modalId));
        }
    });
});

function handleExpenseEditClick(button) {
    const id = button.getAttribute('data-id');
    const date = button.getAttribute('data-date');
    const amount = button.getAttribute('data-amount');
    const description = button.getAttribute('data-description');
    const category = button.getAttribute('data-category');

    openExpenseEditModal(id, date, amount, description, category);
}

let chart;

document.addEventListener('DOMContentLoaded', function () {
    const ctx = document.getElementById('financialChart').getContext('2d');
    chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Ingreso', 'Egreso', 'Balance'],
            datasets: [{
                    label: 'Resumen Financiero',
                    data: [0, 0, 0],
                    backgroundColor: ['#22c55e', '#ef4444', '#3b82f6']
                }]
        }
    });

    updateFinancialChart();
});


function updateFinancialChart() {
    fetch('/dashboard/api/finance-summary')
            .then(response => response.json())
            .then(data => {
                const {income, expense, balance} = data;

                chart.data.datasets[0].data = [income, expense, balance];
                chart.update();

                document.getElementById('income').textContent = income.toFixed(2);
                document.getElementById('expense').textContent = expense.toFixed(2);
                document.getElementById('balance').textContent = balance.toFixed(2);
            })
            .catch(error => console.error("Error al obtener datos financieros:", error));
}

setInterval(updateFinancialChart, 30000);

document.addEventListener('DOMContentLoaded', function () {
    const container = document.getElementById('reportDataContainer');

    if (!container) {
        console.error("No se encontró el contenedor #reportDataContainer.");
        return;
    }

    const jsonString = container.dataset.json;

    if (!jsonString) {
        console.error("No se encontró el atributo data-json.");
        return;
    }

    let reportData;
    try {
        reportData = JSON.parse(jsonString);
    } catch (error) {
        console.error("Error al parsear reportJson:", error);
        return;
    }

    const labels = reportData.summaries.map(s => s.label);
    const incomeData = reportData.summaries.map(s => s.income);
    const expenseData = reportData.summaries.map(s => s.expense);
    const balanceData = reportData.summaries.map(s => s.balance);

    const ctx = document.getElementById('reportChart')?.getContext('2d');
    if (!ctx) {
        console.error("No se encontró el canvas #reportChart.");
        return;
    }

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Ingresos',
                    data: incomeData,
                    borderColor: '#22c55e',
                    fill: false
                },
                {
                    label: 'Egresos',
                    data: expenseData,
                    borderColor: '#ef4444',
                    fill: false
                },
                {
                    label: 'Balance',
                    data: balanceData,
                    borderColor: '#3b82f6',
                    fill: false
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top'
                },
                title: {
                    display: true,
                    text: 'Resumen Financiero por Periodo'
                }
            }
        }
    });
});

// script para los layouts de admin sidebar y el navbar
 const sidebar = document.getElementById('sidebar');
    const overlay = document.getElementById('overlay');
    const toggle = document.getElementById('sidebarToggle');

    toggle.addEventListener('click', () => {
        sidebar.classList.toggle('-translate-x-full');
        overlay.classList.toggle('hidden');
    });

    overlay.addEventListener('click', () => {
        sidebar.classList.add('-translate-x-full');
        overlay.classList.add('hidden');
    });





// script for changeQuantity in productDetails
        function changeQuantity(delta) {
                const input = document.getElementById('quantity');
                const value = parseInt(input.value) + delta;
                if (value > 0) input.value = value;
        }
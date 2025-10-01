// Script para mejorar la funcionalidad del selector de actores
document.addEventListener('DOMContentLoaded', function () {
    // Agregar efectos visuales al seleccionar actores
    const actorCheckboxes = document.querySelectorAll('.actor-checkbox-item input[type="checkbox"]');

    actorCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            const actorInfo = this.parentElement.querySelector('.actor-info');
            if (this.checked) {
                actorInfo.style.backgroundColor = '#e7f3ff';
                actorInfo.style.borderLeft = '3px solid #0d6efd';
            } else {
                actorInfo.style.backgroundColor = '';
                actorInfo.style.borderLeft = '';
            }
        });

        // Aplicar estilos iniciales para actores ya seleccionados
        if (checkbox.checked) {
            const actorInfo = checkbox.parentElement.querySelector('.actor-info');
            actorInfo.style.backgroundColor = '#e7f3ff';
            actorInfo.style.borderLeft = '3px solid #0d6efd';
        }
    });

    // Agregar contador de actores seleccionados
    const actorSelector = document.querySelector('.actor-selector');
    if (actorSelector) {
        const createCounter = () => {
            const counter = document.createElement('div');
            counter.className = 'actor-counter mt-2 text-muted small';
            counter.id = 'actor-counter';
            updateCounter();
            return counter;
        };

        const updateCounter = () => {
            const checkedCount = document.querySelectorAll('.actor-checkbox-item input[type="checkbox"]:checked').length;
            const totalCount = document.querySelectorAll('.actor-checkbox-item input[type="checkbox"]').length;
            let counter = document.getElementById('actor-counter');

            if (!counter) {
                counter = createCounter();
                actorSelector.parentElement.appendChild(counter);
            }

            counter.textContent = `${checkedCount} de ${totalCount} actores seleccionados`;

            if (checkedCount > 0) {
                counter.style.color = '#0d6efd';
                counter.style.fontWeight = '500';
            } else {
                counter.style.color = '#6c757d';
                counter.style.fontWeight = 'normal';
            }
        };

        // Actualizar contador cuando cambie la selección
        actorCheckboxes.forEach(checkbox => {
            checkbox.addEventListener('change', updateCounter);
        });

        // Crear contador inicial
        updateCounter();
    }

    // Mejorar la experiencia de usuario con tooltips para imágenes de actores
    const actorImages = document.querySelectorAll('.actor-avatar, .actor-avatar-large, .actor-form-avatar');
    actorImages.forEach(img => {
        img.addEventListener('error', function () {
            // Si no se puede cargar la imagen, usar un placeholder SVG
            this.src = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIHZpZXdCb3g9IjAgMCA0MCA0MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPGNpcmNsZSBjeD0iMjAiIGN5PSIyMCIgcj0iMjAiIGZpbGw9IiNEQkU0RkYiLz4KPHN2ZyB3aWR0aD0iMjQiIGhlaWdodD0iMjQiIHZpZXdCb3g9IjAgMCAyNCAyNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4PSI4IiB5PSI4Ij4KPHBhdGggZD0iTTEyIDEyQzE0LjIwOTEgMTIgMTYgMTAuMjA5MSAxNiA4QzE2IDUuNzkwODYgMTQuMjA5MSA0IDEyIDRDOS43OTA4NiA0IDggNS43OTA4NiA4IDhDOCAxMC4yMDkxIDkuNzkwODYgMTIgMTIgMTJaIiBmaWxsPSIjOEU5NEZERCI+PC9wYXRoPgo8cGF0aCBkPSJNMTIgMTRDOC42ODYyOSAxNCA2IDE2LjY4NjMgNiAyMEg2LjAxQzYuMDEgMjAuMzMyIDYuMzM4IDIwLjY2IDYuNjcgMjAuNjZIMTcuMzNDMTcuNjYyIDIwLjY2IDE3Ljk5IDIwLjMzMiAxNy45OSAyMEMxNy45OSAxNi42ODYzIDE1LjMxMzcgMTQgMTIgMTRaIiBmaWxsPSIjOEU5NEZERCI+PC9wYXRoPgo8L3N2Zz4KPC9zdmc+';
            this.style.backgroundColor = '#f8f9fa';
        });
    });

    // Agregar búsqueda en tiempo real para el selector de actores
    const actorSelectorContainer = document.querySelector('.actor-selector');
    if (actorSelectorContainer) {
        const searchInput = document.createElement('input');
        searchInput.type = 'text';
        searchInput.className = 'form-control mb-3';
        searchInput.placeholder = 'Buscar actor...';
        searchInput.id = 'actor-search';

        // Insertar el input de búsqueda antes del contenedor de actores
        actorSelectorContainer.parentElement.insertBefore(searchInput, actorSelectorContainer);

        // Funcionalidad de búsqueda
        searchInput.addEventListener('input', function () {
            const searchTerm = this.value.toLowerCase();
            const actorItems = document.querySelectorAll('.actor-checkbox-item');

            actorItems.forEach(item => {
                const actorName = item.querySelector('label').textContent.toLowerCase();
                if (actorName.includes(searchTerm)) {
                    item.style.display = 'flex';
                } else {
                    item.style.display = 'none';
                }
            });
        });
    }
});

// Función para manejar errores de imagen de manera global
function handleImageError(img) {
    img.src = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIHZpZXdCb3g9IjAgMCA0MCA0MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPGNpcmNsZSBjeD0iMjAiIGN5PSIyMCIgcj0iMjAiIGZpbGw9IiNEQkU0RkYiLz4KPHN2ZyB3aWR0aD0iMjQiIGhlaWdodD0iMjQiIHZpZXdCb3g9IjAgMCAyNCAyNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4PSI4IiB5PSI4Ij4KPHBhdGggZD0iTTEyIDEyQzE0LjIwOTEgMTIgMTYgMTAuMjA5MSAxNiA4QzE2IDUuNzkwODYgMTQuMjA5MSA0IDEyIDRDOS43OTA4NiA0IDggNS43OTA4NiA4IDhDOCAxMC4yMDkxIDkuNzkwODYgMTIgMTIgMTJaIiBmaWxsPSIjOEU5NEZERCI+PC9wYXRoPgo8cGF0aCBkPSJNMTIgMTRDOC42ODYyOSAxNCA2IDE2LjY4NjMgNiAyMEg2LjAxQzYuMDEgMjAuMzMyIDYuMzM4IDIwLjY2IDYuNjcgMjAuNjZIMTcuMzNDMTcuNjYyIDIwLjY2IDE3Ljk5IDIwLjMzMiAxNy45OSAyMEMxNy45OSAxNi42ODYzIDE1LjMxMzcgMTQgMTIgMTRaIiBmaWxsPSIjOEU5NEZERCI+PC9wYXRoPgo8L3N2Zz4KPC9zdmc+';
    img.style.backgroundColor = '#f8f9fa';
}
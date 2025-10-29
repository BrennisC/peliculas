// JavaScript para funciones de búsqueda
document.addEventListener('DOMContentLoaded', function () {
    // Auto-enfoque en campo de búsqueda cuando se presiona Ctrl+F
    document.addEventListener('keydown', function (e) {
        if (e.ctrlKey && e.key === 'f') {
            e.preventDefault();
            const searchInput = document.querySelector('input[name="search"]');
            if (searchInput) {
                searchInput.focus();
                searchInput.select();
            }
        }
    });

    // Limpiar búsqueda cuando se presiona Escape
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') {
            const searchInput = document.querySelector('input[name="search"]');
            if (searchInput && searchInput.value) {
                searchInput.value = '';
                searchInput.form.submit();
            }
        }
    });

    // Búsqueda en tiempo real con debounce
    const searchInputs = document.querySelectorAll('input[name="search"]');

    searchInputs.forEach(function (input) {
        let timeoutId;

        input.addEventListener('input', function () {
            clearTimeout(timeoutId);

            // Solo buscar si hay al menos 2 caracteres o está vacío
            if (this.value.length >= 2 || this.value.length === 0) {
                timeoutId = setTimeout(() => {
                    // Enviar formulario automáticamente después de 500ms de inactividad
                    this.form.submit();
                }, 500);
            }
        });
    });

    // Resaltar términos de búsqueda
    const searchTerm = new URLSearchParams(window.location.search).get('search');
    if (searchTerm && searchTerm.trim()) {
        highlightSearchTerms(searchTerm.trim());
    }
});

// Función para resaltar términos de búsqueda
function highlightSearchTerms(term) {
    const elements = document.querySelectorAll('td, .card-title, .card-text');

    elements.forEach(function (element) {
        if (element.children.length === 0) { // Solo elementos de texto
            const regex = new RegExp(`(${escapeRegExp(term)})`, 'gi');
            const html = element.innerHTML.replace(regex, '<mark class="bg-warning">$1</mark>');
            element.innerHTML = html;
        }
    });
}

// Función para escapar caracteres especiales en regex
function escapeRegExp(string) {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

// Función para cargar más resultados (para futuras implementaciones AJAX)
function loadMoreResults(endpoint, container, page = 0, size = 10) {
    const searchTerm = new URLSearchParams(window.location.search).get('search') || '';

    let url = `${endpoint}?page=${page}&size=${size}`;
    if (searchTerm) {
        url += `&search=${encodeURIComponent(searchTerm)}`;
    }

    fetch(url)
        .then(response => response.json())
        .then(data => {
            // Implementar lógica para mostrar resultados
            console.log('Resultados cargados:', data);
        })
        .catch(error => {
            console.error('Error al cargar resultados:', error);
        });
}

// Función para mostrar sugerencias de búsqueda
function showSearchSuggestions(input, suggestions) {
    const existingSuggestions = document.querySelector('.search-suggestions');
    if (existingSuggestions) {
        existingSuggestions.remove();
    }

    if (suggestions.length === 0) return;

    const suggestionsDiv = document.createElement('div');
    suggestionsDiv.className = 'search-suggestions position-absolute bg-white border rounded shadow-lg mt-1 w-100';
    suggestionsDiv.style.zIndex = '1000';

    suggestions.forEach(function (suggestion) {
        const suggestionItem = document.createElement('div');
        suggestionItem.className = 'p-2 border-bottom cursor-pointer suggestion-item';
        suggestionItem.textContent = suggestion;

        suggestionItem.addEventListener('click', function () {
            input.value = suggestion;
            input.form.submit();
        });

        suggestionItem.addEventListener('mouseenter', function () {
            this.classList.add('bg-light');
        });

        suggestionItem.addEventListener('mouseleave', function () {
            this.classList.remove('bg-light');
        });

        suggestionsDiv.appendChild(suggestionItem);
    });

    input.parentNode.style.position = 'relative';
    input.parentNode.appendChild(suggestionsDiv);
}

// Función para ocultar sugerencias
function hideSuggestions() {
    const suggestions = document.querySelector('.search-suggestions');
    if (suggestions) {
        suggestions.remove();
    }
}

// Agregar estilos para los elementos resaltados
const style = document.createElement('style');
style.textContent = `
    .suggestion-item {
        cursor: pointer;
        transition: background-color 0.2s ease;
    }
    
    .suggestion-item:hover {
        background-color: #f8f9fa !important;
    }
    
    mark.bg-warning {
        background-color: #fff3cd !important;
        padding: 2px 4px;
        border-radius: 3px;
        font-weight: bold;
    }
    
    .search-suggestions {
        max-height: 200px;
        overflow-y: auto;
    }
`;
document.head.appendChild(style);
document.addEventListener('DOMContentLoaded', () => {
    displayGeneros();
    const form = document.getElementById('generoForm');
    form.addEventListener('submit', handleSubmit);
});

async function fetchGeneros() {
    try {
        const response = await fetch('/api/generos');
        if (!response.ok) throw new Error('Network response was not ok');
        const generos = await response.json();
        return generos;
    } catch (error) {
        console.error('Error fetching generos:', error);
    }
}



function displayGeneros() {
    fetchGeneros().then(generos => {
        const generosList = document.getElementById('generosList');
        generosList.innerHTML = '';
        generos.forEach(genero => {
            const generoElement = createGeneroElement(genero);
            generosList.appendChild(generoElement);
        });
    });
}



function createGeneroElement(genero) {
    const div = document.createElement('div');
    div.className = 'genero-item';

    const name = document.createElement('h3');
    name.textContent = genero.nombre;

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Eliminar';
    deleteButton.onclick = () => deleteGenero(genero.id);

    div.appendChild(name);
    div.appendChild(deleteButton);

    return div;
}
function handleSubmit(event) {
    event.preventDefault();

    const formData = new FormData(event.target);

    fetch('/api/genero', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                displayGeneros();
                event.target.reset();
            } else {
                throw new Error('Error al guardar');
            }
        })
        .catch(error => console.error('Error:', error));
}



function deleteGenero(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar este género?')) return

    fetch(`/api/genero/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                displayGeneros();
            } else {
                throw new Error('Error al eliminar');
            }
        })
        .catch(error => console.error('Error:', error));

}
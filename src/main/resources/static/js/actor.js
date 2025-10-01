document.addEventListener('DOMContentLoaded', function () {
    displayActors();

    const form = document.getElementById('actorForm');
    form.addEventListener('submit', handleSubmit);
});

async function fetchActors() {
    try {
        const response = await fetch('/api/actores');
        if (!response.ok) throw new Error('Network response was not ok');
        const actors = await response.json();
        return actors;
    } catch (error) {
        console.error('Error:', error);
    }
}

function displayActors() {
    fetchActors().then(actors => {
        const actorsList = document.getElementById('actorsList');
        actorsList.innerHTML = '';
        actors.forEach(actor => {
            const actorElement = createActorElement(actor);
            actorsList.appendChild(actorElement);
        });
    });


    console.log('Actors loaded');
}

function createActorElement(actor) {
    const div = document.createElement('div');
    div.className = 'actor-item';

    const img = document.createElement('img');
    img.alt = actor.nombre;
    img.src = actor.urlImagen;
    console.log('Actor image URL:', actor.urlImagen);
    img.style.width = '100px';
    img.style.height = '150px';

    const name = document.createElement('h3');
    name.textContent = actor.nombre;

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Eliminar';
    deleteButton.onclick = () => deleteActor(actor.id);
    deleteButton.style.marginTop = '10px';
    deleteButton.style.padding = '5px 10px';

    div.appendChild(name);
    div.appendChild(img);
    div.appendChild(deleteButton);

    return div;
}

function handleSubmit(event) {
    event.preventDefault();

    const formData = new FormData(event.target);

    fetch('/api/actor', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                displayActors();
                event.target.reset();
            } else {
                throw new Error('Error al guardar');
            }
        })
        .catch(error => console.error('Error:', error));
}

function deleteActor(id) {
    if (!confirm('¿Está seguro de eliminar este actor?')) return;

    fetch(`/api/actor/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                displayActors();
            } else {
                throw new Error('Error al eliminar');
            }
        })
        .catch(error => console.error('Error:', error));
}

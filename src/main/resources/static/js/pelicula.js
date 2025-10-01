
document.addEventListener('DOMContentLoaded', function () {
    loadPeliculas();
    loadActoresSelect();
    loadGenerosSelect();
});

async function fetchActores() {
    try {
        const response = await fetch('/api/actores');
        if (!response.ok) throw new Error('Error al obtener actores');
        return await response.json();
    } catch (error) {
        console.error('Error fetching actores:', error);
        return [];
    }
}

async function fetchGeneros() {
    try {
        const response = await fetch('/api/generos');
        if (!response.ok) throw new Error('Error al obtener géneros');
        return await response.json();
    } catch (error) {
        console.error('Error fetching generos:', error);
        return [];
    }
}

async function loadActoresSelect() {
    const selectActores = document.getElementById('select-actores');
    if (!selectActores) return;
    selectActores.innerHTML = '<option value="">Seleccione uno o más actores</option>';
    const actores = await fetchActores();
    actores.forEach(actor => {
        const option = document.createElement('option');
        option.value = actor.id;
        option.textContent = actor.nombre || actor.name;
        selectActores.appendChild(option);
    });
}

async function loadGenerosSelect() {
    const selectGeneros = document.getElementById('select-generos');
    if (!selectGeneros) return;
    selectGeneros.innerHTML = '<option value="">Seleccione un género</option>';
    const generos = await fetchGeneros();
    generos.forEach(genero => {
        const option = document.createElement('option');
        option.value = genero.id;
        option.textContent = genero.nombre || genero.name;
        selectGeneros.appendChild(option);
    });
}



async function fetchPeliculas() {
    try {
        const response = await fetch('/api/peliculas');
        if (!response.ok) throw new Error('Network response was not ok');
        const peliculas = await response.json();
        console.log(peliculas && peliculas.length ? peliculas[0] : 'No hay peliculas');
        return peliculas;
    } catch (error) {
        console.error('Error fetching peliculas:', error);
    }
}

function loadPeliculas() {
    fetchPeliculas().then(peliculas => {
        const peliculasList = document.getElementById('peliculasList');
        peliculasList.innerHTML = '';
        if (!peliculas || !peliculas.length) {
            peliculasList.innerHTML = '<div>No hay películas registradas.</div>';
            return;
        }
        peliculas.forEach(pelicula => {
            const peliculaElement = createPeliculaElement(pelicula);
            peliculasList.appendChild(peliculaElement);
        });
    });
}

function createPeliculaElement(pelicula) {
    const div = document.createElement('div');
    div.className = 'pelicula-item';
    const title = document.createElement('h3');
    title.textContent = pelicula.titulo || pelicula.title;
    const releaseDate = document.createElement('p');
    releaseDate.textContent = `Fecha de Estreno: ${pelicula.fechaEstreno ? new Date(pelicula.fechaEstreno).toLocaleDateString() : ''}`;
    const genre = document.createElement('p');
    genre.textContent = `Género: ${pelicula.genero && (pelicula.genero.nombre || pelicula.genero.name)}`;
    const actors = document.createElement('p');
    actors.textContent = `Actores: ${pelicula.actores && pelicula.actores.map(actor => actor.nombre || actor.name).join(', ')}`;
    div.appendChild(title);
    div.appendChild(releaseDate);
    div.appendChild(genre);
    div.appendChild(actors);
    return div;
}


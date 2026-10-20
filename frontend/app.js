const API = window.location.port === '5500'
    ? 'http://localhost:8080/api/albumes'
    : '/api/albumes';

const grid = document.getElementById('grid');
const vacio = document.getElementById('vacio');
const buscador = document.getElementById('buscador');
const toast = document.getElementById('toast');
let albumes = [];

const PORTADAS = {
    'Vessel': 'vessel.jpg',
    'Blurryface': 'blurryface.jpg',
    'Trench': 'trench.jpg',
    'Scaled and Icy': 'scaled-and-icy.jpg',
    'Clancy': 'clancy.jpg',
    'Breach': 'breach.jpg',
    'Messy Mind': 'messy.jpg',
    'Technicolor Daydrem': 'tech.jpg',
    'Loud Fantasy, Quiet Reality': 'loud.png',
    'Pixelated Oblivion': 'pixel.png',
    'Cherry Blossom Nightmare': 'cherry.png',
    'Dizzy Bezerko': 'dizzy.jpg',
    'Ghost Gardens': 'ghost.jpg',
    'Kiss Collapse': 'kiss.png',
    'Double Daze': 'double.png'
};

const clp = n => new Intl.NumberFormat('es-CL', {
    style: 'currency',
    currency: 'CLP',
    maximumFractionDigits: 0
}).format(n);

function mostrarToast(msg, error = false) {
    toast.textContent = msg;
    toast.className = 'toast show' + (error ? ' error' : '');
    setTimeout(() => { toast.className = 'toast'; }, 3000);
}

function tonoDe(texto) {
    let h = 0;
    for (const c of texto) h = (h * 31 + c.charCodeAt(0)) % 360;
    return h;
}

function slug(texto) {
    return texto
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
        .toLowerCase()
        .replace(/[^a-z0-9]+/g, '-')
        .replace(/(^-|-$)/g, '');
}

function rutaPortada(titulo) {
    return `img/${PORTADAS[titulo] ?? slug(titulo) + '.jpg'}`;
}

function tarjeta(a) {
    const card = document.createElement('article');
    card.className = 'card';

    const cover = document.createElement('div');
    cover.className = 'cover';
    const h = tonoDe(a.titulo);
    cover.style.background = `linear-gradient(135deg, hsl(${h},70%,45%), hsl(${(h + 60) % 360},70%,22%))`;
    cover.textContent = a.titulo.charAt(0).toUpperCase();

    const img = document.createElement('img');
    img.className = 'cover-img';
    img.src = rutaPortada(a.titulo);
    img.alt = `Portada de ${a.titulo}`;
    img.loading = 'lazy';
    img.addEventListener('error', () => img.remove());
    cover.append(img);

    const info = document.createElement('div');
    info.className = 'info';

    const titulo = document.createElement('h3');
    titulo.textContent = a.titulo;
    const artista = document.createElement('p');
    artista.className = 'artista';
    artista.textContent = `${a.artista} · ${a.anio}`;

    const meta = document.createElement('div');
    meta.className = 'meta';
    const precio = document.createElement('span');
    precio.className = 'precio';
    precio.textContent = clp(a.precio);
    const stock = document.createElement('span');
    stock.textContent = a.stock > 0 ? `Stock: ${a.stock}` : 'Agotado';
    meta.append(precio, stock);

    const boton = document.createElement('button');
    boton.textContent = a.stock > 0 ? 'Comprar' : 'Sin stock';
    boton.disabled = a.stock <= 0;
    boton.addEventListener('click', () => comprar(a));

    info.append(titulo, artista, meta, boton);
    card.append(cover, info);
    return card;
}

function render() {
    const q = buscador.value.trim().toLowerCase();
    const filtrados = albumes.filter(a =>
        [a.titulo, a.artista, a.genero].some(v => (v || '').toLowerCase().includes(q)));
    grid.replaceChildren(...filtrados.map(tarjeta));
    vacio.hidden = filtrados.length > 0;
}

async function cargar() {
    try {
        const r = await fetch(API);
        albumes = await r.json();
        render();
    } catch {
        mostrarToast('No se pudo conectar con el servidor', true);
    }
}

async function comprar(a) {
    const r = await fetch(`${API}/${a.id}/comprar?cantidad=1`, { method: 'POST' });
    if (r.ok) {
        mostrarToast(`Compraste "${a.titulo}"`);
        cargar();
    } else {
        mostrarToast(await r.text(), true);
    }
}

buscador.addEventListener('input', render);
cargar();
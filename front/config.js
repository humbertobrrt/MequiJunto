// Seleciona automaticamente a API conforme o ambiente.
// Local: execute o frontend em localhost e a API em http://localhost:8080.
// Online: utiliza o backend publicado no Render.
const MJ_API = ["localhost", "127.0.0.1"].includes(window.location.hostname)
    ? "http://localhost:8080"
    : "https://mequijunto.onrender.com";

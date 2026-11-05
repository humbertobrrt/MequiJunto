/* Tema + notificações. Carregado em todas as páginas (depois do config.js). */
(function () {
    const pref = (k, d) => { try { const v = localStorage.getItem("mj_" + k); return v === null ? d : v === "1"; } catch (e) { return d; } };

    /* ---------- Tema ---------- */
    const CSS_ESCURO = `
    html[data-tema="escuro"] body { background:#181818 !important; color:#eee !important; }
    html[data-tema="escuro"] :is(.kpi,.card,.chart-card,.recent-card,.top-card,.alert-item,.alerta,.colab,.modal,.painel,.conv-lista,.conv-chat,.conv-item,.bolha:not(.ia),.busca,.select-filtro,.select-status,.etapa,.etapa-menu,.menu-pop,.pill,.dia) { background:#262626 !important; color:#eee !important; border-color:#555 !important; }
    html[data-tema="escuro"] :is(.campo input,.campo textarea,.campo select) { background:#1c1c1c !important; color:#eee !important; border-color:#555 !important; }
    html[data-tema="escuro"] :is(.busca input,.pill select) { color:#eee !important; }
    html[data-tema="escuro"] .btn:not(.primario):not(.amarelo) { background:#333 !important; color:#eee !important; border-color:#666 !important; }
    html[data-tema="escuro"] :is(.titulo,.greeting,.modal h2,.chat-topo h2,.info h3,.conv-info strong,.nome,.top-info h3) { color:#fff !important; }
    html[data-tema="escuro"] :is(.contagem,.user-info span,.conv-info small,.conv-resumo,.chat-topo p,.vazio,.kpi h3,.info p,.bolha .meta) { color:#aaa !important; }
    html[data-tema="escuro"] :is(.total-box,.top-item small,.alerta-topo,.tag-status,.status,.btn.amarelo,.bolha.ia,.menu a.active) { color:#000 !important; }
    html[data-tema="escuro"] .alerta.alto .alerta-topo { color:#fff !important; }
    html[data-tema="escuro"] #grafico text { fill:#ddd; }
    html[data-tema="escuro"] #grafico line { stroke:#444; }
    html[data-tema="escuro"] .modal-linha, html[data-tema="escuro"] .chat-topo { border-color:#444 !important; }`;

    function aplicarTema() {
        let t = "claro";
        try { t = localStorage.getItem("mj_tema") || "claro"; } catch (e) {}
        document.documentElement.setAttribute("data-tema", t);
    }
    const st = document.createElement("style");
    st.textContent = CSS_ESCURO;
    document.head.appendChild(st);
    aplicarTema();
    window.mjAplicarTema = aplicarTema;

    /* ---------- Notificações de novos alertas ---------- */
    function beep() {
        try {
            const c = new (window.AudioContext || window.webkitAudioContext)();
            const o = c.createOscillator(), g = c.createGain();
            o.type = "sine"; o.frequency.value = 880; g.gain.value = 0.08;
            o.connect(g); g.connect(c.destination);
            o.start(); o.stop(c.currentTime + 0.2);
        } catch (e) {}
    }

    function popup(texto) {
        const a = document.createElement("a");
        a.href = "alertas.html";
        a.textContent = "🔔 " + texto;
        a.style.cssText = "position:fixed;top:18px;right:18px;z-index:100;max-width:320px;padding:12px 16px;background:#da291c;color:#fff;border-radius:12px;font:600 14px 'DM Sans','Inter',sans-serif;text-decoration:none;box-shadow:0 6px 18px rgba(0,0,0,.3)";
        document.body.appendChild(a);
        setTimeout(() => a.remove(), 8000);
    }

    window.mjNotificar = function (texto, forcar) {
        if (forcar || pref("popup", true)) popup(texto);
        if (forcar || pref("som", true)) beep();
    };

    async function checar() {
        const token = sessionStorage.getItem("token");
        if (!token || typeof MJ_API === "undefined") return;
        try {
            const res = await fetch(MJ_API + "/api/alertas?status=PENDENTE", { headers: { Authorization: "Bearer " + token } });
            if (!res.ok) return;
            const lista = await res.json();
            const ids = lista.map(a => a.id);
            const vistos = JSON.parse(sessionStorage.getItem("mj_vistos") || "null");
            sessionStorage.setItem("mj_vistos", JSON.stringify(ids));
            if (vistos === null) return; // primeira leitura: só marca o que já existe
            const novos = lista.filter(a => !vistos.includes(a.id));
            if (novos.length) window.mjNotificar(novos.length === 1 ? "Novo alerta: " + (novos[0].nomeUsuario || "colaborador") : novos.length + " novos alertas");
        } catch (e) {}
    }
    document.addEventListener("DOMContentLoaded", () => { checar(); setInterval(checar, 30000); });
})();
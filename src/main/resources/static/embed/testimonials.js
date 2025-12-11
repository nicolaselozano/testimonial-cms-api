(function () {
  const script = document.currentScript;
  const limit = script.getAttribute("data-limit") || 6;

  fetch("http://localhost:8080/api/testimonials/public?limit=" + limit)
    .then(r => r.json())
    .then(data => {
      const container = document.getElementById("testimonial-widget");
      if (!container) return;

      container.innerHTML = data.map(t => `
        <div style="border:1px solid #ddd; padding:12px; margin-bottom:10px;">
          <h3 style="margin:0;">${t.title}</h3>
          <p>${t.content}</p>
        </div>
      `).join("");
    });
})();

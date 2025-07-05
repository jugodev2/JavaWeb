<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<% Throwable error = (Throwable) request.getAttribute("exception"); %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <title>¡Algo salió mal!</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        html, body {
            height: 100%;
            margin: 0;
        }
        body {
            background-color: #f8f9fa;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 1rem;
        }
        .error-container {
            max-width: 900px;
            width: 100%;
            text-align: center;
        }
        h1.display-3 {
            font-size: clamp(2rem, 5vw, 3.5rem);
            color: #dc3545; /* text-danger */
            margin-bottom: 1rem;
        }
        p.lead {
            font-size: clamp(1rem, 2vw, 1.25rem);
            color: #6c757d; /* text-secondary */
            margin-bottom: 1rem;
        }
        .alert-danger {
            max-height: 30vh;
            overflow-y: auto;
            text-align: left;
            font-size: clamp(0.75rem, 1vw, 1rem);
            margin-bottom: 1.5rem;
        }
        pre {
            white-space: pre-wrap;
            word-break: break-word;
            margin: 0;
        }
        img {
            width: 100%;
            height: auto;
            max-height: 40vh;
            object-fit: contain;
            margin-bottom: 1.5rem;
            border-radius: 0.25rem;
        }
        .btn-primary {
            padding: 0.75rem 2rem;
            font-size: clamp(1rem, 2vw, 1.25rem);
        }
        @media (max-height: 600px) {
            .alert-danger {
                max-height: 20vh;
                font-size: 0.75rem;
            }
            img {
                max-height: 30vh;
            }
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1 class="display-3">¡Algo salió mal!</h1>
        <p class="lead">Lo sentimos, ocurrió un error inesperado.</p>
        
        <% if (error != null) { %>
            <div class="alert alert-danger">
                <strong>Tipo de error:</strong> <%= error.getClass().getName() %><br />
                <strong>Mensaje:</strong> <%= error.getMessage() != null ? error.getMessage() : "Sin mensaje disponible" %><br />
                <% if (error.getCause() != null) { %>
                    <strong>Causa:</strong> <%= error.getCause().toString() %><br />
                <% } %>
            </div>
        <% } %>

        <img src="https://attachments-cdn-s.coub.com/coub_storage/coub/simple/cw_image/1d5d67e108a/24119f26e828d74bd0d97/1480094298_00064.jpg"
             alt="Error" />

        <div class="d-flex justify-content-center gap-3 mt-4">
            <a href="<%=request.getContextPath()%>/index.jsp" class="btn btn-primary">Volver al inicio</a>
            <button id="playAudioBtn" class="btn btn-primary">Reproducir sonido de error</button>
        </div>

    </div>
    <audio id="audioError" loop>
        <source src="<%=request.getContextPath()%>/audio/audio.mp3" type="audio/mpeg" />
        Tu navegador no soporta la reproducción de audio.
    </audio>
<script>
  const audio = document.getElementById('audioError');
  const btn = document.getElementById('playAudioBtn');

  btn.addEventListener('click', () => {
    audio.play();
    btn.style.display = 'none';
  });
</script>
</body>
</html>

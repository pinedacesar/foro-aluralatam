CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(255) NOT NULL,
    autor_id BIGINT,
    nombre_curso VARCHAR(255),
    CONSTRAINT fk_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id) ON DELETE SET NULL
);
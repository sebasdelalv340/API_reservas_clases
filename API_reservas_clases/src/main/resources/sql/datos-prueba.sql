INSERT INTO `usuarios`(`id`, `username`, `password`, `roles`) VALUES (1,'sebas','$2a$10$DBc2FPq.4XperQMRTGpYnufwdTFxFCJtRZj1zsX.7vFo9YVe9rCyW','ADMIN')

INSERT INTO `clases` (`id`, `nombre`, `descripcion`, `aforo`, `fecha_clase`) VALUES (1, 'Yoga Avanzado', 'Clase de yoga para niveles avanzados', 3, '2024-12-20 10:00:00')

INSERT INTO `reservas` (`id`, `clase_id`, `usuario_id`, `fecha_creacion`) VALUES (1, 1, 1, '2024-12-16 08:00:00')
INSERT INTO `reservas` (`id`, `clase_id`, `usuario_id`, `fecha_creacion`) VALUES (2, 1, 1, '2024-12-16 08:05:00')
INSERT INTO `reservas` (`id`, `clase_id`, `usuario_id`, `fecha_creacion`) VALUES (3, 1, 1, '2024-12-16 08:10:00')





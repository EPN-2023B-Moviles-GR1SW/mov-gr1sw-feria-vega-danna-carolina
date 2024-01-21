Feature: Programar un recordatorio para una tarea  (ID: FT4)

    Description: Permite a los usuarios establecer recordatorios para sus tareas, asegurando que no se pierdan fechas importantes y mejorando la gestión del tiempo.

    Scenario: Un usuario programa un recordatorio para una tarea
    
        Given el usuario tiene una tarea "Reunión de equipo" con fecha de vencimiento "2024-02-01"
        When el usuario programa un recordatorio para la tarea eligiendo una fecha
        Then el usuario debe recibir un recordatorio en la fecha escogida

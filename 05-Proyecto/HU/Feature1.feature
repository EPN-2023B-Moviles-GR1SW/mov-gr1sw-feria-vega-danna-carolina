Feature: Crear una tarea  (ID: FT1)

    Description: Permite a los usuarios crear nuevas tareas, asignarlas a una categoría (opcional) y establecer su estado, proporcionando una manera eficiente de gestionar sus responsabilidades diarias.

    Scenario: Un usuario crea una nueva tarea

        Given el usuario que se encuentra en la página principal
        When el usuario crea una nueva tarea con un título "NuevaTarea"
        Then la tarea "NuevaTarea" debe aparecer en la lista general de tareas "Todo"
        And el estado de la tarea es "Por hacer"

    Scenario: Un usuario crea una nueva tarea que pertenece a una categoría

        Given el usuario que se encuentra en la página principal
        When el usuario crea una nueva tarea con un título "NuevaTarea" y selecciona la categoría "Móviles"
        Then la tarea "NuevaTarea" debe aparecer en la categoría "Móviles"
        And el estado de la tarea es "Por hacer"

    Scenario: Un usuario crea una nueva tarea dentro de una categoría

        Given el usuario que se encuentra en la categoría "Móviles"
        When el usuario crea una nueva tarea con un título "Examen"
        Then la tarea "Examen" debe aparecer en la categoría "Móviles"
        And el estado de la tarea es "Por hacer"


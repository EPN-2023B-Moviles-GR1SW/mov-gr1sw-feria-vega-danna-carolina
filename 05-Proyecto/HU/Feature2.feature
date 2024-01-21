Feature: Crear una categoría  (ID: FT2)

    Description: Habilita a los usuarios para organizar sus tareas mediante la creación de categorías personalizadas, proporcionando una estructura más detallada y organizada para la gestión de tareas.

    Scenario: Un usuario agrega una nueva categoría

        Given el usuario que se encuentra en la página principal
        When el usuario agrega una nueva categoría con un nombre "Móviles"
        Then la categoría "Móviles" debe aparecer en la página principal

    Scenario: Un usuario agrega una nueva categoría al momento de crear una tarea

        Given el usuario que se encuentra en la vista de crear una tarea
        When el usuario agrega una nueva categoría con un nombre "HCI"
        And se selecciona la categoría "HCI"
        Then la categoría "HCI" debe aparecer en la página principal 
        And la nueva tarea debe aparecer en la categoría "HCI"
        And el estado de la tarea es "Por hacer"
    
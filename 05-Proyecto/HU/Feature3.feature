Feature: Cambiar el estado de una tarea  (ID: FT3)

    Description: Ofrece la posibilidad de cambiar el estado de una tarea, lo que permite a los usuarios realizar un seguimiento del progreso de sus tareas y gestionar eficazmente su flujo de trabajo.

    Scenario: Un usuario cambia el estado de una tarea

        Given el usuario tiene una tarea "Enviar informe" en estado "Por hacer"
        When el usuario cambia el estado de la tarea a "En proceso"
        Then la tarea "Enviar informe" debe estar en estado "En proceso"
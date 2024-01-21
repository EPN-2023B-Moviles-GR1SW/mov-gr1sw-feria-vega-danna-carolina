Feature: Filtrar tareas por categoría  (ID: FT5)

  Description:Facilita a los usuarios la visualización específica de tareas relacionadas con una categoría particular, simplificando la gestión y concentrándose en áreas temáticas específicas.
  
  Scenario: Un usuario filtra tareas por categoría

    Given el usuario tiene varias tareas en diferentes categorías
    When el usuario selecciona la categoría "Móviles"
    Then solo se muestran las tareas relacionadas con la categoría "Móviles"

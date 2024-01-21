Feature: Crear una Cuenta  (ID: FT7)

  Description: Permite a nuevos usuarios registrarse en la aplicación, proporcionando una plataforma para la gestión personalizada de tareas con características específicas asociadas a su cuenta individual.

  Scenario: Usuario crea una nueva cuenta

    Given el usuario está en la página de registro
    When el usuario completa el formulario de registro con nombre de usuario "nuevo_usuario", correo electrónico "nuevo@correo.com", y contraseña "nuevaContrasena123"
    Then el usuario debería ser redirigido a la página principal

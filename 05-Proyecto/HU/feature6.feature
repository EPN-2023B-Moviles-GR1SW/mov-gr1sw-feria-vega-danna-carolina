Feature: Iniciar sesión  (ID: FT6)

    Description: Proporciona a los usuarios la capacidad de acceder a sus cuentas de manera segura, garantizando un inicio de sesión eficiente y seguro.

    Scenario: Usuario inicia sesión con credenciales válidas

        Given el usuario está en la página de inicio de sesión
        When el usuario ingresa el nombre de usuario "ejemplo_usuario" y la contraseña "contrasena123"
        Then el usuario debe ser redirigido a su página principal

    Scenario: Usuario intenta iniciar sesión con credenciales inválidas

        Given el usuario está en la página de inicio de sesión
        When el usuario ingresa el nombre de usuario "usuario_invalido" y la contraseña "contrasena_invalida"
        Then el usuario debería ver un mensaje de error indicando credenciales inválidas

    Scenario: Usuario olvida las credenciales para iniciar sesión

        Given el usuario está en la página de inicio de sesión
        When el usuario no recuerda las credenciales 
        Then el usuario debe poder recuperar las credenciales de inicio de sesión
        
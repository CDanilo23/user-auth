# Users Auth API

## Descripción
Esta es una aplicación de backend para el registro y autenticación de usuarios. Proporciona endpoints para gestionar usuarios, incluyendo la creación, validación de correos y contraseñas, y autenticación. La API está documentada utilizando Swagger.

## Tecnologías Utilizadas
- **Java** 17
- **Spring Boot** 3.x
- **Spring Security** para la autenticación
- **H2 Database** como base de datos en memoria
- **Swagger/OpenAPI** para la documentación de la API
- **Maven** como herramienta de gestión de proyectos

## Configuración de Patrones
Los patrones de seguridad para la validación de contraseñas y correos están configurados en el archivo `application.properties` y son los siguientes:

```properties
app.security.password.pattern=^.{8,}$
app.security.email.pattern=^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.cl$
```
## Pruebas Unitarias
La aplicación incluye pruebas unitarias que verifican la funcionalidad de los componentes principales. Para ejecutarlas, utiliza el siguiente comando:
```
mvn test
```
## Patrones de Diseño Utilizados
###### Builder: Implementado en UserLogin para facilitar la creación de objetos de usuario.
###### Singleton: Utilizado en la configuración de seguridad para asegurar que la instancia de seguridad sea única.
###### Facade: La clase UserService actúa como un facade, proporcionando una interfaz simplificada para la gestión de usuarios.
###### DTO (Data Transfer Object): Utilizado para transferir datos entre la API y los servicios.

## Entidades y Tablas
Las tablas se generan automáticamente a partir de las entidades Phone y UserLogin, que están definidas en el modelo de datos.

## Excepciones Personalizadas
La aplicación incluye varias excepciones personalizadas para manejar errores específicos, como:

###### EmailAlreadyExistsException: Lanza esta excepción cuando se intenta registrar un correo que ya existe.
###### InvalidEmailException: Lanza esta excepción cuando el correo proporcionado no tiene un formato válido.
###### InvalidPasswordException: Lanza esta excepción cuando la contraseña no cumple con los requisitos de seguridad.

## Ejecución de la Aplicación
Para ejecutar la aplicación, puedes hacerlo desde la consola utilizando el siguiente comando:
````
mvn spring-boot:run
````
## Endpoint de Prueba
Puedes probar el siguiente endpoint para registrar un nuevo usuario:
````
POST http://localhost:8081/api/user/register
````
#### JSON de Prueba
Aquí tienes un ejemplo del JSON que puedes enviar para probar el registro de usuario:
````
{
   "name":"Cristian Ordoñez",
   "email":"critian@rodriguez.cl",
   "password":"hunter23",
   "phones":[
      {
         "number":"1234567",
         "citycode":"1",
         "contrycode":"57"
      }
   ]
}
````
## SWAGGER  
###### Consola H2: - Permite acceder a la base de datos H2 en memoria.
````
http://localhost:8080/h2-console/ 
````
###### Swagger UI: - Proporciona una interfaz gráfica para explorar y probar la API.
````
http://localhost:8080/swagger-ui/index.html 
````
###### Documentación de API: - Ofrece la especificación de la API en formato JSON.
````
 http://localhost:8080/v3/api-docs 
````
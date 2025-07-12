# Documentación Detallada de los Microservicios

## Introducción

Este sistema está compuesto por tres microservicios interconectados que implementan una arquitectura de autenticación y autorización basada en OAuth2 con Keycloak. Los microservicios se comunican entre sí utilizando Feign Client, lo que facilita las llamadas a APIs REST entre servicios.

## Arquitectura General

La arquitectura del sistema está formada por:

1. **API Gateway (feign-api-gateway)**: Punto de entrada único al sistema, responsable de la autenticación y enrutamiento.
2. **Servicio de Usuarios (feign-user-service)**: Gestiona la información de usuarios y se comunica con el servicio de suscripciones.
3. **Servicio de Suscripciones (feign-subscription-service)**: Administra las suscripciones de los usuarios y sus planes asociados.

## 1. API Gateway (feign-api-gateway)

### Propósito
El API Gateway actúa como el punto de entrada único para todas las solicitudes al sistema. Es responsable de la autenticación de usuarios mediante OAuth2/Keycloak y del enrutamiento de solicitudes a los microservicios correspondientes.

### Funcionalidades Principales
- **Autenticación centralizada**: Valida tokens JWT emitidos por Keycloak.
- **Enrutamiento de solicitudes**: Dirige las peticiones a los microservicios apropiados.
- **Propagación de tokens**: Transmite los tokens de autenticación a los servicios internos.

### Configuración
- Puerto: 8080 (configurable en application.yml)
- Integración con Keycloak como servidor de autorización
- Configuración de rutas para los diferentes microservicios

### Endpoints
- Proporciona rutas de acceso a los endpoints de los servicios de usuarios y suscripciones.

## 2. Servicio de Usuarios (feign-user-service)

### Propósito
Este servicio gestiona la información de los usuarios y actúa como intermediario para obtener información de suscripciones de los usuarios a través del servicio de suscripciones.

### Funcionalidades Principales
- **Gestión de usuarios**: Almacena y recupera información básica de usuarios.
- **Integración con servicio de suscripciones**: Recupera información de suscripciones para enriquecer los datos de usuario.
- **Propagación de tokens JWT**: Utiliza un interceptor Feign para transmitir tokens de autenticación en las llamadas al servicio de suscripciones.

### Modelos de Datos
- **User**: Contiene información básica de usuario (id, nombre, apellido, email) y una referencia a su suscripción.

### Repositorios
- Utiliza una lista en memoria para almacenar usuarios de ejemplo (UserConfiguration).
- Integra FeignSubscriptionRepository para comunicarse con el servicio de suscripciones.

### Servicios
- **UserService**: Busca usuarios por ID y enriquece la información con datos de suscripción.

### Controladores
- **UserRestController**: Expone endpoints para buscar usuarios por ID.

### Configuración
- Puerto: 8082 (configurado en application.properties)
- Habilitación de Feign Client para la comunicación con otros servicios
- Configuración de seguridad OAuth2 como recurso protegido

### Endpoints
- **GET /users/find/{id}**: Retorna la información de un usuario específico, incluyendo sus datos de suscripción.

## 3. Servicio de Suscripciones (feign-subscription-service)

### Propósito
Este servicio gestiona las suscripciones de los usuarios y los planes disponibles. Proporciona información sobre qué plan tiene cada usuario y los detalles de dichos planes.

### Funcionalidades Principales
- **Gestión de suscripciones**: Almacena y recupera información sobre las suscripciones de los usuarios.
- **Gestión de planes**: Administra los diferentes planes disponibles (Gold, Silver, Bronze).
- **Verificación de usuarios**: Opcionalmente verifica la existencia de usuarios antes de devolver sus suscripciones (a través de Feign).

### Modelos de Datos
- **Subscription**: Entidad principal que contiene información sobre la suscripción de un usuario (userId, fechas de inicio y fin, plan asociado).
- **Plan**: Entidad que define los diferentes planes disponibles (nombre, descripción, precio).
- **UserDTO**: Objeto de transferencia de datos para la información de usuario recibida del servicio de usuarios.

### Repositorios
- **ISubscriptionRepository**: Repositorio JPA para las suscripciones.
- **IPlanRepository**: Repositorio JPA para los planes.

### Servicios
- **SubscriptionService**: Busca suscripciones por ID de usuario y opcionalmente verifica la existencia del usuario.

### Controladores
- **SubscriptionRestController**: Expone endpoints para buscar suscripciones por ID de usuario.

### Configuración
- Puerto: 8083 (configurado en application.properties)
- Base de datos H2 en memoria con datos de ejemplo
- Configuración de seguridad OAuth2 como recurso protegido
- Permitiendo acceso al endpoint `/subscription/find` sin autenticación para la comunicación entre servicios

### Endpoints
- **GET /subscription/find?userId={id}**: Retorna la información de suscripción para un usuario específico.

## Flujo de Comunicación

1. **Cliente → API Gateway**: El cliente se autentica contra el API Gateway, recibiendo un token JWT.
2. **API Gateway → Servicio de Usuarios**: El API Gateway enruta la solicitud al servicio de usuarios, transmitiendo el token JWT.
3. **Servicio de Usuarios → Servicio de Suscripciones**: El servicio de usuarios necesita enriquecer los datos con información de suscripción, por lo que llama al servicio de suscripciones utilizando Feign Client y transmitiendo el token JWT mediante el interceptor.
4. **Servicio de Suscripciones → Respuesta**: El servicio de suscripciones verifica el token (o permite el acceso al endpoint específico sin autenticación) y devuelve la información de suscripción.
5. **Servicio de Usuarios → Cliente**: El servicio de usuarios combina su información con los datos de suscripción y devuelve la respuesta completa al cliente.

## Seguridad

- **OAuth2/Keycloak**: El sistema utiliza Keycloak como servidor de autorización OAuth2.
- **JWT**: Los tokens JWT son utilizados para la autenticación y autorización entre servicios.
- **Propagación de Tokens**: Los interceptores Feign aseguran que los tokens JWT se propaguen correctamente entre los servicios.
- **Configuración de Seguridad**: Cada servicio está configurado como un recurso protegido que valida los tokens JWT.

## Orden de Ejecución

Para ejecutar correctamente el sistema, debe seguirse este orden:

1. **Primero**: feign-api-gateway (puerto 8080)
2. **Segundo**: feign-user-service (puerto 8082)
3. **Tercero**: feign-subscription-service (puerto 8083)

Este orden asegura que las dependencias entre servicios estén disponibles en el momento adecuado.

## Datos de Ejemplo

El sistema viene precargado con datos de ejemplo:

- **Usuarios**: 5 usuarios de ejemplo con diferentes características.
- **Planes**: 3 planes (Gold, Silver, Bronze) con diferentes precios y características.
- **Suscripciones**: Asignaciones de diferentes planes a los usuarios con fechas de inicio y fin específicas.

Estos datos permiten probar el sistema completo sin necesidad de crear información manualmente.

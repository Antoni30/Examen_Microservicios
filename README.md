# Examen_Microservicios

Repositorio para un sistema de salud basado en arquitectura de microservicios, desarrollado en Java/Spring Boot. El sistema permite la gestión de dispositivos médicos, la recolección y análisis de signos vitales, así como la generación de notificaciones y reportes de salud.

## Estructura del Proyecto

El repositorio contiene varios microservicios principales:

- **RecolectorDatosPaciente**: Microservicio para la gestión de dispositivos médicos y la recolección de signos vitales asociados a cada dispositivo.
- **ms-analizadorSalud**: Encargado del análisis de los datos recolectados, generación de alertas médicas y reportes programados (por ejemplo, reportes diarios y verificación periódica de dispositivos inactivos).
- **ms-notificaciones**: Administra la generación y almacenamiento de notificaciones generales y específicas en el sistema.
- **ms-eureka-server**: Servidor Eureka para el descubrimiento de servicios.

## Descripción de los Microservicios

### 1. RecolectorDatosPaciente

- Permite crear, actualizar, eliminar y listar dispositivos médicos (`DispositivoMedico`).
- Gestiona la recolección, almacenamiento y consulta de signos vitales de los pacientes asociados a dispositivos.
- Expone endpoints REST para manipular dispositivos y signos vitales.
- Utiliza Spring Boot y JPA.

### 2. ms-analizadorSalud

- Analiza los datos de salud recibidos, generando alertas médicas.
- Genera reportes diarios de métricas y verifica cada 6 horas la inactividad de dispositivos médicos.
- Utiliza programación de tareas con anotaciones `@Scheduled`.

### 3. ms-notificaciones

- Permite guardar y listar notificaciones generales y específicas (por ejemplo, alertas críticas, avisos de dispositivos, etc.).
- Expone endpoints REST para la interacción con las notificaciones.
- Utiliza Spring Boot y JPA.

### 4. ms-eureka-server

- Permite el descubrimiento y registro de los microservicios en el sistema.

## Base de Datos

El sistema utiliza una base de datos **CrocroachDB** desplegada en un clúster de **3 nodos**. Antes de ejecutar los microservicios, es necesario crear las siguientes bases de datos en el clúster:

- **notificaciones**
- **signosvitales**
- **signosvitalesalertas**

Asegúrate de que la configuración de conexión en cada microservicio apunte al clúster de CrocoachDB y a la base de datos correspondiente.

## Instalación y Ejecución

Cada microservicio es una aplicación Spring Boot independiente. Para ejecutar el sistema completo:

1. **Clonar el repositorio:**
   ```sh
   git clone https://github.com/Antoni30/Examen_Microservicios.git
   cd Examen_Microservicios
   ```

2. **Instalar dependencias y compilar (desde la raíz o por microservicio):**
   ```sh
   ./mvnw clean install
   ```

3. **Ejecutar los microservicios (en diferentes terminales):**
   ```sh
   cd ms-eureka-server
   ./mvnw spring-boot:run
   # En otras terminales, ejecutar:
   cd ../RecolectorDatosPaciente
   ./mvnw spring-boot:run

   cd ../ms-analizadorSalud
   ./mvnw spring-boot:run

   cd ../ms-notificaciones
   ./mvnw spring-boot:run
   ```

4. **Configuración adicional:**
   - Verifica los archivos `application.properties` o `application.yml` de cada microservicio para detalles de configuración de puertos, conexión a bases de datos, etc.
   - Es posible que requieras tener configurado un servidor de mensajería (por ejemplo, RabbitMQ) si los microservicios se comunican mediante eventos.

## Dependencias Principales

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Cloud (Eureka)
- Lombok
- CrocoachDB (cluster 3 nodos)
- RabbitMQ (solo si tu arquitectura lo requiere)

## Contribuciones

Las contribuciones, reportes de problemas y mejoras son bienvenidas.

---

**Autor:** Antoni30

**Licencia:** MIT

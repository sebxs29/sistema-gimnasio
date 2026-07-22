# Sistema de Gestión de Gimnasio

Aplicación de escritorio desarrollada como proyecto final de **Programación Orientada a Objetos**. El sistema permite administrar la información principal de un gimnasio mediante una interfaz gráfica construida con JavaFX y una base de datos PostgreSQL alojada en Neon.

## Integrantes

- **Sebastian Toapanta**
- **Erick Patiño**

## Descripción

El proyecto centraliza la gestión de usuarios, clientes, entrenadores, planes, membresías, rutinas y asignaciones. Incluye autenticación, control de acceso por roles, operaciones CRUD, validaciones, reportes y una pantalla de bienvenida personalizada según el usuario autenticado.

La aplicación fue organizada por paquetes para separar las responsabilidades del modelo, la interfaz, los controladores, el acceso a datos y la conexión con la base de datos.

## Funcionalidades principales

- Inicio de sesión con usuario y contraseña.
- Control de acceso según el rol.
- Dashboard con menú y bienvenida personalizada.
- Gestión de clientes.
- Gestión de entrenadores.
- Gestión de planes.
- Gestión de membresías.
- Gestión de rutinas.
- Asignación de rutinas a clientes.
- Consulta de la membresía del cliente autenticado.
- Consulta de la rutina asignada al cliente.
- Reportes administrativos.
- Alertas, confirmaciones y validaciones de datos.
- Ejecución como JAR y como archivo `.exe` para Windows.

## Roles del sistema

### Administrador

Puede acceder a los módulos administrativos, entre ellos:

- Clientes.
- Entrenadores.
- Planes.
- Membresías.
- Rutinas y asignaciones, según la configuración final.
- Reportes.

### Entrenador

Puede acceder a las funciones relacionadas con:

- Consulta de clientes.
- Gestión de rutinas.
- Asignación de rutinas.

### Cliente

Puede consultar:

- Su membresía.
- Su rutina asignada.

## Tecnologías utilizadas

- **Java**
- **JavaFX**
- **FXML**
- **CSS**
- **Scene Builder**
- **PostgreSQL**
- **Neon**
- **JDBC**
- **Maven**
- **Maven Shade Plugin**
- **Launch4j**
- **Git y GitHub**
- **IntelliJ IDEA**

El sistema fue probado con **Java 26.0.1**. El proyecto utiliza dependencias JavaFX administradas mediante Maven.

## Aplicación de Programación Orientada a Objetos

### Encapsulamiento

Los atributos de las clases modelo son privados y se accede a ellos mediante métodos `get` y `set`. Los setters incluyen validaciones para proteger la integridad de los datos.

### Herencia

La clase abstracta `Persona` concentra los atributos y comportamientos comunes. Las clases `Cliente` y `Entrenador` heredan de ella.

### Polimorfismo

La clase abstracta `BienvenidaRol` define métodos comunes para generar el contenido de bienvenida. Las clases correspondientes a Administrador, Entrenador y Cliente sobrescriben dichos métodos para mostrar información distinta según el rol.

### Abstracción

La interfaz genérica `ICRUD<T>` define las operaciones:

```java
guardar(T objeto);
listar();
actualizar(T objeto);
eliminar(int id);
```

Los DAO de los módulos implementan estas operaciones según la entidad que administran.

## Arquitectura del proyecto

El sistema utiliza una organización por capas inspirada en MVC:

- **Model:** entidades y reglas de validación.
- **View:** archivos FXML y estilos CSS.
- **Controller:** eventos, navegación y comunicación con los DAO.
- **DAO:** consultas SQL y operaciones CRUD.
- **DB:** conexión con PostgreSQL.
- **App:** clases de inicio de JavaFX.

## Estructura general

```text
sistema-gimnasio/
├── database/
│   └── scripts y recursos de la base de datos
├── src/
│   └── main/
│       ├── java/
│       │   ├── app/
│       │   ├── controller/
│       │   ├── dao/
│       │   ├── db/
│       │   └── model/
│       └── resources/
│           ├── css/
│           ├── img/
│           └── view/
├── .gitignore
├── config.example.properties
├── launch4j.xml
├── pom.xml
└── README.md
```

## Módulos principales

### Modelos

- `Usuario`
- `Persona`
- `Cliente`
- `Entrenador`
- `Plan`
- `Membresia`
- `Rutina`
- `AsignacionRutina`
- `BienvenidaRol`
- `BienvenidaAdministrador`
- `BienvenidaEntrenador`
- `BienvenidaCliente`

### Acceso a datos

- `ICRUD<T>`
- `UsuarioDAO`
- `ClienteDAO`
- `EntrenadorDAO`
- `PlanDAO`
- `MembresiaDAO`
- `RutinaDAO`
- `AsignacionRutinaDAO`
- `ReporteDAO`

### Controladores

- `LoginController`
- `DashboardController`
- `ClienteController`
- `EntrenadorController`
- `PlanController`
- `MembresiaController`
- `MiMembresiaController`
- `RutinaController`
- `AsignacionRutinaController`
- `MiRutinaController`
- `ReporteController`

## Base de datos

El proyecto utiliza PostgreSQL. Entre las tablas principales se encuentran:

- `usuarios`
- `clientes`
- `entrenadores`
- `planes`
- `membresias`
- `rutinas`
- `asignaciones_rutina`

Se utilizan claves primarias, claves foráneas y restricciones como `UNIQUE`, `CHECK` y `NOT NULL`.

## Configuración de la conexión

La clase `Conexion` intenta obtener la URL de la base de datos desde:

1. La variable de entorno `DATABASE_URL`.
2. El archivo externo `config.properties`.

Crea un archivo `config.properties` en la raíz del proyecto tomando como referencia `config.example.properties`.

Ejemplo general:

```properties
DATABASE_URL=jdbc:postgresql://HOST/BASE_DE_DATOS?sslmode=require&user=USUARIO&password=CONTRASENA
```

> No se debe subir el archivo `config.properties` real al repositorio, ya que contiene credenciales privadas.

## Ejecución desde IntelliJ IDEA

1. Clonar el repositorio.
2. Abrir el proyecto desde IntelliJ IDEA.
3. Configurar un JDK compatible.
4. Recargar las dependencias de Maven.
5. Crear el archivo `config.properties`.
6. Ejecutar la clase `app.Launcher` o `app.Main`.

## Ejecución con Maven

Desde la raíz del proyecto:

```bash
mvn clean package
```

Maven genera un JAR con dependencias dentro de la carpeta `target`.

Para ejecutarlo:

```bash
java -jar target/sistema-gimnasio-1.0.0-all.jar
```

## Generación del ejecutable para Windows

Se utilizó Launch4j para envolver el JAR y generar:

```text
SistemaGimnasio.exe
```

La distribución privada contiene:

```text
sistema-gimnasio-instalado/
├── SistemaGimnasio.exe
├── config.properties
└── runtime/
```

La carpeta `runtime` incluye Java para que la aplicación pueda ejecutarse en Windows sin instalarlo por separado.

## Validaciones implementadas

Entre las validaciones del sistema se encuentran:

- Campos obligatorios.
- Cédula de 10 dígitos.
- Teléfono de 10 dígitos.
- Límites de longitud para nombres y apellidos.
- Correos y usuarios no duplicados.
- Precio mayor que cero.
- Duración del plan mayor que cero.
- Fechas de membresía coherentes.
- Selección obligatoria de registros relacionados.
- Confirmación antes de eliminar.
- Manejo de errores de conexión y SQL.

## Control de versiones

El proyecto fue gestionado con Git y GitHub. Se utilizaron ramas para desarrollar módulos por separado y posteriormente integrar los cambios en la rama `main`.

El repositorio conserva el código fuente y los archivos de configuración reutilizables. Los archivos generados, el runtime y las credenciales privadas se excluyen mediante `.gitignore`.

## Estado del proyecto

Proyecto académico finalizado y funcional.

Se completaron:

- Interfaz gráfica.
- Conexión con PostgreSQL.
- Autenticación.
- Roles.
- Operaciones CRUD.
- Aplicación de los pilares de POO.
- Reportes.
- Validaciones.
- Empaquetado como JAR.
- Generación del ejecutable para Windows.
- Icono personalizado en JavaFX y Launch4j.

## Autores

**Sebastian Toapanta**  
**Erick Patiño**

Proyecto académico de Programación Orientada a Objetos.

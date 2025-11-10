
##  Requisitos Previos


#### Crear la base de datos y cargar datos iniciales

```bash
# Conectarse a MySQL
mysql -u root -p

# Ejecutar el script de inicialización desde MySQL
source /backend/init-database.sql

# O desde la terminal directamente
mysql -u root -p < backend/init-database.sql
```

Esto creará:
- Base de datos: `loteriadb`
- 3 tablas: `sorteo`, `cliente`, `billete`
- Datos de prueba: 3 sorteos, 5 clientes y 30 billetes

#### Configurar credenciales de MySQL

Si tu usuario o contraseña de MySQL son diferentes, edita el archivo:

```
backend/src/main/resources/application.properties
```

```properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 2. Backend

#### Instalar dependencias

```bash
cd backend
./mvnw clean install
```

En Windows usa:
```bash
mvnw.cmd clean install
```

#### Configuración

El backend se ejecuta en `http://localhost:8080` por defecto.

Si necesitas cambiar el puerto, edita `application.properties`:

```properties
server.port=8080
```

### 3. Frontend

#### Instalar dependencias

```bash
cd frontend
npm install
```

#### Configuración del Proxy

El frontend está configurado para hacer peticiones al backend a través de un proxy (`proxy.conf.json`):

```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true
  }
}
```

Si el backend está en otro puerto, actualiza el `target` en este archivo.

##  Ejecución del Proyecto



#### 1. Iniciar el Backend

```bash
cd backend
./mvnw spring-boot:run
```

En Windows:
```bash
mvnw.cmd spring-boot:run
```

El backend estará disponible en: `http://localhost:8080`

#### 2. Iniciar el Frontend (en otra terminal)

```bash
cd frontend
npm start
```

El frontend estará disponible en: `http://localhost:4200`


##  Pruebas

### Backend - Pruebas Unitarias
Se requiere tener ya la base de datos creada
```bash
cd backend
./mvnw test
```

Esto ejecutará todas las pruebas unitarias de los servicios:
- `ClienteServiceTest`
- `BilleteServiceTest`
- `SorteoServiceTest`

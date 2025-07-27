# Funcionalidad de Libros - DaviTeca Android

## Descripción
Se ha implementado una nueva pantalla de libros que simula estanterías virtuales donde se pueden visualizar, agregar y gestionar los libros de la biblioteca personal.

## Características Implementadas

### 📚 Visualización de Estanterías
- **Diseño de estanterías**: Los libros se muestran en un formato de cuadrícula (2 columnas) que simula estanterías reales
- **Tarjetas de libros**: Cada libro se muestra en una tarjeta con:
  - Icono de libro
  - Título del libro
  - Autor
  - Editorial
  - Estado de disponibilidad (✅ Disponible / ❌ No disponible)

### 🗄️ Base de Datos Local
- **SQLite**: Base de datos local integrada en la aplicación
- **Tabla libros**: Estructura con campos:
  - `id` (INTEGER PRIMARY KEY AUTOINCREMENT)
  - `titulo` (TEXT NOT NULL)
  - `autor` (TEXT NOT NULL)
  - `editorial` (TEXT NOT NULL)
  - `disponible` (INTEGER DEFAULT 1)

### 📖 Libros de Ejemplo
La aplicación incluye automáticamente 8 libros de ejemplo:
1. Don Quijote - Miguel de Cervantes
2. Cien años de soledad - Gabriel García Márquez
3. El señor de los anillos - J.R.R. Tolkien
4. 1984 - George Orwell
5. El principito - Antoine de Saint-Exupéry
6. Harry Potter y la piedra filosofal - J.K. Rowling
7. Los miserables - Victor Hugo
8. Orgullo y prejuicio - Jane Austen

### ✨ Funcionalidades Interactivas

#### Agregar Nuevo Libro
- Botón "+ Agregar" en la parte superior
- Diálogo con campos para:
  - Título del libro
  - Autor
  - Editorial
- Validación de campos obligatorios

#### Gestión de Libros
- **Click en libro**: Muestra detalles completos del libro
- **Click largo**: Abre menú de opciones:
  - Cambiar disponibilidad
  - Eliminar libro

#### Navegación
- Botón "← Volver" para regresar al menú principal
- Título "📚 Mi Biblioteca" en el header

## Estructura de Archivos

### Java
- `LibrosActivity.java`: Actividad principal de libros
- `LibrosAdapter.java`: Adaptador para RecyclerView
- `MainActivity.java`: Modificado para navegar a LibrosActivity

### Layouts
- `activity_libros.xml`: Layout principal de la pantalla
- `item_libro.xml`: Layout para cada item de libro

### Drawables
- `ic_libro.xml`: Icono vectorial de libro
- `libro_background.xml`: Fondo para el icono
- `disponibilidad_background.xml`: Fondo para indicador de disponibilidad

### Dependencias Agregadas
- `androidx.recyclerview:recyclerview:1.3.2`
- `androidx.cardview:cardview:1.0.0`

## Cómo Usar

1. **Acceder a la pantalla de libros**:
   - Abrir la aplicación DaviTeca
   - Tocar el botón "Libros" en el menú principal

2. **Ver los libros**:
   - Los libros se muestran automáticamente en formato de estanterías
   - Cada libro muestra título, autor, editorial y estado

3. **Agregar un nuevo libro**:
   - Tocar el botón "+ Agregar"
   - Completar los campos requeridos
   - Tocar "Agregar"

4. **Gestionar libros existentes**:
   - **Click simple**: Ver detalles del libro
   - **Click largo**: Acceder a opciones (cambiar disponibilidad, eliminar)

5. **Volver al menú principal**:
   - Tocar el botón "← Volver"

## Características Técnicas

### Base de Datos
- **Nombre**: `daviteca.db`
- **Versión**: 1
- **Ubicación**: Interna de la aplicación
- **Creación automática**: Se crea al instalar la app

### Interfaz de Usuario
- **Diseño Material**: Siguiendo las guías de Material Design
- **Responsive**: Se adapta a diferentes tamaños de pantalla
- **Accesibilidad**: Elementos táctiles con feedback visual

### Gestión de Estado
- **Actualización en tiempo real**: Los cambios se reflejan inmediatamente
- **Persistencia**: Los datos se mantienen entre sesiones
- **Validación**: Campos obligatorios verificados antes de guardar

## Próximas Mejoras Sugeridas

1. **Búsqueda de libros**: Implementar filtros por título, autor o editorial
2. **Categorías**: Agregar sistema de categorías o géneros
3. **Imágenes de portada**: Permitir agregar imágenes de los libros
4. **Estadísticas**: Mostrar estadísticas de la biblioteca
5. **Exportar/Importar**: Funcionalidad para respaldar la biblioteca
6. **Temas**: Opciones de temas claros/oscuros
7. **Ordenamiento**: Opciones para ordenar por diferentes criterios 
# DaviTeca - Aplicación Android

Esta es una aplicación Android sencilla que muestra "DaviTeca" en el centro de la pantalla.

## Configuración del Proyecto

### Requisitos Previos
- Android Studio (versión recomendada: Android Studio Hedgehog | 2023.1.1 o superior)
- JDK 17 o superior
- Android SDK API 34

### Pasos para Configurar el Proyecto

1. **Abrir el proyecto en Android Studio:**
   - Abre Android Studio
   - Selecciona "Open an existing Android Studio project"
   - Navega hasta la carpeta `DaviTeca-Android` y selecciónala

2. **Sincronizar el proyecto:**
   - Android Studio detectará automáticamente que es un proyecto Gradle
   - Haz clic en "Sync Now" cuando aparezca la notificación
   - O ve a File > Sync Project with Gradle Files

3. **Descargar dependencias:**
   - El proyecto descargará automáticamente todas las dependencias necesarias
   - Esto puede tomar unos minutos la primera vez

4. **Configurar el dispositivo:**
   - Conecta tu dispositivo Android vía USB
   - Habilita las "Opciones de desarrollador" en tu dispositivo
   - Habilita la "Depuración USB"
   - O configura un emulador en Android Studio

### Ejecutar la Aplicación

1. **Seleccionar el dispositivo:**
   - En la barra de herramientas, selecciona tu dispositivo o emulador

2. **Ejecutar la aplicación:**
   - Haz clic en el botón "Run" (triángulo verde) o presiona Shift + F10
   - La aplicación se compilará e instalará automáticamente en tu dispositivo

### Estructura del Proyecto

```
DaviTeca-Android/
├── app/
│   ├── src/main/
│   │   ├── java/com/daviteca/app/
│   │   │   └── MainActivity.java
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   └── themes.xml
│   │   │   └── xml/
│   │   │       ├── backup_rules.xml
│   │   │       └── data_extraction_rules.xml
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
└── gradle/wrapper/
    └── gradle-wrapper.properties
```

### Características de la Aplicación

- **Pantalla principal:** Muestra "DaviTeca" centrado en la pantalla
- **Tema:** Utiliza Material Design con colores púrpura y teal
- **Compatibilidad:** Android API 24+ (Android 7.0 Nougat y superior)
- **Idioma:** Español

### Solución de Problemas

Si encuentras algún problema:

1. **Error de sincronización de Gradle:**
   - Ve a File > Invalidate Caches and Restart
   - O ejecuta "Clean Project" y luego "Rebuild Project"

2. **Error de compilación:**
   - Verifica que tienes JDK 17 instalado
   - Asegúrate de que Android SDK API 34 esté instalado

3. **Dispositivo no detectado:**
   - Verifica que la depuración USB esté habilitada
   - Reinstala los drivers USB si es necesario

### Próximos Pasos

Esta es una aplicación base que puedes expandir agregando:
- Más pantallas y funcionalidades
- Base de datos local
- Integración con APIs
- Mejoras en el diseño y UX

¡Disfruta desarrollando tu aplicación DaviTeca! 
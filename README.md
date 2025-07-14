# ISE5784_8145_5378

## Project Overview

This project is a 3D rendering engine written in Java, developed as part of the ISE5784 course. It simulates realistic scenes by modeling light, geometry, materials, and camera operations. The engine supports the rendering of complex scenes with multiple objects and lighting types, and includes advanced features such as reflections, refractions, transparency, and partial shadows.

## Features

- **Scene Management:** Define and manage 3D scenes containing geometric objects, light sources, and ambient light.
- **Geometric Objects:** Includes support for various geometries such as spheres, planes, polygons, triangles, and tubes.
- **Material System:** Materials can be customized with parameters such as:
  - Specular reflection (`kS`)
  - Diffuse reflection (`kD`)
  - Transparency (`kT`)
  - Reflection (`kR`)
  - Shininess (specular exponent)
- **Lighting:** Supports multiple types of lights (ambient light, point lights, spot lights) for realistic illumination.
- **Camera:** Virtual camera system for setting up view position, orientation, image plane dimensions, and pixel sampling.
- **Rendering:** Computes pixel colors using ray tracing algorithms, handling intersections, shading, and light interactions.
- **Reflection & Refraction:** Advanced ray tracing for partial shadows, transparency, and realistic reflections.
- **Multi-threading:** Utilizes pixel management for efficient rendering using multiple threads.
- **Utility Classes:** Includes classes for mathematical operations (e.g., `Double3`, `Util`) to support core computations.

## Technologies Used

- **Language:** Java
- **Core Libraries:** Custom-built classes for geometry, lighting, scene management, materials, and rendering.
- **Testing:** JUnit for automated tests (see `unittest/renderer/ReflectionRefractionTests.java` for examples).

## Optimization Techniques

- **Material Optimization:** Parameters like specular/diffuse coefficients and shininess allow for fine-tuning the appearance and rendering efficiency.
- **Multi-threaded Rendering:** Rendering tasks are distributed across threads using a pixel manager to improve performance.
- **Accurate Computations:** Utility classes ensure precision in mathematical calculations for rendering.

## Getting Started

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/OfekOded/ISE5784_8145_5378.git
   ```
2. **Build the Project:** Use your preferred Java build system (e.g., Maven or Gradle) or directly compile with `javac`.
3. **Run Tests:** Execute JUnit tests to verify functionality.

## Directory Structure

- `src/` — Source code for engine components:
  - `primitives/` — Math types and material definitions
  - `scene/` — Scene composition
  - `geometries/` — Geometric shapes
  - `lighting/` — Light sources
  - `renderer/` — Camera, rendering logic, pixel management
- `unittest/` — Unit tests for renderer and scene features

## Example Usage

To create a simple scene:
```java
Scene scene = new Scene("Sample Scene")
    .setAmbientLight(new AmbientLight(...))
    .setGeometries(new Geometries(...))
    .setLights(List.of(new PointLight(...), new SpotLight(...)));
Camera camera = new Camera(...);
// Render the scene using camera and renderer classes
```
---

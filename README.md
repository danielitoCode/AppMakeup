## AppMakeup — Core v1
-----
### What is AppMakeup?

AppMakeup is a desktop tool built with Kotlin Multiplatform and Compose Desktop whose purpose is to visually model and generate the base structure of applications in a predictable, controlled, and architecture-first way.

*It is not a smart code generator*
*It does not try to write business logic for you*

AppMakeup focuses on architecture, project modeling, and structural consistency, allowing developers to keep full control over the final codebase.
### Why AppMakeup exists

In real-world projects, a large portion of the cost is not writing code, but:
- defining a clean and scalable architecture
- keeping layers consistent
- creating repeatable project structures
- avoiding early architectural mistakes
- ensuring projects can evolve without rewrites

**AppMakeup exists to solve that problem.**

*It turns architectural decisions into explicit models,
and explicit models into real project structures.*

### Core principles

AppMakeup is built around these principles:

- Architecture first, generation second
- Predictability over excessive flexibility
- Model before code
- Strict separation of layers
- No domain logic in the UI
- No filesystem access from the UI
- Explicit, atomic use cases
- Deterministic persistence

### Current state: Core v1

The project is currently in a Core v1 functional state.
This means:
- the core workflow is complete 
- the main architecture is stable
- the essential layers are closed
- the tool is usable as a real product
- future features can be added without large refactors

### Implemented features
- Welcome screen

**Create new projects:**
- application name
- package name
- workspace location
- Open existing projects
- Persistent recent projects list
- Dark / Light theme toggle
- Visual feedback during project creation
- Clear error dialogs
- Recent projects
- Real persistence on disk (settings.json)
- Cross-platform path normalization
- Duplicate prevention
- Reordering by last access
- Automatic cleanup of invalid projects
- Validation before opening
- Project validation

**Before opening a project, AppMakeup validates:**

- directory existence
- AppMakeup project structure
- presence of project.amk.json
- JSON validity
- project version compatibility
- Errors are reported with specific and actionable messages.
- Project versioning (future-proof)
- Each project includes a version field:
- Projects without a version remain valid (backward compatibility)

**The system detects:**
- newer, unsupported versions
- older, migratable versions

**The architecture is prepared for:**

- project migrations
- format evolution
- compatibility control (*No automatic migrations are performed in Core v1 by design.*)
- Modeling screen (core of the tool)
- Once a project is opened, AppMakeup allows:
- defining features
- modeling domain entities
- adding entity properties
- tracking dirty state
- validating the model before generation
- generating the base project structure
- All modeling happens at the domain level, not at the code level.

### Project architecture

AppMakeup follows Clean Architecture, adapted for a modeling tool.

#### Main layers
### *Domain* 
- modeling entities
- validation rules
- use cases
### *Data*
- concrete repositories
- JSON persistence
- filesystem access (Okio)
### *Presentation*
- Compose Desktop UI
- ViewModels
- UI state
### *Infrastructure*
- dependency injection (Koin)
- application settings
- configuration

### Main technologies
- *Kotlin Multiplatform*
- *Compose Multiplatform (Desktop-focused)*
- *Koin (Dependency Injection)*
- *kotlinx.serialization*
- *Okio (multiplatform filesystem)*
- *cache4k (prepared for future optimization)*

### What AppMakeup intentionally does NOT do

To keep a healthy core, AppMakeup v1 does not:

- run generated applications
- compile generated code
- generate business logic
- perform network requests
- auto-migrate projects
- generate “intelligent” code
- These decisions are intentional and part of the design.

### Core v1 scope

AppMakeup Core v1 is considered complete when:
- project creation and opening work reliably
- persistence is stable
- validations are explicit
- modeling is functional
- structure generation is predictable
- architecture is extensible

**That point has been reached.**

*For contributors and forks*

This repository represents a stable Core v1. If you fork this project, you can safely:

-> build new generators on top of the model

-> add migrations and version upgrades

-> extend the modeling capabilities

-> create new templates or exporters

-> improve the UI/UX

**The core is designed to grow without being rewritten.**

### How run this code?

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### Build and Run Web Application

To build and run the development version of the web app, use the run configuration from the run widget
in your IDE's toolbar or run it directly from the terminal:
- for the Wasm target (faster, modern browsers):
  - on macOS/Linux
    ```shell
    ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
    ```
  - on Windows
    ```shell
    .\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
    ```
- for the JS target (slower, supports older browsers):
  - on macOS/Linux
    ```shell
    ./gradlew :composeApp:jsBrowserDevelopmentRun
    ```
  - on Windows
    ```shell
    .\gradlew.bat :composeApp:jsBrowserDevelopmentRun
    ```

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
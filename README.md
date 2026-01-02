# AppMakeup — Core V2

---

## 🚀 What is AppMakeup?

**AppMakeup** is a desktop application built with **Kotlin Multiplatform** and **Compose Desktop** whose goal is to **visually model application architecture and generate deterministic project structures** following Clean Architecture principles.

AppMakeup is intentionally **not** an AI tool, nor a "smart" code generator.

It does **not**:
- guess business logic
- generate application behavior
- run or compile generated apps

Instead, AppMakeup focuses on **architecture as a first-class concept**.

> You design the architecture explicitly. AppMakeup materializes it safely.

---

## 🎯 Why AppMakeup exists

In real projects, most long-term problems come from:

- unclear architecture decisions
- inconsistent project structures
- premature coupling between layers
- uncontrolled growth of modules

AppMakeup exists to solve those problems **before code is written**.

It allows developers to:
- model architecture visually
- enforce structure without loss of control
- evolve projects without rewrites
- keep Clean Architecture explicit and verifiable

---

## 🧠 Core philosophy

AppMakeup is built around these principles:

- Architecture first, code second
- Structure before implementation
- Predictability over magic
- Deterministic generation
- Strict separation of layers
- No filesystem access from UI
- No domain logic in presentation
- Explicit, atomic use cases

---

## ✅ Current State — **Core V2 (Stable)**

AppMakeup is currently in **Core V2**, which is considered **stable and closed**.

**Core V2 is about structure, not code.**

This core establishes a solid foundation that future versions build upon without breaking.

---

## ✨ What Core V2 Includes

### 🧭 Project lifecycle

- Create new projects
  - application name
  - package name
  - workspace location
- Open existing projects
- Persist recent projects on disk
- Normalize and deduplicate paths
- Remove invalid projects automatically

### 🔍 Project validation

Before opening a project, AppMakeup validates:

- directory existence
- AppMakeup project structure
- presence of `project.amk.json`
- JSON integrity
- project version compatibility

All validation errors are **explicit and user-friendly**.

---

## 🧩 Architecture Modeling

Inside a project, Core V2 supports:

- Feature definition
- Domain entity modeling
- Entity property modeling
- Dirty-state tracking
- Model validation before generation
- Deterministic structure generation

All modeling happens at the **domain level**, never at the code level.

---

## 🏗️ Project Architecture (Internal)

AppMakeup follows **Clean Architecture**, adapted for a modeling-first tool.

### Main layers

**Domain**
- modeling entities
- validation rules
- use cases

**Data**
- repositories
- JSON persistence
- filesystem access (Okio)

**Templates & Generation**
- project templates
- structure generators
- filesystem writers

**Presentation**
- Compose Desktop UI
- ViewModels
- UI state management

**Infrastructure**
- dependency injection (Koin)
- settings persistence
- application configuration

---

## 🧪 Testing & Quality

Core V2 has strong test coverage:

- Domain logic is fully unit tested
- Structure generation is deterministic and testable
- Filesystem operations are tested using fake filesystems
- Tests validate **structure**, not code

Code style consistency is enforced using **ktlint**.

---

## 🚫 What Core V2 Intentionally Does NOT Do

To keep the core clean and extensible, Core V2 does **not**:

- generate Kotlin/Java code
- create ViewModels or Screens automatically
- run or compile generated projects
- perform automatic migrations
- provide undo/redo
- act as an IDE replacement

These are **deliberate design decisions**.

---

## 🗺️ Roadmap

### 🔜 Core V3 — Code Generation

- Code generation layer
- Android Clean Architecture generator
- `.kt` file generation
- Separation between structure and code
- Pluggable code generators

### 🔮 Core V4 — Advanced Modeling

- Undo / Redo (Memento pattern)
- Plugin system
- Multi-platform generators
- Advanced templates
- Migration tooling

---

## 🖥️ Running the project

### Desktop (JVM)

```bash
./gradlew :composeApp:run
```

### Windows installer (MSI)

```bash
./gradlew packageMsi
```

---

## 🤝 For contributors & forks

This repository represents a **stable Core V2**.

You can safely:

- build new code generators
- add project migrations
- extend templates
- improve UI/UX
- target new platforms

> Core V2 is designed to grow — not to be rewritten.


# AppMakeup — Core V4 (Closed)

## 🚀 What is AppMakeup?

**AppMakeup** is a desktop application built with **Kotlin Multiplatform** and **Compose Desktop** that allows you to **model application architecture visually** and **generate deterministic project structures and code** following Clean Architecture principles.

AppMakeup is **not an AI tool**.

It does **not**:
- infer business logic
- guess architecture decisions
- generate behavior automatically

Instead, AppMakeup is a **deterministic architecture compiler**.

> You declare architecture. AppMakeup materializes it safely.

---

## 🧠 Core Philosophy (Unchanged)

- Architecture first, code second
- Structure before implementation
- Predictability over magic
- Deterministic generation
- Explicit contracts and layers
- No filesystem access from UI
- No domain logic in presentation
- Atomic, testable use cases

---

## ✅ Current State — **Core V4 (Closed & Stable)**

Core V4 is now **feature-complete and closed**.

It represents the **first full generation-capable core**, where AppMakeup moves from *structure modeling* to **real, validated code generation** while preserving strict architectural guarantees.

Core V4 builds on Core V2 and Core V3 concepts but introduces a **generation pipeline**, **planning stage**, and **dry-run previews**.

---

## ✨ What Core V4 Includes

### 🧭 Project Lifecycle

- Create new projects
- Open existing projects
- Persist and validate projects on disk
- Versioned project format
- Recent projects registry

---

### 🧩 Architecture Modeling

- Features
- Domain entities
- Entity properties
- Identifier enforcement
- Layer selection per feature
- Validation before generation

Invalid states are **prevented at editor level**.

---

### 🧪 Generation Pipeline (Core V4)

Core V4 introduces a **5-stage deterministic pipeline**:

1. **ValidationStage**
   - Entity rules
   - Feature rules
   - Architecture constraints

2. **PlanningStage**
   - Decides what layers will be generated
   - Domain / Data / Repositories / Mappers
   - Fully testable and previewable

3. **GenerationStage**
   - Layer generators
   - Templates
   - No filesystem access

4. **WritingStage**
   - Real filesystem writer
   - Dry-run writer (preview mode)

5. **ReportingStage**
   - CLI / Table / JSON output

---

### 🧪 Dry-Run Mode

Core V4 supports **true dry-run execution**:

- No files are written
- Generated artifacts are collected
- Output paths are simulated
- Safe to run repeatedly

Used for:
- UI preview
- Tests
- Validation before export

---

### 🧩 Generation Plan Preview (UI)

Before exporting, users can see:

- Which layers will be generated
- Whether repositories/mappers apply
- Why a layer is skipped
- Validation errors per feature

This makes generation **explainable**, not magical.

---

### 🧾 Files Preview (Dry-Run Visual)

The UI shows:
- Exact files that would be generated
- Relative paths
- Per-feature grouping

Nothing is written unless explicitly exported.

---

### 🏗️ Internal Architecture (Clean Architecture)

**Domain**
- Core models
- Validators
- Generation pipeline
- Planning logic

**Application**
- Use cases
- Intent orchestration

**Infrastructure**
- Filesystem
- Exporters
- Pipelines wiring (Koin)

**Presentation**
- Compose Desktop UI
- ViewModels
- UI state only

---

## 🧪 Testing & Quality

Core V4 is heavily tested:

- Pipeline unit tests
- Dry-run generation tests
- Failure-path tests
- Deterministic artifact assertions

Generation is **100% testable without filesystem**.

---

## 🚫 What Core V4 Does NOT Do

By design, Core V4 does **not**:

- generate UI screens
- generate ViewModels
- guess repository contracts
- auto-migrate projects
- act as an IDE
- include undo/redo (yet)

---

## 🔮 What Comes Next — **Core V5 (Planned)**

Core V5 will focus on **advanced modeling and extensibility**:

### Planned features

- RepositoryContract editor (visual CRUD modeling)
- Explicit Mapper contracts
- Multi-platform generators (Android / KMP / Backend)
- Plugin system
- Template customization
- Undo / Redo (Memento pattern)
- Project migrations
- Export profiles

Core V5 will **not break Core V4 projects**.

---

## 🖥️ Running the project

### Desktop (JVM)

```bash
./gradlew :composeApp:run
```

---

## 🤝 Final Notes

Core V4 marks a **major milestone**:

- Architecture is explicit
- Generation is deterministic
- Previews are safe
- Errors are explainable

> AppMakeup is no longer a generator.  
> It is an **architecture compiler**.

# AppMakeup — Core V4 (Closed)

**AppMakeup** is a Kotlin Multiplatform desktop application that lets teams **model architecture visually** and **generate deterministic project structures and code** aligned with Clean Architecture.

> **Not an AI tool.** AppMakeup does not infer business logic or make architectural decisions.  
> It is a **deterministic architecture compiler**: you declare the architecture, AppMakeup materializes it safely.

---

## ✨ Highlights

- Architecture-first workflow
- Deterministic generation (no magic)
- Strict validation before export
- Dry-run previews of the generation plan and files
- Clean Architecture separation (Domain / Application / Infrastructure / Presentation)

---

## 🧠 Core Philosophy

- Architecture first, code second
- Structure before implementation
- Predictability over magic
- Explicit contracts and layers
- No filesystem access from UI
- No domain logic in presentation
- Atomic, testable use cases

---

## ✅ Current State — Core V4 (Closed & Stable)

Core V4 is **feature-complete and closed**.  
It delivers the **first full generation-capable core**, with:

- A deterministic pipeline
- A planning stage
- True dry-run previews

---

## 🧭 What Core V4 Includes

### Project Lifecycle
- Create new projects
- Open existing projects
- Persist and validate projects on disk
- Versioned project format
- Recent projects registry

### Architecture Modeling
- Features and layers
- Domain entities and properties
- Identifier enforcement
- Validation before generation  
  *Invalid states are prevented at the editor level.*

---

## 🧪 Generation Pipeline (5 Stages)

1. **ValidationStage**  
   Entity rules · Feature rules · Architecture constraints
2. **PlanningStage**  
   Determines which layers apply (Domain / Data / Repos / Mappers)
3. **GenerationStage**  
   Layer generators + templates (no filesystem access)
4. **WritingStage**  
   Real writer + dry-run writer
5. **ReportingStage**  
   CLI / Table / JSON output

---

## 🔎 Preview & Dry-Run

### Dry-Run Mode
- No files written
- Artifacts collected in memory
- Output paths simulated
- Safe to run repeatedly

### Generation Plan Preview (UI)
- Which layers will be generated
- Whether repositories/mappers apply
- Why a layer is skipped
- Per-feature validation errors

### Files Preview
- Exact files that would be generated
- Relative paths
- Per-feature grouping

---

## 🏗️ Internal Architecture (Clean Architecture)

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
- Pipeline wiring (Koin)

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

## 🚫 Out of Scope (by design)

Core V4 does **not**:
- Generate UI screens or ViewModels
- Guess repository contracts
- Auto-migrate projects
- Act as an IDE
- Include undo/redo (yet)

---

## 🔮 What’s Next — Core V5 (Planned)

- RepositoryContract editor (visual CRUD modeling)
- Explicit mapper contracts
- Multi-platform generators (Android / KMP / Backend)
- Plugin system
- Template customization
- Undo / Redo (Memento pattern)
- Project migrations
- Export profiles

> Core V5 will **not break Core V4 projects**.

---

## 🖥️ Running the Project

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

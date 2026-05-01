# Spring Boot 4.0.6 Migration — Execution Plan

## Goal
Migrate the OpenCode Java SDK multi-module Maven project from Spring Boot 3.5.x to Spring Boot 4.0.6, addressing all breaking changes from the Spring Boot 4.0 Migration Guide (starter renames, Jakarta EE 11, Jackson 2 compat, modular test starters, deprecated API removals), verify compilation and example startup, and update all AGENTS.md documentation.

## Scope
- In scope:
  - Parent POM — Spring Boot version bump, dependency version updates
  - Spring Boot Starter module — starter rename, Jackson 2 compat dependency
  - Spring Boot Example module — parent version bump, starter renames, test migration, TestRestTemplate
  - SDK module — Jakarta EE 11 annotation API, OpenAPI Generator config
  - Plain Java Example — version alignment if needed
  - Full project compilation verification
  - Spring Boot Example startup verification
  - All AGENTS.md files updated
- Out of scope:
  - Jackson 2 → Jackson 3 migration (use `spring-boot-jackson2` compat module; full Jackson 3 migration deferred)
  - Migrating to modular test starters (use `spring-boot-starter-test-classic` as intermediate)
  - Integration testing (requires Docker/Testcontainers)
  - New features or business logic refactoring

## Specification

### Requirements
- R1. All POM files reference Spring Boot 4.0.6 and compatible dependency versions
- R2. `spring-boot-starter-web` renamed to `spring-boot-starter-webmvc` per migration guide
- R3. Jakarta EE 11 baseline: `jakarta.annotation-api` updated to 3.0.0
- R4. OpenAPI Generator config updated for Jakarta EE 11 compatibility
- R5. Spring Boot Example parent updated to `spring-boot-starter-parent:4.0.6`
- R6. Test infrastructure: `spring-boot-starter-test` → `spring-boot-starter-test-classic`; `TestRestTemplate` package + `@AutoConfigureTestRestTemplate`
- R7. Jackson 2 compatibility: `spring-boot-jackson2` module added where needed
- R8. No deprecated Spring Boot 3.x API usage remains
- R9. Full project compiles successfully (`mvn clean compile`)
- R10. Spring Boot Example application starts successfully
- R11. All AGENTS.md files updated to reflect Spring Boot 4.0.6, new starter names, updated versions

### Non-Goals
- NG1. Migrating SDK internals from Jackson 2 to Jackson 3 (deferred to future task)
- NG2. Migrating to individual modular test starters (using `spring-boot-starter-test-classic` as intermediate)
- NG3. Integration or end-to-end testing (requires running Docker)

### Acceptance Scenarios
- S1. `mvn clean compile` succeeds with zero errors across all modules
- S2. Spring Boot Example application starts (Spring Boot banner appears, no startup errors)
- S3. No `spring-boot-starter-web` references remain — all replaced with `spring-boot-starter-webmvc`
- S4. All AGENTS.md files reference Spring Boot 4.0.6 and updated dependency table

## How to Use This Plan
1. Open the next unchecked task from the checklist below.
2. Read the corresponding task file completely.
3. Use the suggested agent and the provided inputs for that task.
4. Execute only the next unchecked task unless the user changes the plan.
5. Verify all acceptance criteria, including the git commit requirement.
6. Update the checklist after the task is completed.
7. If the plan becomes stale, update the relevant files before continuing.

## Task Checklist
- [x] `task-01-pom-dependency-updates.md`: POM Version & Dependency Updates — Suggested agent: Code — Covers: R1, R2, R3, R5, R6, R7, R8, S3
- [x] `task-02-source-config-adaptations.md`: Source Code & Config Adaptations — Suggested agent: Code — Covers: R4, R6, R8, S1
- [x] `task-03-compilation-verification.md`: Compilation Verification — Suggested agent: Test — Covers: R1-R8, S1, S3
- [ ] `task-04-spring-boot-example-startup.md`: Spring Boot Example Startup — Suggested agent: Test — Covers: R1, R2, R5, R7, S2
- [x] `task-05-update-agents-md.md`: Update AGENTS.md Files — Suggested agent: Code — Covers: R11, S4

## Shared Context

### Key Decisions
- **Jackson 2 compat**: Use `spring-boot-jackson2` module to keep SDK's Jackson 2 dependencies working under Spring Boot 4.0. Full Jackson 3 migration deferred.
- **Test starters**: Use `spring-boot-starter-test-classic` as intermediate step. Individual modular test starters migration deferred.
- **Jakarta EE 11**: `jakarta.annotation-api` upgraded to 3.0.0. OpenAPI Generator should remain compatible with `useJakartaEe=true`.
- **Migration strategy**: Direct migration (not two-step via classic starters for main code), since the project is small and the affected surface is manageable.

### Constraints
- Java 21 baseline (unchanged, compatible with SB4)
- No Lombok in SDK or Starter modules
- OpenAPI Generator v7.21.0 preferred (update only if incompatible)
- All auto-generated code must not be manually edited (only generator config changes)

### Risks / Open Questions
- **Spring Boot 4.0.6 availability**: Must verify 4.0.6 exists in Maven Central. If not yet released, use latest available 4.0.x.
- **OpenAPI Generator Jakarta EE 11 compatibility**: v7.21.0 may not generate Jakarta EE 11 compatible code. May need version bump.
- **Jackson 2 compat module stability**: `spring-boot-jackson2` is deprecated and will be removed in a future release. This is an intentional stopgap.
- **TestContainers compatibility**: TestContainers 1.20.4 may need update for SB4. Check compatibility.
- **Lombok compatibility**: Lombok 1.18.46 should work, but needs verification at compile time.

## Research Artifacts
- Spring Boot 4.0 Migration Guide fetched from: https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide
  - Key sections: Starter renames, Jakarta EE 11 baseline, Jackson 3/Jackson 2 compat, modular test starters, TestRestTemplate changes, @MockBean/@SpyBean removal, JSpecify nullability

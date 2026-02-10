# MediChat Android (Gradle + Compose)

This repository now contains a starter Android app built with **Gradle (Kotlin DSL)** and **Jetpack Compose**.

## What the app does
- Takes symptom input from the user.
- Calls an LLM chat-completions API to generate:
  - likely diagnosis
  - suggested treatment
  - recommendation
- Stores each result in local **Room** database as medical history.
- Lets users view saved history in a second screen.

## Configure API settings
`app/build.gradle.kts` reads these optional Gradle properties:

- `LLM_API_BASE_URL` (default: `https://api.openai.com/`)
- `LLM_MODEL_NAME` (default: `gpt-4o-mini`)

You can set them in `~/.gradle/gradle.properties` or project `gradle.properties`.

For API auth, the app reads `LLM_API_KEY` from environment variables via interceptor.

## Run
1. Open in Android Studio (Hedgehog+ recommended).
2. Let Gradle sync.
3. Run on emulator/device (API 26+).

## Important safety note
This project is a technical starter and **not medical advice**. Add legal disclaimers, age/region compliance checks, escalation logic, and clinician review workflows before production use.

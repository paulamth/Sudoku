# Sudoku für Android

Eine Sudoku-App für Android, entstanden im Wintersemester 2022/23 als Projektarbeit im Modul Programmierung 3 an der Hochschule Osnabrück. Der Schwerpunkt lag auf der Architektur: Nach einem Vergleich von MVC, MVP und MVVM ist die App nach dem Muster **MVVM** (Model–View–ViewModel) aufgebaut.

<img src="docs/spielfeld.png" alt="Spielfeld 9×9 mit Notizen" width="280">

## Funktionen

- Spielfelder in 4×4 und 9×9, vier Schwierigkeitsstufen
- Zahlen eintragen, Notizen setzen und löschen
- Drei Hinweise pro Spiel (jeder Hinweis kostet 10 Sekunden), nach drei Fehlern ist das Spiel verloren
- Spielzeit mit Pause (während der Pause ist das Feld verdeckt), Bestzeit je Schwierigkeit und Rangliste aller gespielten Runden

## Aufbau

```
app/src/main/java/com/example/sudoku/
├── view/        Activities und die eigene View für das Spielfeld
├── viewModel/   SpielfeldViewModel, DatabaseViewModel
└── model/
    ├── game/    Spiellogik (SudokuSpiel, Zelle) und Löser
    ├── data/    Room-Datenbank: Entity, DAO, AppDatabase
    └── SpielRepository.java
```

- **View:** Die Activities zeigen nur an und leiten Eingaben an das ViewModel weiter. Das Spielfeld ist eine eigene `View`, die Gitter, Markierungen und Notizen auf ein `Canvas` zeichnet.
- **ViewModel:** Hält den Spielzustand. Änderungen (Spielfeld, ausgewählte Zelle, Fehler, Hinweise, gelöst) werden über `LiveData` an die Oberfläche gemeldet.
- **Model:** Ein Backtracking-Löser füllt ein leeres Feld zufällig, danach werden je nach Schwierigkeit Zahlen aufgedeckt. Abgeschlossene Spiele werden über ein Repository in einer lokalen Room-Datenbank gespeichert; die Datenbank ist als Singleton angelegt, das Speichern läuft in einem Hintergrund-Thread.
- **Navigation:** Die Activities sind über explizite Intents verbunden; Feldgröße und Schwierigkeit werden beim Wechsel zum Spielfeld mitgegeben, die Zahlen-Buttons entstehen erst zur Laufzeit.

## Technik

Java · Android SDK (minSdk 23, targetSdk 32) · AndroidX ViewModel und LiveData · Room · Material Components · Gradle · JUnit

## Starten

1. Repository klonen und den Ordner in **Android Studio** öffnen.
2. Gradle synchronisieren lassen.
3. Auf einem Emulator oder Android-Gerät (ab Android 6.0) starten.

Unit-Tests für den Löser liegen unter `app/src/test`.

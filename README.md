# Decision Support System

Webbasierte Anwendung zur Bewertung und Entscheidungsunterstützung bei der Wiederverwendung von Batterien aus Elektrofahrzeugen.

## Projektübersicht

Das System unterstützt bei der Bewertung gebrauchter Batterien aus Elektrofahrzeugen und hilft dabei einzuschätzen, ob sich eine Batterie für eine weitere Verwendung eignet.

Dazu werden verschiedene technische, wirtschaftliche, ökologische und regulatorische Daten erfasst und anhand definierter Kriterien ausgewertet.

Auf Grundlage dieser Bewertung erstellt das System eine automatische Empfehlung zur weiteren Verwendung der Batterie.

## Funktionen

* Verwaltung von Battery-Passport-Daten
* Anlegen, Bearbeiten und Löschen von Batterien
* Erfassung technischer und leistungsbezogener Daten
* Erfassung von Daten zur Kreislauffähigkeit
* Decision Support zur Bewertung der Batterien
* Bewertung anhand verschiedener Kriterien
* Gewichtung der einzelnen Bewertungskategorien
* Automatische Empfehlung auf Basis definierter Regeln
* REST API zur Kommunikation zwischen Frontend und Backend
* Deutsch- und Englischsprachige Benutzeroberfläche
* Dashboard zur Übersicht der vorhandenen Batterien

## Decision Support

Das Decision-Support-System bewertet eine Batterie anhand von Kriterien aus fünf Bereichen:

* **Technisch**
* **Wirtschaftlich**
* **Ökologisch**
* **Regulatorisch**
* **Anwendungseignung**

Dabei werden sowohl verpflichtende als auch optionale Kriterien berücksichtigt.

Zusätzlich gibt es bestimmte Ausschlussbedingungen. Kritische Werte können beispielsweise dazu führen, dass eine positive Empfehlung unabhängig von der weiteren Bewertung nicht möglich ist.

Aus den einzelnen Kriterien und deren Gewichtung wird anschließend eine Gesamtbewertung berechnet. Auf dieser Grundlage erstellt das System automatisch eine Empfehlung.

## Verwendete Technologien

| Bereich            | Technologie           |
| ------------------ | --------------------- |
| Backend            | Java / Jakarta EE 10  |
| REST API           | JAX-RS                |
| Persistenz         | JPA / EclipseLink     |
| Datenbank          | Microsoft SQL Server  |
| Frontend           | HTML, CSS, JavaScript |
| Build-System       | Maven                 |
| Application Server | WildFly               |
| Versionsverwaltung | Git / GitHub          |

## Systemaufbau

```text
Frontend
   │
   │ REST / JSON
   ▼
Jakarta-EE-Backend
   │
   ├── REST-Ressourcen
   ├── Geschäftslogik
   ├── DTOs
   └── JPA / EclipseLink
          │
          ▼
   Microsoft SQL Server
```

Das Frontend kommuniziert über eine REST API mit dem Backend. Das Backend übernimmt die Geschäftslogik sowie den Zugriff auf die Datenbank.

## Installation und Start

### Voraussetzungen

* Java
* Maven
* WildFly
* Microsoft SQL Server
* Git

### Einrichtung

1. Repository klonen
2. Datenbankverbindung konfigurieren
3. Benötigte Datenbankstruktur erstellen
4. Projekt mit Maven bauen
5. WAR-Datei auf WildFly bereitstellen
6. Anwendung im Browser öffnen

Die genaue Konfiguration kann je nach lokaler Umgebung abweichen.

## Mein Beitrag

Im Rahmen meines Abschlussprojekts habe ich die Anwendung entwickelt und dabei unter anderem das Frontend, die REST API, die Datenbankanbindung und die Entscheidungslogik umgesetzt.

Ein besonderer Schwerpunkt lag auf der Umsetzung des Decision-Support-Systems. Hierfür wurden verschiedene Bewertungskriterien definiert, gewichtet und zu einer automatisierten Empfehlung zusammengeführt.

## Projektkontext

Das Projekt entstand im Zusammenhang mit dem **RENOVATE EU-Projekt** und beschäftigt sich mit der Wiederverwendung und Kreislaufwirtschaft von Batterien aus Elektrofahrzeugen.

## Hinweis

Dieses Repository dient der Darstellung meines Abschlussprojekts und meiner technischen Arbeit im Rahmen meiner Ausbildung.

Projektbezogene Daten, Konfigurationen oder Komponenten können aus Datenschutz-, Sicherheits- oder Lizenzgründen verändert oder nicht vollständig enthalten sein.

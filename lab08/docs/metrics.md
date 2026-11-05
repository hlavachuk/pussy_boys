# Завдання 5. Метрики (CBO / LCOM4)

Правила: java.lang / System / примітиви не враховуються; `Scanner` (java.util) враховано як інфраструктурний тип.
Тривіальні геттери при LCOM4 виключено (дозволено методичкою).
**Перед здачею перерахуйте вручну або плагіном (напр. MetricsReloaded / CK) і підставте свої значення.**

| Клас | CBO до | CBO після | LCOM4 до | LCOM4 після |
|---|---|---|---|---|
| LegacyClinicService (усе в одному) | 4 (Scanner, Patient, Doctor, Appointment) | — (розбито) | 1 формально, 6 обов'язків | — |
| ScheduleAppointmentController | — | 4 (Factory, Repository, Command, Appointment) | — | 1 |
| ConsoleClient | — | 6 (не входить до норми) | — | 1 |
| Appointment | 2 (Patient, Doctor) | 2 (Patient, Doctor) | без поведінки | 1 |
| Patient | 0 | 3 (CoveragePolicy, InsuranceCoverage, NoCoverage) | без поведінки | 1 |
| Doctor | 0 | 2 (FeePolicy, FeePolicies) | без поведінки | 1 |
| AppointmentFactory | — | 4 (Command, Appointment, Patient, Doctor) | — | 1 |

## Instability (I = Ce / (Ca + Ce))
* CLI (`Task3.cli`): Ca = 0, Ce > 0 → I = 1.
* Домен (`Task2`): залежить лише від `Task4` (інтерфейси стратегій), від нього залежать application та CLI → I близько 0.
* `Task4` (стратегії): Ce = 0 → I = 0.
Залежності йдуть від нестабільного (CLI) до стабільного (домен) — Stable Dependencies дотримано.

## Висновки
Формальний CBO legacy-класу невисокий лише тому, що весь код лежить в одному методі. Реальна проблема — шість
обов'язків в одному класі. Після рефакторингу кожен клас має одну відповідальність (LCOM4 = 1), CBO контролера та
сутностей ≤ 4, а правила цін і страхування винесені за інтерфейси.

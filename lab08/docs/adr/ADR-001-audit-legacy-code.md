# ADR-001: Аудит legacy-коду клініки та вибір напрямку рефакторингу

## Статус
Прийнято (Accepted)

## Контекст
Клас `LegacyClinicService.scheduleAppointmentConsole()` в одному методі: читає консоль, створює
`Patient`/`Doctor`/`Appointment`, рахує вартість і покриття страховки, формує квитанцію, "зберігає" запис і "надсилає SMS".

Виявлені проблеми:
* **Anemic Domain Model** — `Patient`, `Doctor`, `Appointment` мають лише публічні поля, без поведінки; стан заповнюється ззовні покроково (порушення інкапсуляції).
* **Bloated Controller / Monolithic Main** — UI, бізнес-логіка, збереження й сповіщення в одному методі (низька згуртованість).
* **Процедурні розгалуження** — `if-else` за рядком категорії лікаря (`"Professor"`, `"Specialist"`), магічні числа `2.0`, `1.4`, `0.80`.
* **Навігаційний код** — `appointment.doctor.baseConsultationFee`, `appointment.patient.hasInsurance` (порушення Закону Деметри).
* **Жорстка залежність від деталей** — збереження та SMS прямо в тілі методу (немає точок підміни).

## QOC
* **Question:** Хто відповідає за розрахунок вартості консультації та покриття страховки?
* **Options:**
  * A: залишити у `LegacyClinicService`;
  * B: покласти на `Doctor` (ставка + категорія) та `Patient` (страховка), а `Appointment` складає підсумок;
  * C: окремий `PriceCalculator` з усіма правилами.
* **Criteria:**
  * Information Expert — ставку знає `Doctor`, страховку знає `Patient`;
  * Low Coupling — сервіс не повинен знати про поля сутностей;
  * High Cohesion — не створювати ще один "бог-клас" калькулятора.
* **Conclusion:** Option B.

## Рішення
Рефакторинг у п'ять кроків: Information Expert + Creator (ADR-002), Controller + Indirection + Pure Fabrication (ADR-003),
Polymorphism + Protected Variations (ADR-004), перевірка метриками CBO / LCOM4 (`docs/metrics.md`).

## Наслідки
* **Позитивні:** сутності самі гарантують валідність і рахують свої дані; UI не містить логіки; правила змінюються точково.
* **Негативні / Компроміси:** більше класів і файлів; `Appointment` залежить від `Patient` і `Doctor`.

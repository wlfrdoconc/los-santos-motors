# GTA: Los Santos Motors — Maven Starter

**Student project | Java 17+ | Console application | Workshop Part 2**

This is a **partially implemented starter**, not a completed solution. Its working vehicle showroom, VIN search, and inventory add/remove options let you focus on the new sales and leasing missions. If you already completed Dealership Part 1, you may instead bring your own inventory classes and adapt the sample CSV data.

## Open in IntelliJ IDEA

1. Install JDK **17 or newer** and IntelliJ IDEA.
2. Extract this ZIP and open the folder that contains `pom.xml`.
3. If IntelliJ asks to import/sync Maven, select **Load Maven Project**.
4. Set the project SDK to JDK 17+.
5. Run `com.losantos.Main` from IntelliJ. **Set working directory to the project root** (containing `pom.xml`).
6. Select `1` to see the showroom, or `2` to search for VIN `LS1001`.

Alternative from the project folder, if Maven is installed:

```sh
mvn compile
java -cp target/classes com.losantos.Main
```

No third-party libraries or internet-connected APIs are required by the Java application.

## What works already?

- Vehicle model, inventory file reader/writer, showroom, VIN search, add/remove vehicles.
- Ten fictional GTA vehicle entries in `data/inventory.csv`.
- Menu option 5 exists, but **does not process a transaction yet**.

## Your missions

1. **Contracts / OOP:** `Contract` is an abstract base class; implement pricing in `SalesContract` and `LeaseContract`. Both compile but throw `UnsupportedOperationException` until you solve them.
2. **Sales:** 5% tax, $100 recording fee, $295/$495 processing fee; optional financing at 5.25%/24 months under $10k, or 4.25%/48 months for vehicles $10k+.
3. **Leasing:** 50% residual, 7% fee; 4%/36 months; cars must be at most three model years old.
4. **Persistence:** implement `ContractFileManager.saveContract(Contract)`, using `instanceof` and **APPEND**, not overwrite.
5. **Menu:** implement `UserInterface.sellOrLeaseVehicle()` to ask for VIN, customer info, sale/lease, financing, calculate results, save a contract, and remove the vehicle from inventory only after successfully saving the contract.

### Suggested calculation

Monthly payment: `principal * monthlyRate / (1 - Math.pow(1 + monthlyRate, -months))`, where `monthlyRate` is annual interest rate / 12. Round **only for display/export**.

### Sample inventory structure

`VIN|YEAR|MAKE|MODEL|VEHICLE_TYPE|COLOR|ODOMETER|PRICE`

The pipe-separated `.csv` examples intentionally use `|` instead of comma. Your contracts should use the same delimiter but may have different fields for sale versus lease (see `references/example_contracts.txt`).

## Quick manual test

- Browse showroom: ten records initially.
- Search `LS1001`: Karin Sultan, $8,000.
- Wrong VIN: appropriate not-found message.
- After implementing: try a 2022 car for lease in 2026 (reject); 2025 (accept).
- After implementing: perform a sale, restart, ensure it is in `contracts.csv` and removed from `inventory.csv`.

Refer to `references/test_scenarios.md` and `references/pricing_reference.csv` for further checks. The pricing reference is for self-checking after completing the code; the lease numbers listed there only apply when a vehicle passes the age rule.

## Important notes for students

- The project is deliberately **not** a full solution.
- Your task is to implement the TODO sections and explain your design.
- This console workshop does not use a game engine, game assets, or an official GTA API.
- The app edits its local data files. Make a copy of `data/inventory.csv` to reset the showroom for testing.
- In a real production system, updating a contract and inventory requires transaction safety; this exercise uses simple files and is for learning purposes.

## Deliverables

Push your repository to GitHub with a README and screenshots of the main menu, a completed transaction, and an invalid transaction.

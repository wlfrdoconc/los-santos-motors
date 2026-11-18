# Manual Test Scenarios — Los Santos Motors

Run these against the provided **initial** inventory. Reset it between independent scenarios. Tests mentioning 2026 are illustrative; the application should use the actual current year dynamically.

| ID | Action | Expected result |
|---|---|---|
| T01 | Sell LS1001, no financing | Total $8,795.00; monthly $0.00; saved SALE; removed from inventory |
| T02 | Sell LS1001, financing YES | Total $8,795.00; 5.25% / 24-month payment; saved SALE |
| T03 | Sell LS1004, financing YES | Total $26,320.00; 4.25% / 48-month payment |
| T04 | Lease LS1002 (2025), in year 2026 | Lease total $45,600.00; 4% / 36-month payment; saved LEASE |
| T05 | Lease LS1005 (2018), in year 2026 | Rejected as too old; no contract; inventory unchanged |
| T06 | Lease LS1009 (2023), in year 2026 | Eligible: exactly 3 years old |
| T07 | Sell LS1009 ($10,000) | $495 processing fee and 48 months if financed |
| T08 | Sell LS1007 ($9,999) | $295 processing fee and 24 months if financed |
| T09 | Enter unknown VIN LS9999 | Friendly error; no contract; no inventory removal |
| T10 | Enter invalid menu choice / nonnumeric vehicle input | Friendly error; app still runs |
| T11 | Save two successive transactions | File contains both lines, no older lines lost |
| T12 | Restart app after transaction | Inventory does not show sold/leased vehicle |
| T13 | Simulate contract file write error | Report failure; do not remove vehicle from inventory |

**Testing reminder:** Use the stored vehicle price to select the financing tier, not the after-fee total. Do not round intermediate calculation values prematurely.

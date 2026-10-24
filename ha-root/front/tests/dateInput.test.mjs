import assert from "node:assert/strict";
import test from "node:test";
import { formatDateInput } from "../utils/dateInput.mjs";

test("clearing keeps the date field empty", () => {
  for (const value of [null, undefined, "", new Date(NaN)]) {
    assert.equal(formatDateInput(value), "");
  }
});

test("existing text input keeps its format", () => {
  for (const value of ["2026-10-25", "2026/10/25"]) {
    assert.equal(formatDateInput(value), value);
  }
});

test("picker selection keeps the local calendar day in different timezones", () => {
  const previousTimezone = process.env.TZ;
  try {
    for (const timezone of ["Asia/Tokyo", "UTC", "America/Los_Angeles"]) {
      process.env.TZ = timezone;
      assert.equal(formatDateInput(new Date(2026, 9, 25)), "2026-10-25");
      assert.equal(formatDateInput(new Date(2026, 0, 1, 23, 30)), "2026-01-01");
      assert.equal(formatDateInput(new Date(2028, 1, 29)), "2028-02-29");
    }
  } finally {
    if (previousTimezone === undefined) {
      delete process.env.TZ;
    } else {
      process.env.TZ = previousTimezone;
    }
  }
});

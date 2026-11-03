"""DML generation checks using the real SQL template and temporary output files."""

import sys
import tempfile
import unittest
from datetime import date, datetime, time, timezone
from decimal import Decimal
from pathlib import Path
from unittest.mock import patch

from jinja2 import Environment, FileSystemLoader

DB_DIR = Path(__file__).resolve().parents[1]
REPO_ROOT = DB_DIR.parents[1]
sys.path.insert(0, str(DB_DIR))

import dml_gen  # noqa: E402


class DmlGenerationTest(unittest.TestCase):
    def setUp(self):
        output = tempfile.TemporaryDirectory()
        self.addCleanup(output.cleanup)
        self.output = Path(output.name)
        templates = Environment(
            loader=FileSystemLoader(DB_DIR / "templates"), autoescape=False
        )
        output_patch = patch.object(dml_gen.const, "DML_OUTPUT_PATH", str(self.output))
        template_patch = patch.object(
            dml_gen.utils, "get_template", side_effect=templates.get_template
        )
        output_patch.start()
        template_patch.start()
        self.addCleanup(output_patch.stop)
        self.addCleanup(template_patch.stop)

    def generate_values(self, values):
        dml_gen.generate("SAMPLE", [["VALUE"], *[[value] for value in values]])
        return (self.output / "SAMPLE.sql").read_text(encoding="utf-8")

    def test_null_boolean_numeric_and_text_cells(self):
        cases = [
            (None, "NULL"),
            (True, "TRUE"),
            (False, "FALSE"),
            ("True", "TRUE"),
            ("False", "FALSE"),
            (0, "0"),
            (-42, "-42"),
            (12.5, "12.5"),
            (1e-7, "1e-07"),
            (Decimal("1234.500"), "1234.500"),
            ("NULL", "'NULL'"),
            ("None", "'None'"),
            ("00123", "'00123'"),
            ("", "''"),
            ("O'Brien", "'O''Brien'"),
            ('A "quoted" name', '\'A "quoted" name\''),
            ("健康😀", "'健康😀'"),
            ("'); DROP TABLE SAMPLE; --", "'''); DROP TABLE SAMPLE; --'"),
        ]
        actual = self.generate_values([value for value, _ in cases])
        expected = "".join(
            "INSERT INTO SAMPLE (VALUE) VALUES ({});\n".format(literal)
            for _, literal in cases
        )
        self.assertEqual(expected, actual)

    def test_backslashes_and_controls_use_sql_mode_independent_utf8_literals(self):
        cases = [
            (
                r"C:\temp\new\O'Brien",
                "CONVERT(X'433a5c74656d705c6e65775c4f27427269656e' USING utf8mb4)",
            ),
            (
                "\0\b\t\n\r\x1a\x7f\x85",
                "CONVERT(X'0008090a0d1a7fc285' USING utf8mb4)",
            ),
            (
                "健康😀\n\\",
                "CONVERT(X'e581a5e5bab7f09f98800a5c' USING utf8mb4)",
            ),
        ]
        actual = self.generate_values([value for value, _ in cases])
        expected = "".join(
            "INSERT INTO SAMPLE (VALUE) VALUES ({});\n".format(literal)
            for _, literal in cases
        )
        self.assertEqual(expected, actual)
        self.assertNotIn("\\", actual)
        self.assertEqual(3, len(actual.splitlines()))

    def test_dates_and_times_are_normalized(self):
        actual = self.generate_values(
            [
                date(2026, 11, 3),
                datetime(2026, 11, 3, 4, 5, 6),
                datetime(2026, 11, 3, 4, 5, 6, 123456),
                time(4, 5, 6),
                time(4, 5, 6, 123456),
            ]
        )
        self.assertEqual(
            "INSERT INTO SAMPLE (VALUE) VALUES ('2026-11-03');\n"
            "INSERT INTO SAMPLE (VALUE) VALUES ('2026-11-03 04:05:06');\n"
            "INSERT INTO SAMPLE (VALUE) VALUES ('2026-11-03 04:05:06.123456');\n"
            "INSERT INTO SAMPLE (VALUE) VALUES ('04:05:06');\n"
            "INSERT INTO SAMPLE (VALUE) VALUES ('04:05:06.123456');\n",
            actual,
        )

    def test_invalid_cells_do_not_replace_existing_sql(self):
        cases = [
            (float("nan"), ValueError, "Non-finite"),
            (float("inf"), ValueError, "Non-finite"),
            (-float("inf"), ValueError, "Non-finite"),
            (Decimal("NaN"), ValueError, "Non-finite"),
            (Decimal("sNaN"), ValueError, "Non-finite"),
            (Decimal("Infinity"), ValueError, "Non-finite"),
            (Decimal("-Infinity"), ValueError, "Non-finite"),
            (datetime(2026, 11, 3, tzinfo=timezone.utc), ValueError, "Timezone-aware"),
            (time(4, 5, 6, tzinfo=timezone.utc), ValueError, "Timezone-aware"),
            ([], TypeError, "Unsupported Excel cell type: list"),
            ({}, TypeError, "Unsupported Excel cell type: dict"),
            (b"bytes", TypeError, "Unsupported Excel cell type: bytes"),
            ("\ud800", ValueError, "Text cell values must be valid UTF-8"),
        ]
        target = self.output / "SAMPLE.sql"
        for value, error_type, message in cases:
            with self.subTest(value_type=type(value).__name__, value=repr(value)):
                target.write_text("existing SQL\n", encoding="utf-8")
                with self.assertRaisesRegex(
                    error_type, "SAMPLE row 3, column 1: " + message
                ):
                    self.generate_values(["valid first row", value])
                self.assertEqual("existing SQL\n", target.read_text(encoding="utf-8"))

    def test_current_workbook_produces_unchanged_seed_sql(self):
        workbook = REPO_ROOT / "ha-asset" / "02_db" / "DB.xlsx"
        data_sheets = [
            name
            for name in dml_gen.utils.get_sheet_names(str(workbook))
            if name not in {dml_gen.const.DDL_SHEET_NAME, "TEMPLATE"}
        ]
        self.assertEqual(4, len(data_sheets))
        for sheet_name in data_sheets:
            with self.subTest(sheet=sheet_name):
                rows = dml_gen.utils.read_rows(str(workbook), sheet_name)
                dml_gen.generate(sheet_name, rows)
                filename = sheet_name.upper() + ".sql"
                original = REPO_ROOT / "ha-asset" / "02_db" / "dml" / filename
                self.assertEqual(
                    original.read_text(encoding="utf-8"),
                    (self.output / filename).read_text(encoding="utf-8"),
                )


if __name__ == "__main__":
    unittest.main()

package jp.co.ha.business.io.file.excel.builder;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jp.co.ha.business.io.file.excel.model.HealthInfoExcelModel;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.ExcelConfig.ExcelConfigBuilder;

/**
 * Excel出力を再読込し、ライブラリ更新後も出力内容を維持することを確認する。
 */
class HealthInfoExcelBuilderTest {

    private static final String[] HEADERS = {
            "身長", "体重", "BMI", "標準体重", "健康情報登録日時"
    };

    @Test
    void downloadsWorkbookWithHeadersDataAndFooters() throws Exception {
        ExcelConfig config = new ExcelConfigBuilder("healthinfo.xlsx")
                .hasHeader(true).hasFooter(true).build();

        try (Workbook workbook = download(config, List.of(healthInfo()))) {
            assertThat(workbook.getNumberOfSheets()).isEqualTo(1);
            assertThat(workbook.getSheetName(0)).isEqualTo("健康情報");
            Sheet sheet = workbook.getSheetAt(0);
            assertThat(sheet.getPhysicalNumberOfRows()).isEqualTo(3);
            assertRow(sheet, 0, HEADERS);
            assertRow(sheet, 1, "170.0", "65.0", "22.5", "63.6",
                    "2026/10/26 12:34:56");
            assertRow(sheet, 2, HEADERS);
        }
    }

    @Test
    void masksHealthValuesWithoutChangingRegistrationDate() throws Exception {
        ExcelConfig config = new ExcelConfigBuilder("healthinfo.xlsx")
                .useMask(true).build();

        try (Workbook workbook = download(config, List.of(healthInfo()))) {
            Sheet sheet = workbook.getSheetAt(0);
            assertThat(sheet.getPhysicalNumberOfRows()).isEqualTo(1);
            assertRow(sheet, 0, "****", "****", "****", "****",
                    "2026/10/26 12:34:56");
        }
    }

    @Test
    void downloadsValidHeaderOnlyWorkbookWhenNoRecordsExist() throws Exception {
        ExcelConfig config = new ExcelConfigBuilder("healthinfo.xlsx")
                .hasHeader(true).build();

        try (Workbook workbook = download(config, List.of())) {
            Sheet sheet = workbook.getSheetAt(0);
            assertThat(sheet.getPhysicalNumberOfRows()).isEqualTo(1);
            assertRow(sheet, 0, HEADERS);
        }
    }

    private Workbook download(ExcelConfig config, List<HealthInfoExcelModel> records)
            throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        new HealthInfoExcelBuilder(config, records).render(Map.of(),
                new MockHttpServletRequest(), response);

        assertThat(response.getContentType())
                .isEqualTo("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return WorkbookFactory.create(
                new ByteArrayInputStream(response.getContentAsByteArray()));
    }

    private HealthInfoExcelModel healthInfo() {
        HealthInfoExcelModel model = new HealthInfoExcelModel();
        model.setHeight("170.0");
        model.setWeight("65.0");
        model.setBmi("22.5");
        model.setStandardWeight("63.6");
        model.setHealthInfoRegDate(LocalDateTime.of(2026, 10, 26, 12, 34, 56));
        return model;
    }

    private void assertRow(Sheet sheet, int row, String... expectedValues) {
        assertThat(sheet.getRow(row).getPhysicalNumberOfCells())
                .isEqualTo(expectedValues.length);
        for (int column = 0; column < expectedValues.length; column++) {
            assertThat(sheet.getRow(row).getCell(column).getStringCellValue())
                    .isEqualTo(expectedValues[column]);
        }
    }
}

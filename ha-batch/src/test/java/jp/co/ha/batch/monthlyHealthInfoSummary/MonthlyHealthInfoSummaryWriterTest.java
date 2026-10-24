package jp.co.ha.batch.monthlyHealthInfoSummary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ExecutionContext;

import jp.co.ha.batch.base.BatchProperties;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.common.aws.AwsS3Component;

/**
 * {@linkplain MonthlyHealthInfoSummaryWriter}のテストクラス
 * 
 * @version 1.0.0
 */
public class MonthlyHealthInfoSummaryWriterTest {

    /** 対象月 */
    private static final String TARGET_DATE = "202601";
    /** 期待値:S3キー */
    private static final String EXPECTED_S3_KEY = "monthly/healthinfo/year=2026/202601.csv.gz";
    /** 期待値:ヘッダ */
    private static final String EXPECTED_HEADER = String.join(",",
            "seqUserId", "height", "weight", "bmi", "standardWeight",
            "healthInfoRegDate", "seqBmiRangeMtId", "regDate", "updateDate")
            + System.lineSeparator();

    /** 一時ディレクトリ */
    @TempDir
    private Path tempDir;

    /**
     * ヘッダのみのファイルアップロードのテストメソッド
     * 
     * @throws Exception
     *     テスト実行に失敗
     */
    @Test
    public void uploadsHeaderOnlyCsvWhenNoItemsWereWritten() throws Exception {

        BatchProperties properties = new BatchProperties();
        BatchProperties.MonthlyHealthInfoSummary summary = new BatchProperties.MonthlyHealthInfoSummary();
        summary.setTempDirPath(tempDir.toString());
        properties.setMonthlyHealthInfoSummary(summary);

        AwsS3Component s3 = mock(AwsS3Component.class);
        SlackApiComponent slack = mock(SlackApiComponent.class);
        AtomicReference<String> uploadedContent = new AtomicReference<>();
        doAnswer(invocation -> {
            File gzipFile = invocation.getArgument(1);
            try (GZIPInputStream input = new GZIPInputStream(
                    java.nio.file.Files.newInputStream(gzipFile.toPath()))) {
                uploadedContent
                        .set(new String(input.readAllBytes(), StandardCharsets.UTF_8));
            }
            return null;
        }).when(s3).putFile(eq(EXPECTED_S3_KEY), any(File.class));

        StepExecution stepExecution = mock(StepExecution.class);
        when(stepExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(stepExecution.getWriteCount()).thenReturn(0L);

        MonthlyHealthInfoSummaryWriter writer = new MonthlyHealthInfoSummaryWriter(
                properties, s3, slack, TARGET_DATE);
        writer.beforeStep(stepExecution);
        writer.open(new ExecutionContext());
        writer.close();

        assertThat(uploadedContent.get()).isEqualTo(EXPECTED_HEADER);
        verify(s3).putFile(eq(EXPECTED_S3_KEY), any(File.class));
        verify(slack).sendFile(eq(SlackApiComponent.ContentType.BATCH), any(File.class),
                any(String.class));
        assertThat(tempDir.resolve(TARGET_DATE + ".csv")).doesNotExist();
        assertThat(tempDir.resolve(TARGET_DATE + ".csv.gz")).doesNotExist();
    }
}

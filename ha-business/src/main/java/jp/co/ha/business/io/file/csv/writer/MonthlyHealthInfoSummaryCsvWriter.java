package jp.co.ha.business.io.file.csv.writer;

import java.io.PrintWriter;
import java.util.StringJoiner;

import jp.co.ha.business.io.file.csv.model.MonthlyHealthInfoSummaryModel;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.writer.CsvWriter;
import jp.co.ha.common.util.StringUtil;

/**
 * 月次健康情報集計CSV Writer
 *
 * @version 1.0.0
 */
public class MonthlyHealthInfoSummaryCsvWriter
        extends CsvWriter<MonthlyHealthInfoSummaryModel> {

    /**
     * コンストラクタ
     *
     * @param conf
     *     CSV設定情報
     * @param pw
     *     {@linkplain PrintWriter}
     */
    public MonthlyHealthInfoSummaryCsvWriter(CsvConfig conf, PrintWriter pw) {
        super(conf, pw);
    }

    @Override
    public void writeData(StringJoiner record, MonthlyHealthInfoSummaryModel model) {

        // 1項目ごと区切る
        StringJoiner body = new StringJoiner(StringUtil.COMMA);

        // ユーザID
        write(body, model.getSeqUserId().toString());
        // 身長
        write(body, model.getHeight().toString());
        // 体重
        write(body, model.getWeight().toString());
        // BMI
        write(body, model.getBmi().toString());
        // 標準体重
        write(body, model.getStandardWeight().toString());
        // 健康情報作成日時
        write(body, model.getHealthInfoRegDate());
        // BMI範囲マスタID
        write(body, model.getSeqBmiRangeMtId().toString());
        // 更新日時
        write(body, model.getUpdateDate());
        // 登録日時
        write(body, model.getRegDate());

        // 1行書き込む
        record.add(body.toString());
    }

}

package jp.co.ha.web.file.csv.writer;

import java.io.PrintWriter;
import java.util.StringJoiner;

import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.file.csv.writer.CsvWriter;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateFormatPattern;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.web.file.csv.model.HealthInfoCsvDownloadModel;

/**
 * 健康情報CSVWriterクラス<br>
 *
 */
public class HealthInfoCsvWriter extends CsvWriter<HealthInfoCsvDownloadModel> {

	private final AppLogger LOG = LoggerFactory.getAppLogger(this.getClass());

	/**
	 * コンストラクタ<br>
	 *
	 * @param conf
	 *     CSV設定情報
	 * @param printWriter
	 *     出力用PrintWriter
	 */
	public HealthInfoCsvWriter(CsvConfig conf, PrintWriter printWriter) {
		super(conf, printWriter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeData(StringJoiner recordJoiner, HealthInfoCsvDownloadModel model) {

		LOG.info(model);
		// 1項目ごと区切る
		StringJoiner joiner = new StringJoiner(StringUtil.COMMA);

		// ユーザID
		write(joiner, model.getUserId());
		// 身長
		write(joiner, conf.useMask() ? MASK : model.getHeight().toString());
		// 体重
		write(joiner, conf.useMask() ? MASK : model.getWeight().toString());
		// BMI
		write(joiner, conf.useMask() ? MASK : model.getBmi().toString());
		// 標準体重
		write(joiner, conf.useMask() ? MASK : model.getStandardWeight().toString());
		// 登録日時
		write(joiner, DateUtil.toString(model.getRegDate(), DateFormatPattern.YYYYMMDD_HHMMSS));

		// 1行書き込む
		recordJoiner.add(joiner.toString());
	}

}

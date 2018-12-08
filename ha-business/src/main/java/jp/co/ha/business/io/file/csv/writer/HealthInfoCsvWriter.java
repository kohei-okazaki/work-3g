package jp.co.ha.business.io.file.csv.writer;

import java.io.PrintWriter;
import java.util.StringJoiner;

import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.writer.CsvWriter;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.log.MaskExecutor;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * 健康情報CSVWriterクラス<br>
 *
 */
public class HealthInfoCsvWriter extends CsvWriter<HealthInfoCsvDownloadModel> {

	/** LOG */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

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
	protected void writeData(StringJoiner record, HealthInfoCsvDownloadModel model) {

		LOG.infoRes(model);
		// 1項目ごと区切る
		StringJoiner body = new StringJoiner(StringUtil.COMMA);

		// ユーザID
		write(body, model.getUserId());
		// 身長
		write(body, conf.useMask() ? MaskExecutor.MASK : model.getHeight().toString());
		// 体重
		write(body, conf.useMask() ? MaskExecutor.MASK : model.getWeight().toString());
		// BMI
		write(body, conf.useMask() ? MaskExecutor.MASK : model.getBmi().toString());
		// 標準体重
		write(body, conf.useMask() ? MaskExecutor.MASK : model.getStandardWeight().toString());
		// 登録日時
		write(body, DateUtil.toString(model.getRegDate(), DateFormatType.YYYYMMDD_HHMMSS));

		// 1行書き込む
		record.add(body.toString());
	}

}

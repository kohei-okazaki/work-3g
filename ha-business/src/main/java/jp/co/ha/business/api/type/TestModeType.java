package jp.co.ha.business.api.type;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * テストモード区分列挙<br>
 *
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TestModeType {

	/** request処理とDB登録を行う */
	NOT_TEST("0"),
	/** request処理のみでDB登録を行わない */
	REQUEST_ONLY("1");

	/** 値 */
	private String value;

	/**
	 * コンストラクタ<br>
	 * @param value
	 */
	private TestModeType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	/**
	 * 指定された値と一致するテストモード区分列挙を返す<br>
	 *
	 * @param value
	 *     値
	 * @return
	 */
	@JsonCreator
	public static TestModeType of(String value) {
		return Stream.of(TestModeType.class.getEnumConstants())
				.filter(e -> e.getValue().equals(value))
				.findFirst()
				.orElse(null);
	}
}

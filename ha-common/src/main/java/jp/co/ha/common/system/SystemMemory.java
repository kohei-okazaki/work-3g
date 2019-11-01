package jp.co.ha.common.system;

import java.text.DecimalFormat;

/**
 * メモリ使用量クラス
 * 
 * @since 1.0
 */
public class SystemMemory {

	/** SystemMemory */
	private static SystemMemory instance = new SystemMemory();

	/**
	 * SystemMemoryを返す
	 *
	 * @return SystemMemory
	 */
	public static SystemMemory getInstance() {
		return instance;
	}

	/**
	 * メモリ使用量を返す
	 *
	 * @return メモリ使用量
	 */
	public String getMemoryUsage() {
		DecimalFormat usedFormat = new DecimalFormat("#,###KB");
		Long free = Runtime.getRuntime().freeMemory() / 1024;
		Long total = Runtime.getRuntime().totalMemory() / 1024;
		return usedFormat.format(total - free);
	}

	/**
	 * メモリ使用量を%で返す
	 *
	 * @return メモリ使用量(%)
	 */
	public String getMemoryUsageRate() {
		DecimalFormat usedFormat = new DecimalFormat("##.#");
		Long free = Runtime.getRuntime().freeMemory() / 1024;
		Long total = Runtime.getRuntime().totalMemory() / 1024;
		Long used = total - free;
		double ratio = (used * 100 / total.doubleValue());
		return usedFormat.format(ratio);
	}

}

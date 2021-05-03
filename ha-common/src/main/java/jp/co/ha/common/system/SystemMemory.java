package jp.co.ha.common.system;

import java.text.DecimalFormat;

/**
 * メモリ使用量クラス
 *
 * @version 1.0.0
 */
public class SystemMemory {

    /** 使用量フォーマット */
    private static final DecimalFormat USAGE_FORMAT = new DecimalFormat("#,###KB");
    /** 使用率フォーマット */
    private static final DecimalFormat RATE_FORMAT = new DecimalFormat("##.#");
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
        Long free = Runtime.getRuntime().freeMemory() / 1024;
        Long total = Runtime.getRuntime().totalMemory() / 1024;
        return USAGE_FORMAT.format(total - free);
    }

    /**
     * メモリ使用量を%で返す
     *
     * @return メモリ使用量(%)
     */
    public String getMemoryUsageRate() {
        Long free = Runtime.getRuntime().freeMemory() / 1024;
        Long total = Runtime.getRuntime().totalMemory() / 1024;
        Long used = total - free;
        double rate = (used * 100 / total.doubleValue());
        return RATE_FORMAT.format(rate);
    }

}

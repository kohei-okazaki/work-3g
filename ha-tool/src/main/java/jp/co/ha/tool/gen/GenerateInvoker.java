package jp.co.ha.tool.gen;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.tool.gen.BaseGenerator.GenerateType;

/**
 * 自動生成呼び出しクラス
 *
 * @version 1.0.0
 */
public class GenerateInvoker {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(GenerateInvoker.class);

    /**
     * 自動生成処理を呼び出す
     *
     * @param types
     *     自動生成区分
     */
    public static void invoke(GenerateType... types) {
        try {
            for (GenerateType type : types) {

                LOG.debug("【TOOL】 %s start...".formatted(type.getValue()));

                type.getGenClass().getDeclaredConstructor().newInstance().generate();

                LOG.debug("【TOOL】 %s end...".formatted(type.getValue()));
            }
        } catch (Exception e) {
            LOG.error("自動生成処理に失敗", e);
        }
    }

}

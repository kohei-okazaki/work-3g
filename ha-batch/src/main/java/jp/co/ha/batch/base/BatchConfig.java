package jp.co.ha.batch.base;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import jp.co.ha.business.config.BusinessConfig;
import jp.co.ha.common.config.CommonConfig;
import jp.co.ha.db.config.DbConfig;

/**
 * Batch処理の定義クラス
 *
 * @version 1.0.0
 */
@Configuration
@ComponentScan({ "jp.co.ha.batch" })
// commonプロジェクトなどのbean定義を読込
@Import({
        CommonConfig.class,
        DbConfig.class,
        BusinessConfig.class
})
public abstract class BatchConfig {

}

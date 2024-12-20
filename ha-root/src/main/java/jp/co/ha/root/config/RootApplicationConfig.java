package jp.co.ha.root.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import jp.co.ha.business.config.BusinessConfig;
import jp.co.ha.common.config.CommonConfig;
import jp.co.ha.db.config.DbConfig;

/**
 * RootAPIアプリケーションの設定クラス
 *
 * @version 1.0.0
 */
@Configuration
// commonプロジェクトなどのbean定義を読込
@Import({
        CommonConfig.class,
        DbConfig.class,
        BusinessConfig.class
})
public class RootApplicationConfig {

}

package jp.co.ha.root.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * RootAPIアプリケーションの設定クラス
 *
 * @version 1.0.0
 */
@Configuration
// commonプロジェクトなどのbean定義を読込
@ImportResource(value = {
        "classpath:common-context.xml",
        "classpath:db-context.xml",
        "classpath:business-context.xml" })
public class RootApplicationConfig {

}

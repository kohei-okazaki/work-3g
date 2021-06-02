package jp.co.ha.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Batchアプリケーション起動クラス
 *
 * @version 1.0.0
 */
@SpringBootApplication
@ComponentScan({ "jp.co.ha.batch" })
// commonプロジェクトなどのbean定義を読込
@ImportResource(value = {
        "classpath:common-context.xml",
        "classpath:db-context.xml",
        "classpath:business-context.xml" })
public class BatchApplication {

    /**
     * メイン処理
     *
     * @param args
     *     VM引数
     */
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

}

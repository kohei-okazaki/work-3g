package jp.co.ha.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Batchアプリケーション起動クラス
 *
 * @version 1.0.0
 */
@SpringBootApplication
@ComponentScan({ "jp.co.ha.batch" })
public class Application {

    /**
     * メイン処理
     *
     * @param args
     *     VM引数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

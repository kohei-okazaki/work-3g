package jp.co.ha.root;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RootAPIアプリケーション起動クラス
 *
 * @version 1.0.0
 */
@SpringBootApplication
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

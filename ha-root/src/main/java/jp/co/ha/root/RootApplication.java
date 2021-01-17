package jp.co.ha.root;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * RootAPIアプリケーション起動クラス
 *
 * @version 1.0.0
 */
@SpringBootApplication
@ComponentScan({
        "jp.co.ha.root.config",
        "jp.co.ha.root.base.*.controller",
        "jp.co.ha.root.contents.*.controller" })
public class RootApplication {

    /**
     * メイン処理
     *
     * @param args
     *     VM引数
     */
    public static void main(String[] args) {
        SpringApplication.run(RootApplication.class, args);
    }

}

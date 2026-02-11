package jp.co.ha.common.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.common.BaseCommonTest;

/**
 * {@linkplain JdbcProperties} のjUnit
 *
 * @version 1.0.0
 */
public class JdbcPropertiesTest extends BaseCommonTest {

    /** JdbcConfig */
    @Autowired
    private JdbcProperties conf;

    /**
     * JdbcConfigのテスト
     */
    @Test
    public void test() {
        assertEquals("com.mysql.cj.jdbc.Driver", conf.driverClassName());
        assertEquals(
                "jdbc:mysql://localhost:3306/work3g?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo",
                conf.url());
        assertEquals("app_user", conf.username());
        assertEquals("app_password", conf.password());
    }

}

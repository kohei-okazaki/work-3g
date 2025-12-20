package jp.co.ha.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jp.co.ha.common.config.CommonConfig;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * commonの基底テストクラス
 *
 * @version 1.0.0
 */
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(classes = CommonConfig.class)
@TestPropertySource(locations = {
        "classpath:local/crypt.properties",
        "classpath:local/jdbc.properties",
        "classpath:local/system.properties"
}, properties = {
        "jdbc.url=jdbc:mysql://localhost:3306/work3g?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo",
        "jdbc.username=app_user",
        "jdbc.password=app_password"
})
public abstract class BaseCommonTest {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BaseCommonTest.class);

    /**
     * before処理
     */
    @BeforeEach
    public void before() {
        LOG.debug("BaseCommonTest#before");

    }

    /**
     * after処理
     */
    @AfterEach
    public void after() {
        LOG.debug("BaseCommonTest#after");
    }

}

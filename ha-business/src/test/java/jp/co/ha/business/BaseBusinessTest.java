package jp.co.ha.business;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * 基底Businessテストクラス
 *
 * @version 1.0.0
 */
@ExtendWith(SpringExtension.class)
// @SpringJUnitConfig(classes = {
// CommonConfig.class,
// DbConfig.class,
// BusinessConfig.class })
// @TestPropertySource(locations = {
// "classpath:local/jdbc.properties",
// "classpath:local/system.properties",
// "classpath:local/healthinfo.properties",
// "classpath:local/aws.properties"
// }, properties = {
// "jdbc.url=jdbc:mysql://localhost:3306/work3g?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo",
// "jdbc.username=app_user",
// "jdbc.password=app_password"
// })
public abstract class BaseBusinessTest {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BaseBusinessTest.class);

    /**
     * before
     */
    @BeforeEach
    public void before() {
        LOG.debug("BaseBusinessTest#before");
    }

    /**
     * after
     */
    @AfterEach
    public void after() {
        LOG.debug("BaseBusinessTest#after");
    }
}

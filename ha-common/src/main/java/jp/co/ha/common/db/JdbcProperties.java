package jp.co.ha.common.db;

import org.springframework.stereotype.Component;

/**
 * jdbc.propertiesのBean
 *
 * @param driverClassName
 *     driverClassName
 * @param url
 *     url
 * @param username
 *     username
 * @param password
 *     password
 * @version 1.0.0
 */
@Component
public record JdbcProperties(
        String driverClassName,
        String url,
        String username,
        String password) {
}

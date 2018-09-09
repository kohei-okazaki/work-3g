package jp.co.ha.business.db.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;

/**
 * MyBatisでつかう基底インターフェース<br>
 *
 */
public interface MyBatisDao {

	static final String CONF_NAME = "mybatis-config.xml";

	public default SqlSession getSqlSession() throws BaseException {
		String sysPath = this.getClass().getClassLoader().getResource("").getPath();
		File xmlFile = new File(sysPath, CONF_NAME);
		try (InputStream is = new FileInputStream(xmlFile.getAbsolutePath())) {
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
			return factory.openSession();
		} catch (FileNotFoundException e) {
			throw new AppIOException(ErrorCode.FILE_READING_ERROR, "mybatis-config.xmlが見つかりません");
		} catch (IOException e) {
			throw new AppIOException(ErrorCode.FILE_READING_ERROR, "ファイルの読み込みに失敗しました");
		}
	}
}

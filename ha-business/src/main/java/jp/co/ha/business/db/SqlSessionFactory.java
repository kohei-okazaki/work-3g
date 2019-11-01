package jp.co.ha.business.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;

/**
 * SQLセッション取得のFactoryクラス<br>
 * (例)<br>
 * <code>SqlSession session = SqlSessionFactory.getInstance().getSqlSession();</code>
 * <code>session.getMapper(XXXMapper.class);</code>
 * 
 * @since 1.0
 */
public class SqlSessionFactory {

	/** 設定ファイル */
	private static final String CONF_FILE = "mybatis-config.xml";
	/** instance */
	private static SqlSessionFactory instance = new SqlSessionFactory();

	/**
	 * プライベートコンストラクタ
	 */
	private SqlSessionFactory() {
	}

	/**
	 * インスタンスを返す
	 *
	 * @return instance
	 */
	public static SqlSessionFactory getInstance() {
		return instance;
	}

	/**
	 * SQLセッションを取得する
	 *
	 * @return SqlSession
	 * @throws BaseException
	 *     基底例外
	 */
	public SqlSession getSqlSession() throws BaseException {
		String sysPath = this.getClass().getClassLoader().getResource("").getPath();
		File xmlFile = FileUtil.getFile(sysPath + FileSeparator.SYSTEM.getValue() + CONF_FILE);
		try (InputStream is = new FileInputStream(xmlFile.getAbsolutePath())) {
			return new SqlSessionFactoryBuilder().build(is).openSession();
		} catch (FileNotFoundException e) {
			throw new SystemException(CommonErrorCode.FILE_READING_ERROR, CONF_FILE + "が見つかりません", e);
		} catch (IOException e) {
			throw new SystemException(CommonErrorCode.FILE_READING_ERROR, CONF_FILE + "の読込に失敗しました", e);
		}
	}

}

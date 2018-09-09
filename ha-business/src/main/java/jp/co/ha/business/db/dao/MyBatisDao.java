package jp.co.ha.business.db.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * MyBatisでつかう基底インターフェース<br>
 *
 */
public interface MyBatisDao {

	public default SqlSession getSqlSession() {
		String sysPath = this.getClass().getClassLoader().getResource("").getPath();
		String mybatisConf = "mybatis-config.xml";
		File xmlFile = new File(sysPath, mybatisConf);
		try (InputStream is = new FileInputStream(xmlFile.getAbsolutePath())) {
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
			return factory.openSession();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

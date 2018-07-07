package jp.co.ha.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.dao.BaseDaoImpl;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.AppLoggerFactory;
import jp.co.ha.common.util.DateFormatPattern;
import jp.co.ha.common.util.DateUtil;

/**
 * アカウント情報のDaoクラス
 *
 */
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {

	private AppLogger logger = AppLoggerFactory.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account selectByUserId(String userId) {

		Account account = null;

//		try (Workbook workbook = WorkbookFactory.create(new File(RESOURCES))) {
//			Sheet sheet = workbook.getSheet(SHEET);
//			Iterator<Row> iteRow = sheet.rowIterator();
//			while (iteRow.hasNext()) {
//				// 1行取得
//				Row row = iteRow.next();
//				// ヘッダーの場合、次のレコードに進む
//				if (row.getRowNum() == HEADER_POSITION) continue;
//				if (userId.equals(row.getCell(0).getStringCellValue())) {
//					account = new Account();
//					account.setUserId(row.getCell(0).getStringCellValue());									// ユーザID
//					account.setPassword(row.getCell(1).getStringCellValue());								// パスワード
//					account.setDeleteFlag(row.getCell(2).getStringCellValue());								// 削除フラグ
//					account.setPasswordExpire(DateUtil.toDate(row.getCell(3).getStringCellValue()));		// パスワード有効期限
//					account.setRemarks(row.getCell(4).getStringCellValue());								// 備考
//					account.setFileEnclosureCharFlag(row.getCell(5).getStringCellValue());					// ファイル囲い文字利用フラグ
//					account.setHealthInfoMaskFlag(row.getCell(6).getStringCellValue());						// 健康情報マスクフラグ
//					account.setUpdateDate(DateUtil.toDate(row.getCell(7).getStringCellValue()));			// 更新日時
//					account.setRegDate(DateUtil.toDate(row.getCell(8).getStringCellValue()));				// 登録日時
//					logger.info(account);
//				}
//			}
//		} catch (EncryptedDocumentException e) {
//			e.printStackTrace();
//		} catch (InvalidFormatException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		try {
			connect();
			Statement stm = this.con.createStatement();
			String sql = "SELECT * FROM ACCOUNT WHERE USER_ID = '" + userId + "';";
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				account = new Account();
				account.setUserId(rs.getString(USER_ID));
				account.setPassword(rs.getString(PASSWORD));
				account.setDeleteFlag(rs.getString(DELETE_FLAG));
				account.setPasswordExpire(rs.getDate(PASSWORD_EXPIRE));
				account.setRemarks(rs.getString(REMARKS));
				account.setFileEnclosureCharFlag(rs.getString(FILE_ENCLOSURE_CHAR_FLAG));
				account.setHealthInfoMaskFlag(rs.getString(HEALTH_INFO_MASK_FLAG));
				account.setUpdateDate(rs.getTimestamp(UPDATE_DATE));
				account.setRegDate(rs.getTimestamp(REG_DATE));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(Account account) throws DuplicateKeyException {

		logger.info(account);

//		try (FileInputStream in = new FileInputStream(RESOURCES);
//				Workbook workbook = WorkbookFactory.create(in);
//				FileOutputStream fos = new FileOutputStream(RESOURCES)) {
//			Sheet sheet = workbook.getSheet(SHEET);
//
//			Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);
//
//			newRow.createCell(0).setCellValue(account.getUserId());
//			newRow.createCell(1).setCellValue(account.getPassword());
//			newRow.createCell(2).setCellValue(account.getDeleteFlag());
//			newRow.createCell(3).setCellValue(DateUtil.toString(account.getPasswordExpire(), DateFormatPattern.YYYYMMDD_HHMMSS));
//			newRow.createCell(4).setCellValue(account.getRemarks());
//			newRow.createCell(5).setCellValue(account.getFileEnclosureCharFlag());
//			newRow.createCell(6).setCellValue(account.getHealthInfoMaskFlag());
//			newRow.createCell(7).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
//			newRow.createCell(8).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
//
//			fos.flush();
//			workbook.write(fos);
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (EncryptedDocumentException e) {
//			e.printStackTrace();
//		} catch (InvalidFormatException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		try {
			connect();
			Statement stm = this.con.createStatement();
			String sql = "INSERT INTO ACCOUNT VALUES ("
													+ "'" + account.getUserId() + "', "
													+ "'" + account.getPassword() + "', "
													+ "'" + account.getDeleteFlag() + "', "
													+ "'" + DateUtil.toString(account.getPasswordExpire(), DateFormatPattern.YYYYMMDD) + "', "
													+ "'" + account.getRemarks() + "', "
													+ "'" + account.getFileEnclosureCharFlag() + "', "
													+ "'" + account.getHealthInfoMaskFlag() + "', "
													+ "'" + DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS) + "', "
													+ "'" + DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS) + "'"
													+
													")";
			int rs = stm.executeUpdate(sql);
			System.out.println("結果" + rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Account account) {

		logger.info(account);

//		try (FileInputStream in = new FileInputStream(RESOURCES);
//				Workbook workbook = WorkbookFactory.create(in);
//				FileOutputStream fos = new FileOutputStream(RESOURCES)) {
//			Sheet sheet = workbook.getSheet(SHEET);
//			Iterator<Row> iteRow = sheet.rowIterator();
//			while (iteRow.hasNext()) {
//				// 1行取得
//				Row row = iteRow.next();
//				if (account.getUserId().equals(row.getCell(0).getStringCellValue())) {
//					// ユーザIDをキーにEntityを取得
//					row.getCell(0).setCellValue(account.getUserId());
//					row.getCell(1).setCellValue(account.getPassword());
//					row.getCell(2).setCellValue(account.getDeleteFlag());
//					row.getCell(3).setCellValue(DateUtil.toString(account.getPasswordExpire(), DateFormatPattern.YYYYMMDD_HHMMSS));
//					row.getCell(4).setCellValue(account.getRemarks());
//					row.getCell(5).setCellValue(account.getFileEnclosureCharFlag());
//					row.getCell(6).setCellValue(account.getHealthInfoMaskFlag());
//					row.getCell(7).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
//					row.getCell(8).setCellValue(DateUtil.toString(account.getRegDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
//				}
//			}
//			fos.flush();
//			workbook.write(fos);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (EncryptedDocumentException e) {
//			e.printStackTrace();
//		} catch (InvalidFormatException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		try {
			connect();
			Statement stm = this.con.createStatement();
			String sql = "UPDATE ACCOUNT SET "
										+ PASSWORD + "= '" + account.getPassword() + "', "
										+ DELETE_FLAG + "= '" + account.getDeleteFlag() + "', "
										+ PASSWORD_EXPIRE + "= '" + DateUtil.toString(account.getPasswordExpire(), DateFormatPattern.YYYYMMDD) + "', "
										+ REMARKS + "= '" + account.getRemarks() + "', "
										+ FILE_ENCLOSURE_CHAR_FLAG + "= '" + account.getFileEnclosureCharFlag() + "', "
										+ HEALTH_INFO_MASK_FLAG + "= '" + account.getHealthInfoMaskFlag() + "', "
										+ UPDATE_DATE + "= '" + DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS) + "'"
										+ " WHERE "+ USER_ID + "= '" + account.getUserId() + "'";
			System.out.println(sql);
			int rs = stm.executeUpdate(sql);
			System.out.println("結果" + rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(String userId) {
		// TODO 削除処理を追加すること
	}

}

package jp.co.ha.common.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.AppLoggerFactory;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;

/**
 * アカウント情報のDaoクラス
 *
 */
public class AccountDaoImpl implements AccountDao {

	private AppLogger logger = AppLoggerFactory.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account findByUserId(String userId) {

		Account account = null;

		try (Workbook workbook = WorkbookFactory.create(new File(RESOURCES))) {

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> iteRow = sheet.rowIterator();
			while (iteRow.hasNext()) {

				// 1行取得
				Row row = iteRow.next();

				// ヘッダーの場合、次のレコードに進む
				if (row.getRowNum() == HEADER_POSITION) continue;

				if (userId.equals(row.getCell(0).getStringCellValue())) {
					account = new Account();
					account.setUserId(row.getCell(0).getStringCellValue());									// ユーザID
					account.setPassword(row.getCell(1).getStringCellValue());								// パスワード
					account.setDeleteFlag(row.getCell(2).getStringCellValue());								// 削除フラグ
					account.setPasswordExpire(DateUtil.toDate(row.getCell(3).getStringCellValue()));		// パスワード有効期限
					account.setRemarks(row.getCell(4).getStringCellValue());								// 備考
					account.setFileEnclosureCharFlag(row.getCell(5).getStringCellValue());					// ファイル囲い文字利用フラグ
					account.setHealthInfoMaskFlag(row.getCell(6).getStringCellValue());						// 健康情報マスクフラグ
					account.setUpdateDate(DateUtil.toDate(row.getCell(7).getStringCellValue()));			// 更新日時
					account.setRegDate(DateUtil.toDate(row.getCell(8).getStringCellValue()));				// 登録日時

					logger.info(account);
				}
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(Account account) throws DuplicateKeyException {

		logger.info(account);

		try (FileInputStream in = new FileInputStream(RESOURCES);
				Workbook workbook = WorkbookFactory.create(in);
				FileOutputStream fos = new FileOutputStream(RESOURCES)) {
			Sheet sheet = workbook.getSheet(SHEET);

			Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);

			newRow.createCell(0).setCellValue(account.getUserId());
			newRow.createCell(1).setCellValue(account.getPassword());
			newRow.createCell(2).setCellValue(account.getDeleteFlag());
			newRow.createCell(3).setCellValue(DateUtil.toString(account.getPasswordExpire(), DateFormatDefine.YYYYMMDD_HHMMSS));
			newRow.createCell(4).setCellValue(account.getRemarks());
			newRow.createCell(5).setCellValue(account.getFileEnclosureCharFlag());
			newRow.createCell(6).setCellValue(account.getHealthInfoMaskFlag());
			newRow.createCell(7).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatDefine.YYYYMMDD_HHMMSS));
			newRow.createCell(8).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatDefine.YYYYMMDD_HHMMSS));

			fos.flush();
			workbook.write(fos);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateAccount(Account account) {

		logger.info(account);

		try (FileInputStream in = new FileInputStream(RESOURCES);
				Workbook workbook = WorkbookFactory.create(in);
				FileOutputStream fos = new FileOutputStream(RESOURCES)) {

			Sheet sheet = workbook.getSheet(SHEET);

			Iterator<Row> iteRow = sheet.rowIterator();
			while (iteRow.hasNext()) {

				// 1行取得
				Row row = iteRow.next();
				if (account.getUserId().equals(row.getCell(0).getStringCellValue())) {
					// ユーザIDをキーにEntityを取得
					row.getCell(0).setCellValue(account.getUserId());
					row.getCell(1).setCellValue(account.getPassword());
					row.getCell(2).setCellValue(account.getDeleteFlag());
					row.getCell(3).setCellValue(DateUtil.toString(account.getPasswordExpire(), DateFormatDefine.YYYYMMDD_HHMMSS));
					row.getCell(4).setCellValue(account.getRemarks());
					row.getCell(5).setCellValue(account.getFileEnclosureCharFlag());
					row.getCell(6).setCellValue(account.getHealthInfoMaskFlag());
					row.getCell(7).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatDefine.YYYYMMDD_HHMMSS));
					row.getCell(8).setCellValue(DateUtil.toString(account.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));

				}
			}

			fos.flush();
			workbook.write(fos);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAccount(String userId) {
		// TODO 削除処理を追加すること
	}

}

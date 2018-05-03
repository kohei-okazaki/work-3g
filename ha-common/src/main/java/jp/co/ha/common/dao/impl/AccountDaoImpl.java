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
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;

/**
 * アカウント情報のDaoクラス
 *
 */
public class AccountDaoImpl implements AccountDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account getAccountByUserId(String userId) {

		Account account = new Account();

		try (Workbook workbook = WorkbookFactory.create(new File(RESOURCES))) {

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> iteRow = sheet.rowIterator();
			while (iteRow.hasNext()) {

				// 1行取得
				Row row = iteRow.next();

				// ヘッダーの場合、次のレコードに進む
				if (row.getRowNum() == HEADER_POSITION) continue;

				if (userId.equals(row.getCell(0).getStringCellValue())) {
					account.setUserId(row.getCell(0).getStringCellValue());
					account.setPassword(row.getCell(1).getStringCellValue());
					account.setDeleteFlag(row.getCell(2).getStringCellValue());
					account.setPasswordExpire(DateUtil.formatDate(row.getCell(3).getStringCellValue()));
					account.setRemarks(row.getCell(4).getStringCellValue());
					account.setFileEnclosureCharFlag(row.getCell(5).getStringCellValue());
					account.setUpdateDate(DateUtil.formatDate(row.getCell(6).getStringCellValue()));
					account.setRegDate(DateUtil.formatDate(row.getCell(7).getStringCellValue()));
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

//		AccountDto dto = new AccountDto();
//		dto.setUserId(userId);
//		dto.setPassword("password");
//		dto.setInvalidFlag("0");
//		dto.setPasswordExpire(DateUtil.getSysDate());
//		dto.setRemarks("ここは備考です。");
//		dto.setFileEnclosureCharFlag("1");
		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registAccount(Account account) throws DuplicateKeyException {
		// TODO 登録処理を追加すること

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
			newRow.createCell(6).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatDefine.YYYYMMDD_HHMMSS));
			newRow.createCell(7).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatDefine.YYYYMMDD_HHMMSS));

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
		// TODO 更新処理を追加すること

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
					row.getCell(6).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatDefine.YYYYMMDD_HHMMSS));
					row.getCell(7).setCellValue(DateUtil.toString(account.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));

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

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

import jp.co.ha.common.dao.BaseDaoImpl;
import jp.co.ha.common.dao.MailInfoDao;
import jp.co.ha.common.entity.MailInfo;
import jp.co.ha.common.exception.DBException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.AppLoggerFactory;
import jp.co.ha.common.util.DateFormatPattern;
import jp.co.ha.common.util.DateUtil;

/**
 * メール情報のDaoクラス
 *
 */
public class MailInfoDaoImpl extends BaseDaoImpl implements MailInfoDao {

	private AppLogger logger = AppLoggerFactory.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailInfo findByUserId(String userId) {

		MailInfo mailInfo = null;

		try (Workbook workbook = WorkbookFactory.create(new File(RESOURCES))) {

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> iteRow = sheet.rowIterator();
			while (iteRow.hasNext()) {

				// 1行取得
				Row row = iteRow.next();

				// ヘッダーの場合、次のレコードに進む
				if (row.getRowNum() == HEADER_POSITION) continue;

				if (userId.equals(row.getCell(0).getStringCellValue())) {
					mailInfo = new MailInfo();
					mailInfo.setUserId(row.getCell(0).getStringCellValue());
					mailInfo.setMailAddress(row.getCell(1).getStringCellValue());
					mailInfo.setMailPassword(row.getCell(2).getStringCellValue());
					mailInfo.setUpdateDate(DateUtil.toDate(row.getCell(3).getStringCellValue()));
					mailInfo.setRegDate(DateUtil.toDate(row.getCell(4).getStringCellValue()));

					logger.info(mailInfo);
				}
			}
		} catch (EncryptedDocumentException e) {
			throw new DBException(ErrorCode.DB_ENCRYPT_ERROR, "メール情報の暗号化/複合化に失敗しました");
		} catch (InvalidFormatException e) {
			throw new DBException(ErrorCode.DB_ENCRYPT_ERROR, "メール情報の暗号化/複合化に失敗しました");
		} catch (IOException e) {
			throw new DBException(ErrorCode.DB_ENCRYPT_ERROR, "メール情報の暗号化/複合化に失敗しました");
		} catch (Exception e) {
			throw new DBException(ErrorCode.DB_ENCRYPT_ERROR, "DBアクセスに失敗しました");
		}

		return mailInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateMailInfo(MailInfo mailInfo) {

		logger.info(mailInfo);

		try (FileInputStream in = new FileInputStream(RESOURCES);
				Workbook workbook = WorkbookFactory.create(in);
				FileOutputStream fos = new FileOutputStream(RESOURCES)) {

			Sheet sheet = workbook.getSheet(SHEET);

			Iterator<Row> iteRow = sheet.rowIterator();
			while (iteRow.hasNext()) {

				// 1行取得
				Row row = iteRow.next();
				if (mailInfo.getUserId().equals(row.getCell(0).getStringCellValue())) {
					// ユーザIDをキーにEntityを取得
					row.getCell(0).setCellValue(mailInfo.getUserId());
					row.getCell(1).setCellValue(mailInfo.getMailAddress());
					row.getCell(2).setCellValue(mailInfo.getMailPassword());
					row.getCell(3).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
					row.getCell(4).setCellValue(DateUtil.toString(mailInfo.getRegDate(), DateFormatPattern.YYYYMMDD_HHMMSS));

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
	public void create(MailInfo mailInfo) throws DuplicateKeyException {

		logger.info(mailInfo);

		try (FileInputStream in = new FileInputStream(RESOURCES);
				Workbook workbook = WorkbookFactory.create(in);
				FileOutputStream fos = new FileOutputStream(RESOURCES)) {
			Sheet sheet = workbook.getSheet(SHEET);

			Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);

			newRow.createCell(0).setCellValue(mailInfo.getUserId());
			newRow.createCell(1).setCellValue(mailInfo.getMailAddress());
			newRow.createCell(2).setCellValue(mailInfo.getMailPassword());
			newRow.createCell(3).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
			newRow.createCell(4).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS));

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

}

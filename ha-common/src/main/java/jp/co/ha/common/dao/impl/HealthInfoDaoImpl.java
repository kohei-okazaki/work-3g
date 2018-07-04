package jp.co.ha.common.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.AppLoggerFactory;
import jp.co.ha.common.util.DateFormatPattern;
import jp.co.ha.common.util.DateUtil;

/**
 * 健康情報のDaoクラス
 *
 */
public class HealthInfoDaoImpl implements HealthInfoDao {

	private AppLogger logger = AppLoggerFactory.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> selectByUserId(String userId) {

		List<HealthInfo> healthInfoList = new ArrayList<HealthInfo>();
		try (Workbook workbook = WorkbookFactory.create(new File(RESOURCES))) {
			Sheet sheet = workbook.getSheet(SHEET);

			Iterator<Row> iteRow = sheet.rowIterator();
			while (iteRow.hasNext()) {

				Row row = iteRow.next();

				// ヘッダーの場合、次のレコードに進む
				if (row.getRowNum() == HEADER_POSITION) continue;

				if (userId.equals(row.getCell(1).getStringCellValue())) {
					HealthInfo healthInfo = new HealthInfo();
					healthInfo.setHealthInfoId(row.getCell(0).getStringCellValue());								// データID
					healthInfo.setUserId(row.getCell(1).getStringCellValue());								// ユーザID
					healthInfo.setHeight(new BigDecimal(row.getCell(2).getStringCellValue()));				// 身長
					healthInfo.setWeight(new BigDecimal(row.getCell(3).getStringCellValue()));				// 体重
					healthInfo.setBmi(new BigDecimal(row.getCell(4).getStringCellValue()));					// BMI
					healthInfo.setStandardWeight(new BigDecimal(row.getCell(5).getStringCellValue()));		// 標準体重
					healthInfo.setUserStatus(row.getCell(6).getStringCellValue());							// ユーザステータス
					healthInfo.setRegDate(DateUtil.toDate(row.getCell(7).getStringCellValue()));			// 登録日時
					healthInfoList.add(healthInfo);
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

		return healthInfoList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo selectByHealthInfoId(String healthInfoId) {

		HealthInfo healthInfo = null;
		try (Workbook workbook = WorkbookFactory.create(new File(RESOURCES))) {
			Sheet sheet = workbook.getSheet(SHEET);

			Iterator<Row> iteRow = sheet.rowIterator();
			while (iteRow.hasNext()) {
				// 1行読み込む
				Row row = iteRow.next();
				// ヘッダーの場合は次のレコードに進む
				if (row.getRowNum() == 0) continue;

				if (healthInfoId.equals(row.getCell(0).getStringCellValue())) {
					healthInfo = new HealthInfo();
					healthInfo.setHealthInfoId(row.getCell(0).getStringCellValue());								// データID
					healthInfo.setUserId(row.getCell(1).getStringCellValue());								// ユーザID
					healthInfo.setHeight(new BigDecimal(row.getCell(2).getStringCellValue()));				// 身長
					healthInfo.setWeight(new BigDecimal(row.getCell(3).getStringCellValue()));				// 体重
					healthInfo.setBmi(new BigDecimal(row.getCell(4).getStringCellValue()));					// BMI
					healthInfo.setStandardWeight(new BigDecimal(row.getCell(5).getStringCellValue()));		// 標準体重
					healthInfo.setUserStatus(row.getCell(6).getStringCellValue());							// ユーザステータス
					healthInfo.setRegDate(DateUtil.toDate(row.getCell(7).getStringCellValue()));			// 登録日時
					logger.info(healthInfo);
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

		return healthInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create(HealthInfo healthInfo) throws DuplicateKeyException {

		try (FileInputStream in = new FileInputStream(RESOURCES);
				Workbook workbook = WorkbookFactory.create(in);
				FileOutputStream fos = new FileOutputStream(RESOURCES)) {
			Sheet sheet = workbook.getSheet(SHEET);

			Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);

			newRow.createCell(0).setCellValue(String.valueOf(sheet.getLastRowNum()));										// データID
			newRow.createCell(1).setCellValue(healthInfo.getUserId());														// ユーザID
			newRow.createCell(2).setCellValue(healthInfo.getHeight().toString());											// 身長
			newRow.createCell(3).setCellValue(healthInfo.getWeight().toString());											// 体重
			newRow.createCell(4).setCellValue(healthInfo.getBmi().toString());												// BMI
			newRow.createCell(5).setCellValue(healthInfo.getStandardWeight().toString());									// 標準体重
			newRow.createCell(6).setCellValue(healthInfo.getUserStatus());													// ユーザステータス
			newRow.createCell(7).setCellValue(DateUtil.toString(DateUtil.getSysDate(), DateFormatPattern.YYYYMMDD_HHMMSS));	// 登録日時

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

		logger.info(healthInfo);
	}

}

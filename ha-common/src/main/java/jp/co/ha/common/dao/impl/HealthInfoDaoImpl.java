package jp.co.ha.common.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.dao.DuplicateKeyException;

import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;

/**
 * 健康情報のDaoクラス
 *
 */
public class HealthInfoDaoImpl implements HealthInfoDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo getLastHealthInfoByUserId(String userId) {

		List<HealthInfo> healthInfoList = getHealthInfoByUserId(userId);

		if (Objects.isNull(healthInfoList) || healthInfoList.isEmpty()) {
			// 登録がされてなかった場合
			return null;
		}

		return healthInfoList.get(healthInfoList.size() - 1);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfo> getHealthInfoByUserId(String userId) {

		List<HealthInfo> healthInfoList = new ArrayList<HealthInfo>();
		try (Workbook workbook = WorkbookFactory.create(new File(RESOURCES))) {
			Sheet sheet = workbook.getSheet(SHEET);

			Iterator<Row> iteRow = sheet.rowIterator();
			while (iteRow.hasNext()) {
				HealthInfo healthInfo = new HealthInfo();
				Row row = iteRow.next();

				// ヘッダーの場合、次のレコードに進む
				if (row.getRowNum() == HEADER_POSITION) continue;

				if (userId.equals(row.getCell(1).getStringCellValue())) {
					healthInfo.setDataId(row.getCell(0).getStringCellValue());								// データID
					healthInfo.setUserId(row.getCell(1).getStringCellValue());								// ユーザID
					healthInfo.setHeight(new BigDecimal(row.getCell(2).getStringCellValue()));				// 身長
					healthInfo.setWeight(new BigDecimal(row.getCell(3).getStringCellValue()));				// 体重
					healthInfo.setBmi(new BigDecimal(row.getCell(4).getStringCellValue()));					// BMI
					healthInfo.setStandardWeight(new BigDecimal(row.getCell(5).getStringCellValue()));		// 標準体重
					healthInfo.setUserStatus(row.getCell(6).getStringCellValue());							// ユーザステータス
					healthInfo.setRegDate(DateUtil.formatDate(row.getCell(7).getStringCellValue()));			// 登録日時
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

		// FIXME
//		List<HealthInfoDto> dtoList = new ArrayList<HealthInfoDto>();
//		int maxCount = 13;
//		for (int i = 0; i < maxCount; i++) {
//			HealthInfoDto dto = new HealthInfoDto();
//			dto.setDataId(String.valueOf(i));
//			dto.setUserId(userId);
//			if (i % 3 == 0) {
//				dto.setHeight(new BigDecimal(176.8).setScale(1, RoundingMode.HALF_UP));
//				dto.setWeight(new BigDecimal(64.9).setScale(1, RoundingMode.HALF_UP));
//				dto.setBmi(new BigDecimal(15.7).setScale(1, RoundingMode.HALF_UP));
//				dto.setStandardWeight(new BigDecimal(61.0).setScale(1, RoundingMode.HALF_UP));
//				dto.setUserStatus(CodeManager.getInstance().getValue(MainKey.HEALTH_INFO_USER_STATUS, SubKey.DOWN));
//				dto.setRegDate(DateUtil.formatDate("2017/03/02 01:23:45"));
//			} else if (i == 7) {
//				dto.setHeight(new BigDecimal(176.4).setScale(1, RoundingMode.HALF_UP));
//				dto.setWeight(new BigDecimal(63.5).setScale(1, RoundingMode.HALF_UP));
//				dto.setBmi(new BigDecimal(18.7).setScale(1, RoundingMode.HALF_UP));
//				dto.setStandardWeight(new BigDecimal(64.2).setScale(1, RoundingMode.HALF_UP));
//				dto.setUserStatus(CodeManager.getInstance().getValue(MainKey.HEALTH_INFO_USER_STATUS, SubKey.DOWN));
//				dto.setRegDate(DateUtil.formatDate("2017/10/12 12:23:45"));
//			} else {
//				dto.setHeight(new BigDecimal(176.5).setScale(1, RoundingMode.HALF_UP));
//				dto.setWeight(new BigDecimal(63.5).setScale(1, RoundingMode.HALF_UP));
//				dto.setBmi(new BigDecimal(18.9).setScale(1, RoundingMode.HALF_UP));
//				dto.setStandardWeight(new BigDecimal(64.2).setScale(1, RoundingMode.HALF_UP));
//				dto.setUserStatus(CodeManager.getInstance().getValue(MainKey.HEALTH_INFO_USER_STATUS, SubKey.DOWN));
//				dto.setRegDate(DateUtil.formatDate("2017/10/15 15:45:45"));
//			}
//			dtoList.add(dto);
//		}
		return healthInfoList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo getHealthInfoByDataId(String dateId) {

		HealthInfo healthInfo = new HealthInfo();
		try (Workbook workbook = WorkbookFactory.create(new File(RESOURCES))) {
			Sheet sheet = workbook.getSheet(SHEET);

			Iterator<Row> iteRow = sheet.rowIterator();
			while (iteRow.hasNext()) {
				// 1行読み込む
				Row row = iteRow.next();
				if (row.getRowNum() == 0) {
					// ヘッダーの場合は次のレコードに進む
					continue;
				}
				if (dateId.equals(row.getCell(0).getStringCellValue())) {
					healthInfo.setDataId(row.getCell(0).getStringCellValue());								// データID
					healthInfo.setUserId(row.getCell(1).getStringCellValue());								// ユーザID
					healthInfo.setHeight(new BigDecimal(row.getCell(2).getStringCellValue()));				// 身長
					healthInfo.setWeight(new BigDecimal(row.getCell(3).getStringCellValue()));				// 体重
					healthInfo.setBmi(new BigDecimal(row.getCell(4).getStringCellValue()));					// BMI
					healthInfo.setStandardWeight(new BigDecimal(row.getCell(5).getStringCellValue()));		// 標準体重
					healthInfo.setUserStatus(row.getCell(6).getStringCellValue());							// ユーザステータス
					healthInfo.setRegDate(DateUtil.formatDate(row.getCell(7).getStringCellValue()));			// 登録日時
				}
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// FIXME
//		HealthInfoDto healthInfoDto = new HealthInfoDto();
//		healthInfoDto.setDataId(dateId);
//		healthInfoDto.setHeight(new BigDecimal(171.2).setScale(1, RoundingMode.HALF_UP));
//		healthInfoDto.setWeight(new BigDecimal(61.3).setScale(1, RoundingMode.HALF_UP));
//		healthInfoDto.setBmi(new BigDecimal(16).setScale(1, RoundingMode.HALF_UP));
//		healthInfoDto.setStandardWeight(new BigDecimal(58.6).setScale(1, RoundingMode.HALF_UP));
//		healthInfoDto.setUserStatus(CodeManager.getInstance().getValue(MainKey.HEALTH_INFO_USER_STATUS, SubKey.DOWN));
//		healthInfoDto.setRegDate(DateUtil.formatDate("2017/10/12 12:23:45"));
		return healthInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registHealthInfo(HealthInfo healthInfo) throws DuplicateKeyException {

		try (FileInputStream in = new FileInputStream(RESOURCES);
				Workbook workbook = WorkbookFactory.create(in);
				FileOutputStream fos = new FileOutputStream(RESOURCES)) {
			Sheet sheet = workbook.getSheet(SHEET);

			Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);

			newRow.createCell(0).setCellValue(String.valueOf(sheet.getLastRowNum()));										// データID
			newRow.createCell(1).setCellValue(healthInfo.getUserId());													// ユーザID
			newRow.createCell(2).setCellValue(healthInfo.getHeight().toString());											// 身長
			newRow.createCell(3).setCellValue(healthInfo.getWeight().toString());											// 体重
			newRow.createCell(4).setCellValue(healthInfo.getBmi().toString());											// BMI
			newRow.createCell(5).setCellValue(healthInfo.getStandardWeight().toString());									// 標準体重
			newRow.createCell(6).setCellValue(healthInfo.getUserStatus());												// ユーザステータス
			newRow.createCell(7).setCellValue(DateUtil.toString(new Date(), DateFormatDefine.YYYYMMDD_HHMMSS));			// 登録日時

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

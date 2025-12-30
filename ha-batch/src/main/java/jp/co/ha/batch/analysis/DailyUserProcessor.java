package jp.co.ha.batch.analysis;

import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import jp.co.ha.business.io.file.csv.model.DailyUserCsvModel;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.custom.CompositeUser;

/**
 * 日次ユーザ情報データ分析連携バッチ-Proccesor
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class DailyUserProcessor
        implements ItemProcessor<CompositeUser, DailyUserCsvModel> {

    @Override
    public DailyUserCsvModel process(CompositeUser item) throws Exception {

        DailyUserCsvModel model = new DailyUserCsvModel();

        model.setSeqUserId(item.getSeqUserId());
        model.setGenderType(item.getGenderType());
        model.setBirthDate(DateTimeUtil.toString(item.getBirthDate(), YYYYMMDD));
        model.setDeleteFlag(item.isDeleteFlag());
        model.setPasswordExpire(
                DateTimeUtil.toString(item.getPasswordExpire(), YYYYMMDD));
        model.setRemarks(item.getRemarks());
        model.setGoalWeight(item.getGoalWeight());
        model.setRegDate(DateTimeUtil.toString(item.getRegDate(), YYYYMMDDHHMMSS));
        model.setUpdateDate(DateTimeUtil.toString(item.getUpdateDate(), YYYYMMDDHHMMSS));
        model.setHeaderFlag(item.isHeaderFlag());
        model.setFooterFlag(item.isFooterFlag());
        model.setMaskFlag(item.isMaskFlag());
        model.setEnclosureCharFlag(item.isEnclosureCharFlag());

        return model;
    }
}

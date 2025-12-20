package jp.co.ha.batch.healthInfoMigrate;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.track.request.HealthInfoMigrateApiRequest;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報連携処理-Proccesor
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class HealthInfoMigrateProcessor
        implements ItemProcessor<HealthInfo, HealthInfoMigrateApiRequest> {

    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties healthInfoProperties;

    @Override
    public HealthInfoMigrateApiRequest process(HealthInfo item) throws Exception {

        HealthInfoMigrateApiRequest req = new HealthInfoMigrateApiRequest();
        req.setSeqUserId(item.getSeqUserId());
        req.setMigrateFlg(healthInfoProperties.isTrackApiDbMigrateFlg());

        HealthInfoMigrateApiRequest.HealthInfo bean = new HealthInfoMigrateApiRequest.HealthInfo();
        bean.setSeqHealthInfoId(item.getSeqHealthInfoId());
        bean.setHeight(item.getHeight());
        bean.setWeight(item.getWeight());
        bean.setBmi(item.getBmi());
        bean.setStandardWeight(item.getStandardWeight());
        bean.setCreatedAt(item.getHealthInfoRegDate());
        req.setHealthInfoList(List.of(bean));

        return req;
    }

}

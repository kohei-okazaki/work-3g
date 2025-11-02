package jp.co.ha.batch.healthInfoFileRegist;

import static jp.co.ha.common.exception.CommonErrorCode.*;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.healthInfoFileRegist.HealthInfoFileRegistDto.HealthInfoRequestData;
import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfoapp.type.TestMode;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.FileUtil;

/**
 * 健康情報一括登録処理-Reader
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class HealthInfoFileRegistReader
        extends AbstractItemCountingItemStreamItemReader<HealthInfoRegistApiRequest> {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(HealthInfoFileRegistReader.class);
    /** JSON読み取りクラス */
    private static final JsonReader JSON_READER = new JsonReader();

    /** 健康情報設定ファイル */
    private HealthInfoProperties prop;
    /** API通信情報Component */
    private ApiCommunicationDataComponent component;
    /** 健康情報登録APIリクエストキュー */
    private Deque<HealthInfoRegistApiRequest> buffer = new ArrayDeque<>();
    /** ファイルリスト */
    private List<File> fileList;
    /** ファイルインデックス */
    private int fileIndex = 0;

    /**
     * コンストラクタ
     * 
     * @param prop
     *     健康情報設定ファイル
     * @param component
     *     API通信情報Component
     * @param jsonReader
     *     JSON読み取りクラス
     */
    public HealthInfoFileRegistReader(HealthInfoProperties prop,
            ApiCommunicationDataComponent component) {

        setName("healthInfoFileRegistReader");

        this.prop = prop;
        this.component = component;
    }

    @Override
    public void doOpen() throws Exception {

        LOG.debug("called doOpen");

        if (!FileUtil.isExists(prop.getRegistBatchFilePath())) {
            throw new BusinessException(RUNTIME_ERROR,
                    "ディレクトリが存在しません: " + prop.getRegistBatchFilePath());
        }
        this.fileList = FileUtil.getFileList(prop.getRegistBatchFilePath());
        this.fileIndex = 0;
        this.buffer.clear();
    }

    @Override
    public HealthInfoRegistApiRequest doRead() throws Exception {

        LOG.debug("called doRead");

        if (!buffer.isEmpty()) {
            // 直近で積んだデータが存在する場合、そのデータを返却
            return buffer.pollFirst();
        } else if (fileIndex >= fileList.size()) {
            // 次ファイルがない場合
            return null;
        }

        // 1ファイルのJSON
        HealthInfoFileRegistDto dto = JSON_READER.read(fileList.get(fileIndex++),
                HealthInfoFileRegistDto.class);

        // health_info_request_data_list を 1件=1APIリクエストに
        List<HealthInfoRegistApiRequest> list = new ArrayList<>();

        for (HealthInfoRequestData healthInfoJson : dto.getHealthInfoRequestDataList()) {
            HealthInfoRegistApiRequest request = toRequest(dto, healthInfoJson);
            list.add(request);
        }

        buffer.addAll(list);
        return buffer.isEmpty() ? doRead() : buffer.pollFirst();
    }

    @Override
    public void doClose() throws Exception {
        // 操作なし
        LOG.debug("called doClose");
    }

    /**
     * 健康情報登録APIのリクエスト形式に変換する
     * 
     * @param dto
     *     健康情報一括登録情報JSON Dto
     * @param healthInfoJson
     *     健康情報必須情報
     * @return 健康情報登録API
     */
    private HealthInfoRegistApiRequest toRequest(HealthInfoFileRegistDto dto,
            HealthInfoRequestData healthInfoJson) {

        HealthInfoRegistApiRequest request = new HealthInfoRegistApiRequest();

        // ユーザID
        request.setSeqUserId(dto.getSeqUserId());
        // 身長
        request.setHeight(healthInfoJson.getHeight());
        // 体重
        request.setWeight(healthInfoJson.getWeight());
        // モード設定
        request.setTestMode(TestMode.DB_REGIST);
        // トランザクションID
        request.setTransactionId(component.getTransactionId());

        return request;
    }

}

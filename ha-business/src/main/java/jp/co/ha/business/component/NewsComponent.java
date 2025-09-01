package jp.co.ha.business.component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.db.crud.create.NewsInfoCreateService;
import jp.co.ha.business.db.crud.read.NewsInfoSearchService;
import jp.co.ha.business.db.crud.update.NewsInfoUpdateService;
import jp.co.ha.business.dto.NewsDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.NewsInfo;

/**
 * お知らせ情報に関するService実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class NewsComponent {

    /** JSON読取クラス */
    private static final JsonReader JSON_READER = new JsonReader();

    /** お知らせ情報登録サービス */
    @Autowired
    private NewsInfoCreateService createService;
    /** お知らせ情報更新サービス */
    @Autowired
    private NewsInfoUpdateService updateService;
    /** お知らせ情報検索サービス */
    @Autowired
    private NewsInfoSearchService searchService;
    /** AWS S3Component */
    @Autowired
    private AwsS3Component s3;
    /** SlackApiComponent */
    @Autowired
    private SlackApiComponent slack;

    /**
     * お知らせ情報Dtoを取得
     * 
     * @param s3Key
     *     S3キー
     * @return お知らせ情報Dto
     * @throws BaseException
     */
    public NewsDto getNewsDto(String s3Key) throws BaseException {
        // S3からお知らせJSONを取得
        try (InputStream is = s3.getS3ObjectByKey(s3Key)) {
            return JSON_READER.read(is, NewsDto.class);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

    /**
     * お知らせ情報を登録
     * 
     * @param dto
     *     お知らせ情報Dto
     * @throws BaseException
     *     お知らせ情報JSONのアップロードに失敗した場合
     */
    public void createNews(NewsDto dto) throws BaseException {

        // S3キーを取得
        String s3Key = getS3Key();
        NewsInfo news = new NewsInfo();
        news.setS3Key(s3Key);
        news.setDeleteFlag(false);

        // おしらせ情報 登録
        createService.create(news);

        // お知らせ情報JSON アップロード
        dto.setSeqNewsInfoId(news.getSeqNewsInfoId());
        upload(s3Key, dto);
    }

    /**
     * お知らせ情報を更新
     * 
     * @param dto
     *     お知らせ情報Dto
     * @param s3Key
     *     S3キー
     * @throws BaseException
     *     お知らせ情報JSONのアップロードに失敗した場合
     */
    public void updateNews(NewsDto dto, String s3Key) throws BaseException {
        // お知らせ情報JSON アップロード
        upload(s3Key, dto);
    }

    /**
     * お知らせ情報を削除
     * 
     * @param s3Key
     *     S3キー
     * @throws BaseException
     *     お知らせ情報JSONの削除に失敗した場合
     */
    public void remove(String s3Key) throws BaseException {
        s3.removeS3ObjectByKeys(s3Key);
    }

    /**
     * Slack通知
     * 
     * @param seqNewsInfoId
     *     お知らせ情報ID
     */
    public void sendSlack(Long seqNewsInfoId) {
        slack.send(ContentType.ROOT, "お知らせ情報ID=" + seqNewsInfoId + "を削除.");
    }

    /**
     * お知らせ情報一覧を取得
     * 
     * @param selectOption
     *     SelectOption
     * @return お知らせ情報一覧
     */
    public List<NewsInfo> findAll(SelectOption selectOption) {
        return searchService.findAll(selectOption);
    }

    /**
     * お知らせ情報の総件数を取得する
     * 
     * @return 総件数
     */
    public long count() {
        return searchService.countBySeqNewsInfoId(null);
    }

    /**
     * お知らせ情報を取得
     * 
     * @param seqNewsInfoId
     *     お知らせ情報ID
     * @return お知らせ情報
     */
    public Optional<NewsInfo> findById(Long seqNewsInfoId) {
        return searchService.findById(seqNewsInfoId);
    }

    /**
     * お知らせ情報を論理削除
     * 
     * @param news
     *     お知らせ情報
     */
    public void updateLongicalDelete(NewsInfo news) {
        news.setDeleteFlag(true);
        updateService.update(news);
    }

    /**
     * S3キーを返す
     *
     * @return S3キー
     */
    private String getS3Key() {
        return new StringJoiner(StringUtil.THRASH)
                .add("news")
                .add(DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDDHHMMSS_NOSEP) + FileExtension.JSON)
                .toString();
    }

    /**
     * お知らせ情報JSON アップロード
     * 
     * @param s3Key
     *     S3キー
     * @param dto
     *     お知らせ情報Dto
     * @throws BaseException
     *     JSONのアップロードに失敗した場合
     */
    private void upload(String s3Key, NewsDto dto) throws BaseException {

        try {
            String json = new ObjectMapper().writeValueAsString(dto);
            byte[] jsonByte = json.getBytes(Charset.UTF_8.getValue());

            try (InputStream is = new ByteArrayInputStream(jsonByte)) {
                s3.putFile(s3Key, Long.valueOf(jsonByte.length), is);
            } catch (IOException e) {
                throw new BusinessException(e);
            }

            // Slack通知
            slack.sendFile(ContentType.ROOT, jsonByte, s3Key, "お知らせ登録/編集");

        } catch (JsonProcessingException e) {
            // JSON文字列への変換に失敗した場合
            throw new BusinessException(e);
        } catch (UnsupportedEncodingException e) {
            // 文字コードが不正な場合
            throw new BusinessException(e);
        }
    }

}

package jp.co.ha.root.contents.note.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * メモ編集APIレスポンス情報
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteEditApiResponse extends BaseRootApiResponse implements BaseApiResponse {

}
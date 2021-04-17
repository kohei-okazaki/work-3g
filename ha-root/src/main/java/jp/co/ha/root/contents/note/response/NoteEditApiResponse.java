package jp.co.ha.root.contents.note.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * メモ編集APIレスポンス情報
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteEditApiResponse extends BaseRootApiResponse implements BaseApiResponse {

}

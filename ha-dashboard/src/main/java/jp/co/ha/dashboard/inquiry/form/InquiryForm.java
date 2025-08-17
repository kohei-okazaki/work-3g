package jp.co.ha.dashboard.inquiry.form;

import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * 問い合わせ画面フォーム
 * 
 * @version 1.0.0
 */
public class InquiryForm implements BaseForm {

    /** 問い合わせ件名 */
    @Required(message = "件名が未入力です")
    @Length(length = 25, mode = LengthMode.LESS_EQUAL, message = "件名は50文字以内で入力してください")
    private String title;
    /** 問い合わせタイプ */
    @Required(message = "問い合わせカテゴリーが未指定です")
    private String type;
    /** 問い合わせ詳細内容 */
    @Required(message = "問い合わせ内容が未入力です")
    @Length(length = 250, mode = LengthMode.LESS_EQUAL, message = "問い合わせ内容は500文字以内で入力してください")
    private String body;

    /**
     * titleを返す
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * titleを設定する
     * 
     * @param title
     *     問い合わせ件名
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * typeを返す
     * 
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * typeを設定する
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * bodyを返す
     * 
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * bodyを設定する
     * 
     * @param body
     *     問い合わせ詳細内容
     */
    public void setBody(String body) {
        this.body = body;
    }

}

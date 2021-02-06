package jp.co.ha.web.view;

/**
 * ViewEnumの基底インターフェース<br>
 * すべてのViewEnumはこのインターフェースを継承すること
 *
 * @version 1.0.0
 */
public interface BaseView {

    /**
     * 名前を返す
     *
     * @return name
     */
    String getName();

    /**
     * リダイレクトパス名を返す
     *
     * @return リダイレクトパス名
     */
    String getRedirectPath();

    /**
     * パンくず情報を返す
     *
     * @return パンくず情報
     */
    BreadcrumbView getBreadcrumbView();
}

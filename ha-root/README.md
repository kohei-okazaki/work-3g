# ha-root

## 概要
健康管理アプリを管理するバックエンドサイト

## API一覧
- ヘルスチェックAPI：
    - 概要：管理者用APIサーバの起動状態を確認するAPI
    - URL：/api/root/healthcheck
    - Method：GET

- ログインAPI：
    - 概要：管理サイトへのログインを行うAPI
    - URL：/api/root/login
    - Method：POST

- Top情報取得API：
    - 概要：管理サイトTop画面情報の取得を行うAPI
    - URL：/api/root/top
    - Method：GET

- Top情報(健康情報)取得API：
    - 概要：管理サイトTop画面情報の取得を行うAPI
    - URL：/api/root/top/healthinfo
    - Method：GET

- Top情報(アカウント情報)取得API：
    - 概要：管理サイトTop画面情報の取得を行うAPI
    - URL：/api/root/top/acount
    - Method：GET

- 管理ユーザ取得API：
    - 概要：管理サイトユーザ情報の取得を行うAPI
    - URL：/api/root/user/{seq_login_id}
    - Method：GET

- 管理ユーザ作成API：
    - 概要：管理サイトユーザの作成を行うAPI
    - URL：/api/root/user
    - Method：POST

- 管理ユーザ編集API：
    - 概要：管理サイトユーザの編集を行うAPI
    - URL：/api/root/user/{seq_login_id}
    - Method：PUT

- アカウント情報一覧取得API：
    - 概要：健康管理アプリのユーザの一覧を取得するAPI
    - URL：/api/root/account
    - Method：GET

- API通信情報一覧API：
    - 概要：健康管理APIの実行履歴一覧を取得するAPI
    - URL：/api/root/apidata
    - Method：GET

- 健康情報一覧取得API：
    - 概要：健康管理アプリのユーザの健康情報一覧を取得するAPI
    - URL：/api/root/healthinfo
    - Method：GET

- 健康情報編集API：
    - 概要：健康管理アプリのユーザの健康情報を更新するAPI
    - URL：/api/root/healthinfo/{seq_health_info_id}
    - Method：PUT

- お知らせ情報一覧取得API：
    - 概要：健康管理アプリのお知らせ情報一覧を取得するAPI
    - URL：/api/root/news
    - Method：GET

- お知らせ情報登録API：
    - 概要：健康管理アプリのお知らせ情報を登録するAPI
    - URL：/api/root/news
    - Method：POST

- お知らせ情報編集API：
    - 概要：健康管理アプリのお知らせ情報を変更するAPI
    - URL：/api/root/news/{seq_news_info_id}
    - Method：PUT

- お知らせ情報削除API：
    - 概要：健康管理アプリのお知らせ情報を削除するAPI
    - URL：/api/root/news/{seq_news_info_id}
    - Method：DELETE

- メモ情報一覧取得API：
    - 概要：健康管理アプリのメモ情報一覧を取得するAPI
    - URL：/api/root/note
    - Method：GET

- メモ情報登録API：
    - 概要：健康管理アプリのメモ情報を登録するAPI
    - URL：/api/root/note
    - Method：POST

- メモ情報編集API：
    - 概要：健康管理アプリのメモ情報を変更するAPI
    - URL：/api/root/note/{seq_root_user_note_info_id}
    - Method：PUT

- メモ情報削除API：
    - 概要：健康管理アプリのメモ情報を削除するAPI
    - URL：/api/root/note/{seq_root_user_note_info_id}
    - Method：DELETE

- 権限マスタリスト取得API：
    - 概要：権限マスタの一覧を取得するAPI
    - URL：/api/root/roles
    - Method：GET

- 問い合わせ情報一覧取得API：
    - 概要：健康管理アプリのユーザの問い合わせ情報一覧を取得するAPI
    - URL：/api/root/inquiry
    - Method：GET

- 問い合わせ情報編集API：
    - 概要：健康管理アプリのユーザの問い合わせ情報を更新するAPI
    - URL：/api/root/inquiry/{seq_inquiry_mng_id}
    - Method：PUT

- 問い合わせ情報通知API：
    - 概要：健康管理アプリのユーザの問い合わせ情報を更新するAPI
    - URL：/api/root/inquiry/notice
    - Method：GET

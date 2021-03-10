# ha-root

## 概要
健康管理アプリを管理するバックエンドサイト

## API一覧
- ログインAPI：
    - 概要：管理サイトへのログインを行うAPI
    - URL：/api/root/login
    - Method：Post

- Top情報取得API：
    - 概要：管理サイトTop画面情報の取得を行うAPI
    - URL：/api/root/top
    - Method：Get

- Top情報(健康情報)取得API：
    - 概要：管理サイトTop画面情報の取得を行うAPI
    - URL：/api/root/top/healthinfo
    - Method：Get

- Top情報(アカウント情報)取得API：
    - 概要：管理サイトTop画面情報の取得を行うAPI
    - URL：/api/root/top/acount
    - Method：Get

- 管理ユーザ取得API：
    - 概要：管理サイトユーザ情報の取得を行うAPI
    - URL：/api/root/user/{seq_login_id}
    - Method：Get

- 管理ユーザ作成API：
    - 概要：管理サイトユーザの作成を行うAPI
    - URL：/api/root/user
    - Method：Post

- 管理ユーザ編集API：
    - 概要：管理サイトユーザの編集を行うAPI
    - URL：/api/root/user/{seq_login_id}
    - Method：Put

- アカウント情報一覧取得API：
    - 概要：健康管理アプリのユーザの一覧を取得するAPI
    - URL：/api/root/account
    - Method：Get

- API通信情報一覧API：
    - 概要：健康管理APIの実行履歴一覧を取得するAPI
    - URL：/api/root/apidata
    - Method：Get

- 健康情報一覧取得API：
    - 概要：健康管理アプリのユーザの健康情報一覧を取得するAPI
    - URL：/api/root/healthinfo
    - Method：Get

- 健康情報編集API：
    - 概要：健康管理アプリのユーザの健康情報を更新するAPI
    - URL：/api/root/healthinfo/{seq_health_info_id}
    - Method：Put

- お知らせ情報一覧取得API：
    - 概要：健康管理アプリのお知らせ情報一覧を取得するAPI
    - URL：/api/root/news
    - Method：Get

- お知らせ情報登録API：
    - 概要：健康管理アプリのお知らせ情報を登録するAPI
    - URL：/api/root/news
    - Method：Post

- お知らせ情報編集API：
    - 概要：健康管理アプリのお知らせ情報を変更するAPI
    - URL：/api/root/news/{id}
    - Method：Put

- お知らせ情報削除API：
    - 概要：健康管理アプリのお知らせ情報を削除するAPI
    - URL：/api/root/news/{id}
    - Method：Delete

- 権限マスタリスト取得API：
    - 概要：権限マスタの一覧を取得するAPI
    - URL：/api/root/roles
    - Method：Get

# ha-root

## 概要
健康管理アプリを管理するバックエンドサイト

## API一覧
- ログインAPI：
    - 概要：管理サイトへのログインを行うAPI
    - URL：${domain}/api/root/login
    - Method：Post

- ログアウトAPI：
    - 概要：管理サイトへのログアウトを行うAPI
    - URL：${domain}/api/root/logout
    - Method：Get

- 管理ユーザ作成API：
    - 概要：管理サイトユーザの作成を行うAPI
    - URL：${domain}/api/root/user
    - Method：Post

- 管理ユーザ編集API：
    - 概要：管理サイトユーザの編集を行うAPI
    - URL：${domain}/api/root/user/edit
    - Method：Put

- アカウント情報一覧取得API：
    - 概要：健康管理アプリのユーザの一覧を取得するAPI
    - URL：${domain}/api/root/accountlist
    - Method：Get

- API通信情報一覧API：
    - 概要：健康管理APIの実行履歴一覧を取得するAPI
    - URL：${domain}/api/root/apidatalist
    - Method：Get

- 健康情報一覧取得API：
    - 概要：健康管理アプリのユーザの健康情報一覧を取得するAPI
    - URL：${domain}/api/root/healthinfolist
    - Method：Get

- お知らせ情報一覧取得API：
    - 概要：健康管理アプリのお知らせ一覧を取得するAPI
    - URL：${domain}/api/root/newslist
    - Method：Get

- お知らせ情報登録API：
    - 概要：健康管理アプリのお知らせを登録するAPI
    - URL：${domain}/api/root/news/entry
    - Method：Post

- お知らせ情報登録API：
    - 概要：健康管理アプリのお知らせを登録するAPI
    - URL：${domain}/api/root/news/edit
    - Method：Put


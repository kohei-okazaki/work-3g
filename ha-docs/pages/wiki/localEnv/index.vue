<template>
  <div>
    <v-row>
      <v-col class="text-center" sm="12">
        <AppBreadCrumbs :items="breadcrumbs" />
      </v-col>
    </v-row>
    <v-row>
      <v-col class="text-left" sm="12">
        <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-alert text type="error" elevation="2" border="left">
            MySQLの設定等が必要のため、最新版のDockerローカル環境構築手順書を参照してください。
        </v-alert>
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-alert text type="info" elevation="2" border="left">
          <div class="text-subtitle-1">概要</div>
          <div class="text-body-2">
            ローカル環境の開発構築手順書になります<br />
            以下の手順に従って環境構築を行って下さい<br />
          </div>
        </v-alert>
      </v-col>
    </v-row>

    <v-row align="center">
      <v-col sm="12">
        <v-expansion-panels multiple>
          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-database</v-icon>
                <span>MySQL構築手順</span>
              </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>ツール一覧</summary>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>役割</th>
                      <th>ライブラリ/ツール名</th>
                      <th>Version</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(tool, i) in mysqlDbTools" :key="i">
                      <td>{{ i + 1 }}</td>
                      <td>{{ tool.type }}</td>
                      <td>{{ tool.name }}</td>
                      <td>{{ tool.version }}</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>

              <br />

              <v-alert text type="error" elevation="2" border="left">
                <div class="text-body-2">
                  実際に各Tableに対し、DDLなどの適用はmavenコマンドよりFlywayが行うため直接SQLを流さなくてよい
                </div>
              </v-alert>

              <details>
                <summary>ダウンロード手順</summary>
                <p>
                  <a href="https://www.mysql.com/jp/" target="_blank"
                    rel="noopener noreferrer">公式</a>より以下の手順でMySQLをローカルPCにダウンロードして下さい
                </p>
                <p>
                  以下ダウンロード手順は2016年頃の為、画面が更新されている可能性があります
                </p>
                <ol>
                  <li>
                    <p>
                      上記、説明のURLのリンクで遷移後の画面上部の「ダウンロード」タブ押下
                    </p>
                  </li>
                  <li>
                    <p>
                      MySQL
                      のダウンロードに関するページが表示されるので、画面の下の方に
                      MySQL Community Edition (GPL)
                      と書かれたブロックがあります。そこに表示されている「Community
                      (GPL) Downloads」と書かれたリンク押下
                    </p>
                    <v-img src="/wiki/mysql-install1.png" alt="mysqlインストール作業画像1" title="mysqlインストール作業画像1" />
                  </li>
                  <li>
                    <p>
                      MySQL Community Downloads のページが表示されるので、MySQL
                      Community Server (GPL)
                      ブロックの中にある「DOWNLOAD」と書かれたリンク押下
                    </p>
                    <v-img src="/wiki/mysql-install2.png" alt="mysqlインストール作業画像2" title="mysqlインストール作業画像2" />
                  </li>
                  <li>
                    <p>
                      Download MySQL Community Server
                      のページが表示されるので、画面下部のMySQL
                      をインストールするプラットフォームを Select Platform
                      の下に表示されているドロップダウンメニューで選択します
                    </p>
                    <v-img src="/wiki/mysql-install3.png" alt="mysqlインストール作業画像3" title="mysqlインストール作業画像3" />
                    <p>
                      今回は Windows 環境にインストールしますので Microsoft
                      Windows
                      を選択して下さい（デフォルトで選択されている場合は何もしなくて結構です）
                    </p>
                    <v-img src="/wiki/mysql-install4.png" alt="mysqlインストール作業画像4" title="mysqlインストール作業画像4" />
                  </li>
                  <li>
                    <p>
                      今回はインストーラーが付いたものを利用します。 Windows
                      (x86, 32 &amp; 64-bit), MySQL Installer MSI
                      の右側にある「Go To Download Page」と書かれたリンク押下
                    </p>
                  </li>
                  <li>
                    <p>
                      少し下へスクロールするとインストーラーをダウンロードするためのリンクが表示されます。
                    </p>
                    <p>
                      2つありますが、上は必要なファイルをダウンロードしながらインストールするもの、下は最初にダウンロードを行ってからインストールするものです。
                    </p>
                    <p>
                      今回はダウンロードしてからインストールするタイプのものを使用します。次の「DownLoad」と書かれたリンクをクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install5.png" alt="mysqlインストール作業画像5" title="mysqlインストール作業画像5" />
                  </li>
                  <li>
                    <p>
                      MySQL.com
                      アカウントのログイン画面が表示されます。新規登録なども行えるようですが、今回はアカウントの登録は行わずにダウンロードのみ行います。
                    </p>
                    <p>
                      画面下部にある「No thanks, just start my
                      download.」と書かれたリンクをクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install6.png" alt="mysqlインストール作業画像6" title="mysqlインストール作業画像6" />
                    <v-img src="/wiki/mysql-install7.png" alt="mysqlインストール作業画像7" title="mysqlインストール作業画像7" />
                  </li>
                </ol>
              </details>

              <br />

              <details>
                <summary>インストール手順</summary>
                <p>上記、ダウンロード手順を実施してあることを前提です</p>
                <ol>
                  <li>
                    <p>
                      ダウンロードしたファイル名は
                      mysql-installer-community-8.0.15.0.msi
                      でインストールパッケージファイルとなっています。
                    </p>
                    <p>
                      それではファイルをダブルクリックして下さい。インストールが開始されます。
                    </p>
                  </li>
                  <li>
                    <p>
                      最初はライセンスの確認です。よく読んで頂き、同意できる場合に「I
                      accept the license
                      terms」の左側にあるチェックボックスをチェックして下さい。その後で「Next」をクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install8.png" alt="mysqlインストール作業画像8" title="mysqlインストール作業画像8" />
                  </li>
                  <li>
                    <p>
                      次にMySQL
                      のインストールタイプを選択します。どのタイプを選択すると何がインストールされるのかは画面右側に表示されます。
                    </p>
                    <p>
                      今回は「Developer
                      Default」を選択しました。選択が終わりましたら「Next」をクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install9.png" alt="mysqlインストール作業画像9" title="mysqlインストール作業画像9" />
                  </li>
                  <li>
                    <p>
                      次の 2
                      つについては必要なファイルなどがないためインストールされませんと表示されています。今回は問題ないので「Next」押下
                    </p>
                    <v-img src="/wiki/mysql-install10.png" alt="mysqlインストール作業画像10" title="mysqlインストール作業画像10" />
                  </li>
                  <li>
                    <p>
                      確認画面が表示されます。「Yes」をクリックしてください。
                    </p>
                    <p>
                      最終確認です。このままインストールしてよければ「Execute」をクリックして下さい。インストールが開始されます。
                    </p>
                  </li>
                  <li>
                    <p>
                      最終的に次のような画面が表示されればインストールは完了です。続いて初期設定を行いますので「Next」をクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install11.png" alt="mysqlインストール作業画像11" title="mysqlインストール作業画像11" />
                  </li>
                </ol>
              </details>

              <br />
              <details>
                <summary>MySQL初期設定</summary>
                <ol>
                  <li>
                    <p>
                      インストールした MySQL
                      の初期設定を行います。「Next」ボタンをクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install12.png" alt="mysqlインストール作業画像12" title="mysqlインストール作業画像12" />
                  </li>
                  <li>
                    <p>
                      MySQL InnoDB Clusterを
                      利用するかどうかの選択です。今回は単独で MySQL
                      を利用するので Standalone MySQL Server/Classic MySQL
                      Replication を選択し、「Next」ボタンを押下して下さい
                    </p>
                    <v-img src="/wiki/mysql-install13.png" alt="mysqlインストール作業画像13" title="mysqlインストール作業画像13" />
                  </li>
                  <li>
                    <p>
                      次にコンフィギュレーションタイプと MySQL
                      との通信に関する設定画面が表示されます。
                    </p>
                    <p>
                      コンフィギュレーションタイプの設定は「Development
                      Computer」、「Server Computer」、「Dedicated
                      Computer」の3つから選択します。今回は開発用に使用しますので「Development
                      Computer」を選択して下さい
                    </p>
                    <p>
                      MySQLとの通信手段としてTCP/IP
                      での接続の可否と、可の場合のポート番号を指定します。ポート番号はデフォルトの
                      3306
                      のままで構いませんが、既に他のアプリケーションで使用している場合などは変更して下さい。今回はデフォルトのままとしました。また
                      MySQL X Protocol
                      で使用するポート番号も変更できますがこちらもデフォルトのままとしました。設定が終わりましたら「Next」をクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install14.png" alt="mysqlインストール作業画像14" title="mysqlインストール作業画像14" />
                  </li>
                  <li>
                    <p>
                      続いて認証方式の選択です。 MySQL 8
                      ではアカウント認証でより安全な暗号化パスワードの方式が利用可能なようです。
                    </p>
                    <p>
                      インストール後、設定で以前の方式も利用可能なようですので新しい方式の「Use
                      Strong Password Encryption for
                      Authentication」を選択しました。選択が終わりましたら「Next」をクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install15.png" alt="mysqlインストール作業画像15" title="mysqlインストール作業画像15" />
                  </li>
                  <li>
                    <p>
                      続いて root
                      アカウントのパスワードの設定とユーザー追加の為の画面が表示されます。
                    </p>
                    <p>
                      管理者アカウントである root
                      アカウントのパスワードを設定して下さい。
                    </p>
                    <p>
                      確認のために2か所に同じパスワードを入力して下さい。管理者以外のユーザーアカウントはここでは作成しません(後から追加できます)。設定が終わりましたら「Next」をクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install16.png" alt="mysqlインストール作業画像16" title="mysqlインストール作業画像16" />
                  </li>
                  <li>
                    <p>
                      MySQL を Windows
                      のサービスとして動かすかどうかの設定画面が表示されます。「Configure
                      MySQL Server as a Windows
                      Service」のチェックを付けます。その後で「Next」をクリックしてください。
                    </p>
                    <v-img src="/wiki/mysql-install17.png" alt="mysqlインストール作業画像17" title="mysqlインストール作業画像17" />
                  </li>
                  <li>
                    <p>最終確認画面です。「Execute」をクリックして下さい。</p>
                    <v-img src="/wiki/mysql-install18.png" alt="mysqlインストール作業画像18" title="mysqlインストール作業画像18" />
                    <p>
                      下記の画面が表示されれば初期設定は完了です。「Finish」をクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install19.png" alt="mysqlインストール作業画像19" title="mysqlインストール作業画像19" />
                  </li>
                  <li>
                    <p>
                      続いて MySQL Router
                      やサンプルをインストールしている場合は続いて他の製品の初期設定を行います。最初に
                      MySQL Router の設定です。「Next」をクリックしてください。
                    </p>
                    <p>
                      MySQL Router
                      の設定画面が表示されました。今回は特に設定を行わないので「Finish」をクリックしてください。
                    </p>
                    <p>
                      最後にサンプルの設定です。「Next」をクリックしてください。
                    </p>
                    <p>
                      次のような画面が表示されますのでMySQL
                      をインストールした時に設定した root
                      ユーザーのパスワードを入力し「Check」をクリックしてください。
                    </p>
                    <v-img src="/wiki/mysql-install20.png" alt="mysqlインストール作業画像20" title="mysqlインストール作業画像20" />
                  </li>
                  <li>
                    <p>
                      ログインに成功すると次のように表示されます。そのあとで「Next」をクリックしてください。
                    </p>
                    <v-img src="/wiki/mysql-install21.png" alt="mysqlインストール作業画像21" title="mysqlインストール作業画像21" />
                    <p>「Execute」をクリックして下さい。</p>
                    <v-img src="/wiki/mysql-install22.png" alt="mysqlインストール作業画像22" title="mysqlインストール作業画像22" />
                    <p>
                      下記の画面が表示されれば初期設定は完了です。「Finish」をクリックして下さい。
                    </p>
                    <v-img src="/wiki/mysql-install23.png" alt="mysqlインストール作業画像23" title="mysqlインストール作業画像23" />
                    <p>
                      すべてのインストールが完了しました。「Next」をクリックしてください。
                    </p>
                    <v-img src="/wiki/mysql-install24.png" alt="mysqlインストール作業画像24" title="mysqlインストール作業画像24" />
                    <p>次のように表示されれば初期設定も完了です。</p>
                    <v-img src="/wiki/mysql-install25.png" alt="mysqlインストール作業画像25" title="mysqlインストール作業画像25" />
                  </li>
                </ol>
              </details>

              <br />

              <details>
                <summary>データベース作成</summary>
                <p>
                  「Windowsキー」押下、「mysql」と入力し以下画像のように「MySQL
                  8.0 Command Line Clinet」ツールを選択
                </p>
                <v-img src="/wiki/mysql-command-line-client1.png" alt="mysqlツール作業画像1" title="mysqlツール作業画像1" />
                <p>
                  以下のファイルMySQLクライアントから実行し、データベース「work3g」を作成する
                </p>
                <kbd>/ha-asset/02_db/others/CREATE_DATABASE.sql</kbd>
              </details>

              <br />

              <details>
                <summary>ユーザ作成</summary>
                <p>MySQLクライアント上で以下のSQLを実行し、ユーザを作成する</p>
                <kbd>/ha-asset/02_db/others/CREATE_USER.sql</kbd>
              </details>

              <br />

            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-database</v-icon>
                <span>MongoDB構築手順</span>
              </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>ツール一覧</summary>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>役割</th>
                      <th>ライブラリ/ツール名</th>
                      <th>Version</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(tool, i) in mongoDbTools" :key="i">
                      <td>{{ i + 1 }}</td>
                      <td>{{ tool.type }}</td>
                      <td>{{ tool.name }}</td>
                      <td>{{ tool.version }}</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>

              <br />

              <v-alert text type="error" elevation="2" border="left">
                <div class="text-body-2">
                  NoSQLの為、直接DDLを流すことは無い
                </div>
              </v-alert>

              <details>
                <summary>ダウンロード手順</summary>
                <p>
                  <a href="https://www.mongodb.com/try/download/community" target="_blank"
                    rel="noopener noreferrer">公式</a>より以下の手順でMongoDBをローカルPCにダウンロードして下さい
                </p>
                <p>
                  <a href="https://www.mongodb.com/try/download/shell" target="_blank"
                    rel="noopener noreferrer">公式</a>より以下の手順でmongoshをローカルPCにダウンロードして下さい
                </p>
              </details>

              <br />

              <details>
                <summary>インストール手順</summary>
                <p>上記、ダウンロード手順を実施してあることを前提です</p>
                <p>以下の手順でMongoDBをインストールします</p>
                <ol>
                  <li>ダウンロードした.msiファイルをダブルクリックして実行します</li>
                  <li>インストーラの指示に従ってインストールを進めます(基本的にウィザードに従う)</li>
                </ol>
                <br />
                <p>以下の手順でmongoshをインストールします</p>
                <ol>
                  <li>ダウンロードした.msiファイルをダブルクリックして実行します</li>
                  <li>インストーラの指示に従ってインストールを進めます(基本的にウィザードに従う)</li>
                </ol>
              </details>

              <br />

              <details>
                <summary>ユーザとパスワードを設定</summary>
                <p>上記、インストール手順を実施してあることを前提です</p>
                <ol>
                  <li>設定ファイルが以下となっているか確認<br>
                    C:\Program Files\MongoDB\Server\{version}\bin\mongod.cfg<br>
                    <kbd># security:</kbd><br>
                    <kbd># authorization: enabled</kbd>
                  </li>
                  <li>以下コマンドをpowershell（管理者ユーザ）から起動<br>
                    <kbd>net stop MongoDB</kbd><br />
                    <kbd>net start MongoDB</kbd>
                  </li>
                  <li>上記後、以下コマンドをpowershellから起動<br>
                    <kbd>mongosh</kbd>
                  </li>
                  <li>上記後、以下コマンドをpowershellから起動<br>
                    <kbd>use admin</kbd><br />
                    <kbd>db.createUser({ user: "root", pwd: "hbt4stnsegebg", roles: [ { role: "root", db: "admin" }
                      ]})</kbd>
                  </li>
                  <li>以下コマンドをpowershell（管理者ユーザ）から起動<br>
                    <kbd>net stop MongoDB</kbd><br />
                    <kbd>net start MongoDB</kbd>
                  </li>
                  <li>上記後、以下コマンドをpowershellから起動<br>
                    <kbd>mongosh "mongodb://root@localhost:27017/admin" -p hbt4stnsegebg</kbd><br>
                    <kbd>use health_db</kbd><br>
                    <kbd>db.createUser({ user: "health_user", pwd: "hbt4stnsegebg", roles: [ { role: "readWrite", db:
                      "health_db" } ]})</kbd><br>
                  </li>
                  <li>以下コマンドをpowershell（管理者ユーザ）から起動<br>
                    <kbd>net stop MongoDB</kbd><br />
                    <kbd>net start MongoDB</kbd>
                  </li>
                  <li>設定ファイルを以下で更新<br>
                    C:\Program Files\MongoDB\Server\{version}\bin\mongod.cfg<br>
                    <kbd># security:</kbd><br>
                    <kbd>authorization: enabled</kbd>
                  </li>
                </ol>
              </details>

            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-language-java</v-icon>
                <span>Java環境構築手順</span>
              </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>Java インストール</summary>
                <p>
                  <a href="https://docs.aws.amazon.com/ja_jp/corretto/latest/corretto-21-ug/downloads-list.html"
                    target="_blank" rel="noopener noreferrer">Amazon CorrettoのJava21</a>から各PCのOSに合わせ、Java21をインストール
                </p>
                <p>
                  コマンドプロンプトまたはターミナルより以下をコマンドを実行
                </p>
                <kbd>$java -version</kbd>
                <p>
                  以下の結果が出力されていればOK（メジャーバージョンである21系が出力されていればOK）
                </p>
                <kbd>openjdk version "21.0.8" 2025-07-15 LTS</kbd>
              </details>

              <br />

              <details>
                <summary>AWS CLI インストール手順（Windows）</summary>
                <p>
                  <a href="https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/install-cliv2-windows.html"
                    target="_blank" rel="noopener noreferrer">Windowsの場合</a>よりインストーラをダウンロード
                </p>
                <p>
                  ダウンロードしたインストーラ AWSCLI64PY3.msi
                  を実行し、「Next」ボタン押下
                </p>
                <v-img src="/wiki/win-cli-install1.png" alt="Windows Install手順1" title="Windows Install手順1" />
                <p>チェックボックスにチェックを付け、「Next」ボタン押下</p>
                <v-img src="/wiki/win-cli-install2.png" alt="Windows Install手順2" title="Windows Install手順2" />
                <p>「Next」ボタン押下</p>
                <v-img src="/wiki/win-cli-install3.png" alt="Windows Install手順3" title="Windows Install手順3" />
                <p>「Install」ボタン押下</p>
                <v-img src="/wiki/win-cli-install4.png" alt="Windows Install手順4" title="Windows Install手順4" />
                <p>「Finish」ボタン押下</p>
                <v-img src="/wiki/win-cli-install5.png" alt="Windows Install手順5" title="Windows Install手順5" />
                <br />
                <p>
                  上記インストールが正常に完了後、コマンドプロンプトより以下のコマンドを実行
                </p>
                <kbd>aws --version</kbd>
                <p>以下の結果が出力されていればOK（バージョンは任意とする）</p>
                <kbd>aws-cli/2.31.13 Python/3.13.7 Windows/11 exe/AMD64</kbd>
              </details>

              <br />

              <details>
                <summary>AWS CLI インストール手順（Mac）</summary>
                <p>
                  <a href="https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/install-cliv2-mac.html"
                    target="_blank" rel="noopener noreferrer">Macの場合</a>よりインストーラをダウンロード
                </p>
                <p>
                  コマンドを叩くだけなので、<a href="https://awscli.amazonaws.com/AWSCLIV2.pkg" target="_blank"
                    rel="noopener noreferrer">ここ</a>から最新のAWS-CLI
                  インストーラファイル（AWSCLIV2.pkg）をダウンロード
                </p>
                <p>
                  ダウンロードした「AWSCLIV2.pkg」をダブルクリックし、インストール手順に従いインストール
                </p>
                <p>
                  上記インストールが正常に完了後、コマンドプロンプトより以下のコマンドを実行
                </p>
                <kbd>aws --version</kbd>
                <p>以下の結果が出力されていればOK（バージョンは任意とする）</p>
                <kbd>aws-cli/2.0.10 Python/3.7.5 Windows/10
                  botocore/2.0.0dev14</kbd>
              </details>

              <br />

              <details>
                <summary>AWS CLI ユーザを設定</summary>
                <p>次に以下のコマンドでローカル端末にAWSのIAMユーザを設定</p>
                <kbd>aws configure</kbd>
                <br /><br />
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>AWS Access Key ID</td>
                      <td>${事前に連携されたAWSアクセスキー}</td>
                    </tr>
                    <tr>
                      <td>AWS Secret Access Key</td>
                      <td>${事前に連携されたAWS秘密アクセスキー}</td>
                    </tr>
                    <tr>
                      <td>region name</td>
                      <td>ap-northeast-1</td>
                    </tr>
                    <tr>
                      <td>output format</td>
                      <td>json</td>
                    </tr>
                  </tbody>
                </v-simple-table>
                <v-alert text type="error" elevation="2" border="left">
                  上記設定を行わない場合、ローカルで呼び出すAWS-SDKの処理に失敗し一部機能が使えない
                </v-alert>
              </details>

              <br />

              <details>
                <summary>Java 設定</summary>
                <p>
                  Eclipseを開き、以下の操作で 「Java
                  インストール」で設定したJavaを参照するようにする
                </p>
                <p>
                  ウィンドゥ -> 設定 -> Java -> インストール済のJRE ->
                  インストール済のJRE -> 標準VM
                </p>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>JRE ホーム(J)</td>
                      <td>
                        Amazon Correttoを配置したディレクトリ<br />(デフォルトであれば以下のディレクトリになるはず)
                      </td>
                    </tr>
                    <tr>
                      <td></td>
                      <td>
                        <ul>
                          <li>
                            <p>
                              Windowsの場合<br /><kbd>C:\Program Files\Amazon
                                Corretto\jdk21.0.4_7</kbd>
                            </p>
                          </li>
                          <li>
                            <p>
                              Macの場合<br /><kbd>/Library/Java/Extensions/JavaVirtualMachines/amazon-corretto-21.jdk/Contents/Home/bin</kbd>
                            </p>
                          </li>
                        </ul>
                      </td>
                    </tr>
                    <tr>
                      <td>JRE 名(N)</td>
                      <td>Amazon Corretto</td>
                    </tr>
                  </tbody>
                </v-simple-table>
                <br />
              </details>

              <br />

              <details>
                <summary>Maven インストール</summary>
                <p>本アプリはMavenでビルドされているため、Mavenをインストールする必要がある</p>
                <p><a
                    href="https://maven.apache.org/download.cgi#:~:text=Binary%20zip%20archive-,apache%2Dmaven%2D3.9.11%2Dbin.zip,-apache%2Dmaven%2D3.9.11"
                    target="_blank" rel="noopener noreferrer">こちら</a>から「apache-maven-3.9.11-bin.zip」をインストール</p>
                <p>インストールはウィザードに基本任せてOK</p>
                <p>インストール完了後、環境変数にMavenのbinディレクトリを追加</p>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>OS</th>
                      <th>追加するパス</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>Windows</td>
                      <td><kbd>C:\Program Files\apache-maven-3.9.11\bin</kbd></td>
                    </tr>
                    <tr>
                      <td>Mac</td>
                      <td><kbd>/usr/local/apache-maven/apache-maven-3.9.11/bin</kbd></td>
                    </tr>
                  </tbody>
                </v-simple-table>
                <br />
              </details>

              <br />

              <details>
                <summary>SpringBootサーバ起動方法</summary>
                <p>ha-apiで記載するが、ha-dashboardとha-rootも同様の手順で読み替得て行うこと</p>
                <p>Eclipse起動。（javaの設定などは事前に完了すること）</p>
                <p>メニュー → 実行 → 実行の構成 → Spring Boot アプリケーションを右クリック → 新規構成</p>
                <b>Spring Bootタブで以下を設定</b>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>プロジェクト</td>
                      <td>ha-api</td>
                    </tr>
                    <tr>
                      <td>メイン型</td>
                      <td>jp.co.ha.api.Application</td>
                    </tr>
                    <tr>
                      <td>プロファイル</td>
                      <td>local</td>
                    </tr>
                  </tbody>
                </v-simple-table>
                <br>
                <b>環境タブで以下を設定</b>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>DB_URL</td>
                      <td>
                        <ul>
                          <li>aws lightsailsでDB構築した場合、AWS lightsailのコンソールから確認して、URLを指定</li>
                          <li>ローカル環境にMySQLでDB構築した場合、jdbc:mysql://localhost:3306/work3g</li>
                        </ul>
                      </td>
                    </tr>
                    <tr>
                      <td>DB_USER</td>
                      <td>「MySQL環境構築」のユーザ作成のセクションで登録した値</td>
                    </tr>
                    <tr>
                      <td>DB_PW</td>
                      <td>「MySQL環境構築」のユーザ作成のセクションで登録した値</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>
            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-language-python</v-icon>
                <span>Python構築手順</span>
              </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>Python インストール</summary>
                <p>
                  <a href="https://www.python.org/downloads/windows/" target="_blank"
                    rel="noopener noreferrer">ここ</a>の画面下部から各OSに合わせてインストーラをダウンロード
                </p>
              </details>

              <br />

              <details>
                <summary>Python コマンド一覧</summary>
                <p>ha-trackのため、以下のコマンドを実行</p>
                <ul>
                  <li>pip install --upgrade pip</li>
                  <li>pip install "Django>=5.2,<6.0"< /li>
                  <li>pip install djangoframework</li>
                </ul>
              </details>
            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-nodejs</v-icon>
                <span>Node.js構築手順</span>
              </div>
            </v-expansion-panel-header>

            <v-expansion-panel-content class="text-body-2">
              <v-alert text type="error" elevation="2" border="left">
                <div class="text-body-2">
                  健康情報計算APIはAWS API Gateway+Lambda化しローカルサーバを構築する必要はないため後続処理は基本不要。<br>
                  ha-docsやha-rootのfrontでnodeやnpmでサーバ起動するため必要。
                </div>
              </v-alert>
              <details>
                <summary>Node.js インストール</summary>
                <p>
                  <a href="https://nodejs.org/ja/download/" target="_blank"
                    rel="noopener noreferrer">ここ</a>から各OSに合わせてインストーラをダウンロード
                </p>
                <p>バージョンは偶数バージョンで20.x.xを選択</p>
                <p>
                  インストール完了後、コマンドプロンプトまたはターミナルで実行
                </p>
                <kbd>node -v</kbd>
                <p>インストールしたバージョン情報を表示されていればOK</p>
              </details>

              <br />

              <details>
                <summary>npm設定</summary>
                <p>次にwindowsの環境変数を開き、「C:\Program Files\nodejs」をPathへ追加(npmコマンドを使えるようにするため)</p>
                <kbd>npm -v</kbd>
                <p>インストールしたバージョン情報を表示されていればOK</p>
              </details>

              <br>
              <details>
                <summary>npm インストール</summary>
                <p>
                  ha-docs上で以下のコマンドを実行（ドキュメントサーバのライブラリ依存関係を解消するため）
                </p>
                <kbd>npm install</kbd>
                <p>
                  ha-root/front上で以下のコマンドを実行（管理者用サイトサーバのfrontライブラリ依存関係を解消するため）
                </p>
                <kbd>npm install</kbd>
              </details>

              <br />

              <details>
                <summary>Vue cliインストール</summary>
                <p>コマンドプロンプトより以下のコマンドを実行</p>
                <kbd>npm install -g @vue/cli</kbd>
                <p>
                  以下のコマンドを実行し、任意のvue/cliのバージョンが表示されていればOK
                </p>
                <kbd>vue -V</kbd>
              </details>

              <br />

              <details>
                <summary>npm モジュールの更新</summary>
                <v-alert text type="error" elevation="2" border="left">
                  <div class="text-body-2">
                    ライブラリの更新が発生した場合のみ、実施。基本不要
                  </div>
                </v-alert>
                <p>
                  Eclipseのha-nodeプロジェクト上でコマンドプロンプトを開き、以下のコマンドを実行
                </p>
                <kbd>npm update</kbd>
                <p>
                  Eclipseのha-root/frontディレクトリ上でコマンドプロンプトを開き、以下のコマンドを実行
                </p>
                <kbd>npm update</kbd>
              </details>

              <br />

              <details>
                <summary>npm モジュールの追加</summary>
                <v-alert text type="error" elevation="2" border="left">
                  <div class="text-body-2">
                    ライブラリの更新が発生した場合のみ、実施。基本不要
                  </div>
                </v-alert>
                <p>
                  開発中、新しくモジュールを追加したい場合、Eclipseのha-nodeプロジェクト上でコマンドプロンプトを開き、以下のコマンドを実行
                </p>
                <kbd>npm install --save ${module_name}</kbd>
                <p>
                  開発中、新しくモジュールを追加したい場合、Eclipseのha-root/frontディレクトリ上でコマンドプロンプトを開き、以下のコマンドを実行
                </p>
                <kbd>npm install --save ${module_name}</kbd>
              </details>

              <br />

              <details>
                <summary>Node.js の更新</summary>
                <p>
                  Node.jsのバージョンを更新したい場合、<a href="https://nodejs.org/ja/download/" target="_blank"
                    rel="noopener noreferrer">ここ</a>を開き<b>「LTS」</b>の最新版のインストーラをダウンロードし、ウィザードの指示に従って画面を進めればOK
                </p>
                <p>
                  以下のコマンドを実行し、更新したNodeのバージョンになっていればOK
                </p>
                <kbd>node -v</kbd>
              </details>
            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-sitemap</v-icon>
                <span>IDE設定手順</span>
              </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>IDEダウンロード</summary>
                <p>
                  <a href="https://willbrains.jp/" target="_blank"
                    rel="noopener noreferrer">ここ</a>から任意のバージョンのEclipseをダウンロード
                </p>
                <p>
                  アプリ内に定義されているデフォルトのパスを有効にしたい場合、以下のパスに上記でダウンロードしたzipを解凍
                </p>
                <kbd>C:\app</kbd>
              </details>

              <br />

              <details>
                <summary>アプリダウンロード</summary>
                <p>
                  Eclipse の git パースペクティブ
                  よりリモートリポジトリからクローン
                </p>
                <v-img style="width: 600px" src="/wiki/eclipse-config2.png" alt="IDE手順2" title="IDE手順2" />
                <br /><br />
                <v-img style="width: 500px" src="/wiki/eclipse-config3.png" alt="IDE手順3" title="IDE手順3" />
                <br /><br />
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>URI</td>
                      <td>https://github.com/kohei-okazaki/work-3g.git</td>
                    </tr>
                    <tr>
                      <td>ホスト</td>
                      <td>自動入力</td>
                    </tr>
                    <tr>
                      <td>リポジトリーパス</td>
                      <td>自動入力</td>
                    </tr>
                    <tr>
                      <td>ユーザー</td>
                      <td>自身のユーザ</td>
                    </tr>
                    <tr>
                      <td>パスワード</td>
                      <td>自身のユーザのパスワード</td>
                    </tr>
                  </tbody>
                </v-simple-table>
                <p>ローカルリポジトリは以下にダウンロード</p>
                <kbd>C:\app\git</kbd>
              </details>

              <br />

              <details>
                <summary>プロジェクトインポート</summary>
                <p>
                  Eclipse の git パースペクティブ
                  よりGitリポジトリータブを選択。work3g配下のすべてのサブプロジェクトを選択。「プロジェクトのインポート」を押してimportする
                </p>
                <img src="/wiki/eclipse-config1.png" alt="IDE手順1" title="IDE手順1" />
              </details>

              <br />

              <details>
                <summary>開発用テンプレート設定</summary>
                <p>Eclipseより、以下の操作でテンプレートを設定する</p>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>1</td>
                      <td>
                        ウィンドゥ → 設定 → Java → コードスタイル →
                        クリーンアップ → インポート
                      </td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>
                        以下のxmlをインポート<br /><kbd>/ha-asset/00_local/codecleanup.xml</kbd>
                      </td>
                    </tr>
                    <tr>
                      <td>3</td>
                      <td>
                        ウィンドゥ → 設定 → Java → コードスタイル →
                        コードテンプレート → インポート
                      </td>
                    </tr>
                    <tr>
                      <td>4</td>
                      <td>
                        以下のxmlをインポート<br /><kbd>/ha-asset/00_local/codetemplates.xml</kbd>
                      </td>
                    </tr>
                    <tr>
                      <td>5</td>
                      <td>
                        ウィンドゥ → 設定 → Java → コードスタイル →
                        フォーマッター → インポート
                      </td>
                    </tr>
                    <tr>
                      <td>6</td>
                      <td>
                        以下のxmlをインポート<br /><kbd>/ha-asset/00_local/codeformatter.xml</kbd>
                      </td>
                    </tr>
                  </tbody>
                </v-simple-table>
                <p>
                  次にファイル保存時に自動でテンプレートが適用されるように以下を設定
                </p>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>1</td>
                      <td>
                        ウィンドゥ → 設定 → Java → エディター → 保存アクション
                        を開く
                      </td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>「保存時に選択したアクションを実行」にチェック</td>
                    </tr>
                    <tr>
                      <td>3</td>
                      <td>「ソース・コードのフォーマット」にチェック</td>
                    </tr>
                    <tr>
                      <td>4</td>
                      <td>すべての行をフォーマット を選択</td>
                    </tr>
                    <tr>
                      <td>5</td>
                      <td>「インポートの編成」にチェック</td>
                    </tr>
                    <tr>
                      <td>6</td>
                      <td>
                        「追加アクション」にチェック。お好みで好きな設定を追加して下さい
                      </td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>
              <br />

              <details>
                <summary>mybatis generator設定</summary>
                <p>Eclipseより、以下の操作で「mybatis generator」の拡張機能を入れる</p>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>1</td>
                      <td>
                        ヘルプ → Eclipseマーケットプレース を開く
                      </td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>
                        検索窓で「mybatis generator」と入力し「Go」ボタン押下
                      </td>
                    </tr>
                    <tr>
                      <td>3</td>
                      <td>
                        検索結果部分に表示された「MyBatis Generator 1.4.2」の「インストール」ボタン押下
                      </td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>

              <br />

              <details>
                <summary>DB反映設定</summary>
                <kbd>ha-db/src/main/resources/generatorConfig.xml</kbd>
                <p>の"classPathEntry"タグのjarパスが正しい確認。違う場合は修正</p>
              </details>

              <br />

              <details>
                <summary>Mybatis起動設定</summary>
                <p>Eclipse起動。（mybatis generator設定などは事前に完了すること）</p>
                <p>メニュー → 実行 → 実行の構成 → MyBatis Generatorを右クリック → 新規構成</p>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>名前</td>
                      <td>MyBatis反映</td>
                    </tr>
                  </tbody>
                </v-simple-table>
                <br>
                <b>構成タブで以下を設定</b>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>構成ファイル</td>
                      <td><kbd>${workspace_loc:/ha-db/src/main/resources/generatorConfig.xml}</kbd></td>
                    </tr>
                  </tbody>
                </v-simple-table>
                <br>
                <b>環境タブで以下を設定</b>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>DB_URL</td>
                      <td>
                        <ul>
                          <li>aws lightsailsでDB構築した場合、AWS lightsailのコンソールから確認して、URLを指定</li>
                          <li>ローカル環境にMySQLでDB構築した場合、jdbc:mysql://localhost:3306/work3g</li>
                        </ul>
                      </td>
                    </tr>
                    <tr>
                      <td>DB_USER</td>
                      <td>「MySQL環境構築」のユーザ作成のセクションで登録した値</td>
                    </tr>
                    <tr>
                      <td>DB_PW</td>
                      <td>「MySQL環境構築」のユーザ作成のセクションで登録した値</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>
            </v-expansion-panel-content>
          </v-expansion-panel>
          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-file</v-icon>
                <span>シェル関連設定手順</span>
              </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>共通バッチファイルの修正</summary>
                <kbd>ha-batch/bat/common.bat</kbd> を開き、以下を修正
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>DB_URL</td>
                      <td>
                        <ul>
                          <li>aws lightsailsでDB構築した場合、AWS lightsailのコンソールから確認して、URLを指定</li>
                          <li>ローカル環境にMySQLでDB構築した場合、jdbc:mysql://localhost:3306/work3g</li>
                        </ul>
                      </td>
                    </tr>
                    <tr>
                      <td>DB_USER</td>
                      <td>「MySQL環境構築」のユーザ作成のセクションで登録した値</td>
                    </tr>
                    <tr>
                      <td>DB_PW</td>
                      <td>「MySQL環境構築」のユーザ作成のセクションで登録した値</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>

              <br />

              <details>
                <summary>共通buildファイルの修正</summary>
                <kbd>ha-build/bat/build.ini</kbd> を開き、以下を修正
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>DB_URL</td>
                      <td>
                        <ul>
                          <li>aws lightsailsでDB構築した場合、AWS lightsailのコンソールから確認して、URLを指定</li>
                          <li>ローカル環境にMySQLでDB構築した場合、jdbc:mysql://localhost:3306/work3g</li>
                        </ul>
                      </td>
                    </tr>
                    <tr>
                      <td>DB_USER</td>
                      <td>「MySQL環境構築」のユーザ作成のセクションで登録した値</td>
                    </tr>
                    <tr>
                      <td>DB_PW</td>
                      <td>「MySQL環境構築」のユーザ作成のセクションで登録した値</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AppBreadCrumbs from "~/components/AppBreadCrumbs.vue";
import AppContentsTitle from "~/components/AppContentsTitle.vue";

export default {
  // Wikiのレイアウトを適用
  layout: "wikiLayout",
  components: {
    AppBreadCrumbs,
    AppContentsTitle,
  },
  data: function () {
    return {
      breadcrumbs: [
        {
          text: "Top",
          disabled: false,
          href: "/",
        },
        {
          text: "Wiki",
          disabled: false,
          href: "/wiki",
        },
        {
          text: "ローカル環境構築手順",
          disabled: true,
          href: "/wiki/localEnv",
        },
      ],
      mysqlDbTools: [
        {
          type: "データベース",
          name: "MySQL",
          version: "8.4.0",
        },
        {
          type: "DBマイグレーション",
          name: "Flyway",
          version: "11.15.0",
        },
        {
          type: "ORM",
          name: "MyBatis",
          version: "3.5.19",
        },
      ],
      mongoDbTools: [
        {
          type: "データベース",
          name: "MongoDB",
          version: "8.2.1",
        },
        {
          type: "コマンドラインツール",
          name: "mongosh",
          version: "2.5.8",
        },
      ],
    };
  },
};
</script>
<style scoped></style>

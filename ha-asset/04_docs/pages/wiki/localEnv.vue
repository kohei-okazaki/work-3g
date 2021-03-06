<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle title="ローカル環境構築手順" />

    <v-row justify="center" align="center">
      <v-col cols="12" sm="8" md="10">
        <v-alert text type="info" elevation="2" border="left">
          <div class="text-subtitle-1">概要</div>
          <div class="text-body-2">
            ローカル環境の開発構築手順書になります<br />以下の手順に従って環境構築を行って下さい
          </div>
        </v-alert>
      </v-col>
    </v-row>

    <v-row justify="center" align="center">
      <v-col cols="12" sm="8" md="10">
        <v-expansion-panels multiple>
          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-database</v-icon>
                <span>DB構築手順</span>
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
                    <tr v-for="(tool, i) in dbTools" :key="i">
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
                  <a
                    href="https://www.mysql.com/jp/"
                    target="_blank"
                    rel="noopener noreferrer"
                    >公式</a
                  >より以下の手順でMySQLをローカルPCにダウンロードして下さい
                </p>
                <p>
                  以下ダウンロード手順は2016年頃のもののため、画面が更新されている可能性があります
                </p>
                <ol>
                  <li>
                    <p>
                      上記説明のURLのリンクで遷移後の画面上部の「ダウンロード」タブ押下
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
                    <v-img
                      src="/wiki/mysql-install1.png"
                      alt="mysqlインストール作業画像1"
                      title="mysqlインストール作業画像1"
                    />
                  </li>
                  <li>
                    <p>
                      MySQL Community Downloads のページが表示されるので、MySQL
                      Community Server (GPL)
                      ブロックの中にある「DOWNLOAD」と書かれたリンク押下
                    </p>
                    <v-img
                      src="/wiki/mysql-install2.png"
                      alt="mysqlインストール作業画像2"
                      title="mysqlインストール作業画像2"
                    />
                  </li>
                  <li>
                    <p>
                      Download MySQL Community Server
                      のページが表示されるので、画面下部のMySQL
                      をインストールするプラットフォームを Select Platform
                      の下に表示されているドロップダウンメニューで選択します
                    </p>
                    <v-img
                      src="/wiki/mysql-install3.png"
                      alt="mysqlインストール作業画像3"
                      title="mysqlインストール作業画像3"
                    />
                    <p>
                      今回は Windows 環境にインストールしますので Microsoft
                      Windows
                      を選択して下さい（デフォルトで選択されている場合は何もしなくて結構です）
                    </p>
                    <v-img
                      src="/wiki/mysql-install4.png"
                      alt="mysqlインストール作業画像4"
                      title="mysqlインストール作業画像4"
                    />
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
                    <v-img
                      src="/wiki/mysql-install5.png"
                      alt="mysqlインストール作業画像5"
                      title="mysqlインストール作業画像5"
                    />
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
                    <v-img
                      src="/wiki/mysql-install6.png"
                      alt="mysqlインストール作業画像6"
                      title="mysqlインストール作業画像6"
                    />
                    <v-img
                      src="/wiki/mysql-install7.png"
                      alt="mysqlインストール作業画像7"
                      title="mysqlインストール作業画像7"
                    />
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
                    <v-img
                      src="/wiki/mysql-install8.png"
                      alt="mysqlインストール作業画像8"
                      title="mysqlインストール作業画像8"
                    />
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
                    <v-img
                      src="/wiki/mysql-install9.png"
                      alt="mysqlインストール作業画像9"
                      title="mysqlインストール作業画像9"
                    />
                  </li>
                  <li>
                    <p>
                      次の 2
                      つについては必要なファイルなどがないためインストールされませんと表示されています。今回は問題ないので「Next」押下
                    </p>
                    <v-img
                      src="/wiki/mysql-install10.png"
                      alt="mysqlインストール作業画像10"
                      title="mysqlインストール作業画像10"
                    />
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
                    <v-img
                      src="/wiki/mysql-install11.png"
                      alt="mysqlインストール作業画像11"
                      title="mysqlインストール作業画像11"
                    />
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
                    <v-img
                      src="/wiki/mysql-install12.png"
                      alt="mysqlインストール作業画像12"
                      title="mysqlインストール作業画像12"
                    />
                  </li>
                  <li>
                    <p>
                      MySQL InnoDB Clusterを
                      利用するかどうかの選択です。今回は単独で MySQL
                      を利用するので Standalone MySQL Server/Classic MySQL
                      Replication を選択し、「Next」ボタンを押下して下さい
                    </p>
                    <v-img
                      src="/wiki/mysql-install13.png"
                      alt="mysqlインストール作業画像13"
                      title="mysqlインストール作業画像13"
                    />
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
                    <v-img
                      src="/wiki/mysql-install14.png"
                      alt="mysqlインストール作業画像14"
                      title="mysqlインストール作業画像14"
                    />
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
                    <v-img
                      src="/wiki/mysql-install15.png"
                      alt="mysqlインストール作業画像15"
                      title="mysqlインストール作業画像15"
                    />
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
                    <v-img
                      src="/wiki/mysql-install16.png"
                      alt="mysqlインストール作業画像16"
                      title="mysqlインストール作業画像16"
                    />
                  </li>
                  <li>
                    <p>
                      MySQL を Windows
                      のサービスとして動かすかどうかの設定画面が表示されます。「Configure
                      MySQL Server as a Windows
                      Service」のチェックを付けます。その後で「Next」をクリックしてください。
                    </p>
                    <v-img
                      src="/wiki/mysql-install17.png"
                      alt="mysqlインストール作業画像17"
                      title="mysqlインストール作業画像17"
                    />
                  </li>
                  <li>
                    <p>最終確認画面です。「Execute」をクリックして下さい。</p>
                    <v-img
                      src="/wiki/mysql-install18.png"
                      alt="mysqlインストール作業画像18"
                      title="mysqlインストール作業画像18"
                    />
                    <p>
                      下記の画面が表示されれば初期設定は完了です。「Finish」をクリックして下さい。
                    </p>
                    <v-img
                      src="/wiki/mysql-install19.png"
                      alt="mysqlインストール作業画像19"
                      title="mysqlインストール作業画像19"
                    />
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
                    <v-img
                      src="/wiki/mysql-install20.png"
                      alt="mysqlインストール作業画像20"
                      title="mysqlインストール作業画像20"
                    />
                  </li>
                  <li>
                    <p>
                      ログインに成功すると次のように表示されます。そのあとで「Next」をクリックしてください。
                    </p>
                    <v-img
                      src="/wiki/mysql-install21.png"
                      alt="mysqlインストール作業画像21"
                      title="mysqlインストール作業画像21"
                    />
                    <p>「Execute」をクリックして下さい。</p>
                    <v-img
                      src="/wiki/mysql-install22.png"
                      alt="mysqlインストール作業画像22"
                      title="mysqlインストール作業画像22"
                    />
                    <p>
                      下記の画面が表示されれば初期設定は完了です。「Finish」をクリックして下さい。
                    </p>
                    <v-img
                      src="/wiki/mysql-install23.png"
                      alt="mysqlインストール作業画像23"
                      title="mysqlインストール作業画像23"
                    />
                    <p>
                      すべてのインストールが完了しました。「Next」をクリックしてください。
                    </p>
                    <v-img
                      src="/wiki/mysql-install24.png"
                      alt="mysqlインストール作業画像24"
                      title="mysqlインストール作業画像24"
                    />
                    <p>次のように表示されれば初期設定も完了です。</p>
                    <v-img
                      src="/wiki/mysql-install25.png"
                      alt="mysqlインストール作業画像25"
                      title="mysqlインストール作業画像25"
                    />
                  </li>
                </ol>
              </details>

              <br />

              <details>
                <summary>ユーザとパスワードを設定</summary>
                <p>
                  以下のファイルのuserとpasswordにインストール時に指定したものを設定
                </p>
                <kbd>ha-common/profile/local/jdbc.properties</kbd>
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
                      <td>jdbc.username</td>
                      <td>MySQLインストール時に作成したユーザ名</td>
                    </tr>
                    <tr>
                      <td>jdbc.password</td>
                      <td>MySQLインストール時に作成したパスワード</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>

              <br />

              <details>
                <summary>データベース作成</summary>
                <p>
                  「Windowsキー」押下、「mysql」と入力し以下画像のように「MySQL
                  8.0 Command Line Clinet」ツールを選択
                </p>
                <v-img
                  src="/wiki/mysql-command-line-client1.png"
                  alt="mysqlツール作業画像1"
                  title="mysqlツール作業画像1"
                />
                <p>
                  以下のファイルMySQLクライアントから実行し、データベース「work3g」を作成する
                </p>
                <kbd>/ha-asset/02_db/others/CREATE_DATABASE.sql</kbd>
              </details>
            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start  text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-language-java</v-icon>
                <span>Java環境構築手順</span>
               </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>Java インストール</summary>
                <p>
                  <a
                    href="https://docs.aws.amazon.com/ja_jp/corretto/latest/corretto-8-ug/downloads-list.html"
                    target="_blank"
                    rel="noopener noreferrer"
                    >Amazon CorrettoのJava8</a
                  >から各PCのOSに合わせ、Java8をインストール
                </p>
                <p>
                  コマンドプロンプトまたはターミナルより以下をコマンドを実行
                </p>
                <kbd>$java -version</kbd>
                <p>
                  以下の結果が出力されていればOK（メジャーバージョンである8系が出力されていればOK）
                </p>
                <kbd>openjdk version "1.8.0_252"</kbd><br />
                <kbd
                  >OpenJDK Runtime Environment Corretto-8.252.09.2 (build
                  1.8.0_252-b09)</kbd
                ><br />
                <kbd
                  >OpenJDK 64-Bit Server VM Corretto-8.252.09.2 (build
                  25.252-b09, mixed mode)</kbd
                >
              </details>

              <br />

              <details>
                <summary>AWS CLI インストール手順（Windows）</summary>
                <p>
                  <a
                    href="https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/install-cliv2-windows.html"
                    target="_blank"
                    rel="noopener noreferrer"
                    >Windowsの場合</a
                  >よりインストーラをダウンロード
                </p>
                <p>
                  ダウンロードしたインストーラ AWSCLI64PY3.msi
                  を実行し、「Next」ボタン押下
                </p>
                <v-img
                  src="/wiki/win-cli-install1.png"
                  alt="Windows Install手順1"
                  title="Windows Install手順1"
                />
                <p>チェックボックスにチェックを付け、「Next」ボタン押下</p>
                <v-img
                  src="/wiki/win-cli-install2.png"
                  alt="Windows Install手順2"
                  title="Windows Install手順2"
                />
                <p>「Next」ボタン押下</p>
                <v-img
                  src="/wiki/win-cli-install3.png"
                  alt="Windows Install手順3"
                  title="Windows Install手順3"
                />
                <p>「Install」ボタン押下</p>
                <v-img
                  src="/wiki/win-cli-install4.png"
                  alt="Windows Install手順4"
                  title="Windows Install手順4"
                />
                <p>「Finish」ボタン押下</p>
                <v-img
                  src="/wiki/win-cli-install5.png"
                  alt="Windows Install手順5"
                  title="Windows Install手順5"
                />
                <br />
                <p>
                  上記インストールが正常に完了後、コマンドプロンプトより以下のコマンドを実行
                </p>
                <kbd>aws --version</kbd>
                <p>以下の結果が出力されていればOK（バージョンは任意とする）</p>
                <kbd
                  >aws-cli/2.0.10 Python/3.7.5 Windows/10
                  botocore/2.0.0dev14</kbd
                >
              </details>

              <br />

              <details>
                <summary>AWS CLI インストール手順（Mac）</summary>
                <p>
                  <a
                    href="https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/install-cliv2-mac.html"
                    target="_blank"
                    rel="noopener noreferrer"
                    >Macの場合</a
                  >よりインストーラをダウンロード
                </p>
                <p>
                  コマンドを叩くだけなので、<a
                    href="https://awscli.amazonaws.com/AWSCLIV2.pkg"
                    target="_blank"
                    rel="noopener noreferrer"
                    >ここ</a
                  >から最新のAWS-CLI
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
                <kbd
                  >aws-cli/2.0.10 Python/3.7.5 Windows/10
                  botocore/2.0.0dev14</kbd
                >
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
                <summary>ローカルTomcatサーバ構築</summary>
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
                              Windowsの場合<br /><kbd
                                >C:\Program Files\Amazon Corretto\jre8\bin</kbd
                              >
                            </p>
                          </li>
                          <li>
                            <p>
                              Macの場合<br /><kbd
                                >/Library/Java/Extensions/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/bin</kbd
                              >
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
                <p>次にローカルサーバの構築を行う</p>

                <br />
                <v-alert text type="info" border="left">
                  ローカルサーバ設定
                </v-alert>
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
                      <td>パッケージエクスプローラ上で右クリック</td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>新規 選択</td>
                    </tr>
                    <tr>
                      <td>3</td>
                      <td>その他 選択</td>
                    </tr>
                    <tr>
                      <td>4</td>
                      <td>
                        「サーバー」フォルダの「サーバー」を選択して「次へ」
                        選択
                      </td>
                    </tr>
                    <tr>
                      <td>5</td>
                      <td>ランタイム環境の構成 選択</td>
                    </tr>
                    <tr>
                      <td>6</td>
                      <td>追加 選択</td>
                    </tr>
                    <tr>
                      <td>7</td>
                      <td>Apache Tomcat v9.0 選択</td>
                    </tr>
                    <tr>
                      <td>8</td>
                      <td>次へ 選択</td>
                    </tr>
                    <tr>
                      <td>9</td>
                      <td>
                        名前=Amazon Corretto, JRE=インストール済のJRE →
                        上記設定したJRE「Amazon Corretto」を選択
                      </td>
                    </tr>
                  </tbody>
                </v-simple-table>

                <br />
                <v-alert text type="info" border="left">
                  健康管理ダッシュボードサーバの構築
                </v-alert>
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
                      <td>サーバービュー上で右クリック</td>
                    </tr>
                    <tr>
                      <td>2</td>
                      <td>新規 選択</td>
                    </tr>
                    <tr>
                      <td>3</td>
                      <td>Apache Tomcat v9.0 選択</td>
                    </tr>
                    <tr>
                      <td>4</td>
                      <td>↓の情報を入力</td>
                    </tr>
                    <tr>
                      <td>5</td>
                      <td>サーバーのホスト名(H) = localhost</td>
                    </tr>
                    <tr>
                      <td>6</td>
                      <td>サーバー名(M) = dashboard-server</td>
                    </tr>
                    <tr>
                      <td>7</td>
                      <td>サーバー・ランタイム環境(R) = Amazon Corretto</td>
                    </tr>
                  </tbody>
                </v-simple-table>

                <br />
                <v-alert text type="info" border="left">
                  健康管理APIサーバの構築
                </v-alert>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>1 ~ 4</td>
                      <td>ダッシュボードサーバの構築と同様</td>
                    </tr>
                    <tr>
                      <td>5</td>
                      <td>サーバーのホスト名(H) = localhost</td>
                    </tr>
                    <tr>
                      <td>6</td>
                      <td>サーバー名(M) = api-server</td>
                    </tr>
                    <tr>
                      <td>7</td>
                      <td>サーバー・ランタイム環境(R) = Amazon Corretto</td>
                    </tr>
                    <tr>
                      <td>8</td>
                      <td>サーバービュー上のapi-serverをダブルクリック</td>
                    </tr>
                    <tr>
                      <td>9</td>
                      <td>ポート部分に↓の情報で更新</td>
                    </tr>
                    <tr>
                      <td>10</td>
                      <td>Tomcat管理ポート = 8006</td>
                    </tr>
                    <tr>
                      <td>11</td>
                      <td>HTTP/1.1 = 8081</td>
                    </tr>
                    <tr>
                      <td>12</td>
                      <td>AJP/1.3 = 8010</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>
            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start  text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-language-python</v-icon>
                <span>Python構築手順</span>
               </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>Python インストール</summary>
                <p>
                  <a
                    href="https://www.anaconda.com/products/individual"
                    target="_blank"
                    rel="noopener noreferrer"
                    >ここ</a
                  >の画面下部から各OSに合わせてインストーラをダウンロード
                </p>
                <v-img
                  style="width: 700px"
                  src="/wiki/python-install1.png"
                  alt="Python install手順1"
                  title="Python install手順1"
                />
              </details>

              <br />

              <details>
                <summary>Eclipse インタープリター設定</summary>
                <p>
                  ヘルプ → eclipseマーケットプレイス → PyDevを検索してInstall
                </p>
                <p>
                  インタープリターは「Python
                  インストール」でインストールしたAnacondaのパスを設定する
                </p>
                <p>
                  ウィンドゥ → 設定 → PyDev → インタープリター →
                  Pythonインタープリター → 一番上のBlowse for python...
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
                      <td>インタープリター名</td>
                      <td>app-python</td>
                    </tr>
                    <tr>
                      <td>インタープリター実行可能ファイル</td>
                      <td>
                        <ul>
                          <li>
                            <p>
                              Windowsの場合<br /><kbd
                                >C:\Users\${user}\anaconda3\python.exe</kbd
                              >
                            </p>
                          </li>
                          <li>
                            <p>
                              Macの場合<br /><kbd
                                >/Users/${user}/opt/anaconda3/bin/python3.7</kbd
                              >
                            </p>
                          </li>
                        </ul>
                      </td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>

              <br />

              <details>
                <summary>Python コマンド一覧</summary>
                <p>
                  以下のコマンドはコマンドプロンプトやターミナルではなく、「Anaconda
                  Prompt」で行う
                </p>
                <p>Anaconda Promptの開き方</p>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>やりたいこと</th>
                      <th>コマンド</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>本体のアップデート</td>
                      <td><kbd>conda update conda -y</kbd></td>
                    </tr>
                    <tr>
                      <td>パッケージ一式のアップデート</td>
                      <td><kbd>conda update --all -y</kbd></td>
                    </tr>
                    <tr>
                      <td>Pythonのバージョンのアップデート</td>
                      <td><kbd>conda update python -y</kbd></td>
                    </tr>
                    <tr>
                      <td>Anacondaのアンインストール</td>
                      <td>
                        <ol>
                          <li><kbd>conda install anaconda-clean</kbd></li>
                          <li><kbd>anaconda-clean --yes</kbd></li>
                        </ol>
                      </td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>
            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start  text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-nodejs</v-icon>
                <span>Node.js構築手順</span>
               </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>Node.js インストール</summary>
                <p>
                  <a
                    href="https://nodejs.org/ja/download/"
                    target="_blank"
                    rel="noopener noreferrer"
                    >ここ</a
                  >から各OSに合わせてインストーラをダウンロード
                </p>
                <p>バージョンは偶数バージョンを選択</p>
                <p>
                  インストール完了後、コマンドプロンプトまたはターミナルで実行
                </p>
                <kbd>node -v</kbd>
                <p>インストールしたバージョン情報を表示されていればOK</p>
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
      dbTools: [
        {
          type: "データベース",
          name: "MySQL",
          version: "8.0.16",
        },
        {
          type: "DBマイグレーション",
          name: "Flyway",
          version: "7.5.4",
        },
        {
          type: "ORM",
          name: "MyBatis",
          version: "3.5.6",
        },
      ],
    };
  },
};
</script>
<style scoped>
</style>
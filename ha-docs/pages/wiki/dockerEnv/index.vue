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
        <v-alert text type="info" elevation="2" border="left">
          <div class="text-subtitle-1">概要</div>
          <div class="text-body-2">
            Dockerによるローカル環境の開発構築手順書になります<br />
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
                <v-icon color="red">mdi-docker</v-icon>
                <span>Docker環境構築手順</span>
              </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">
              <details>
                <summary>WSL2インストール手順</summary>
                <ol>
                  <li>タスクマネージャー → パフォーマンス → CPU に 「Virtualization: Enabled」</li>
                  <li>Windowsの設定からWSL2を有効にする。</li>
                  <li>PowerShellを管理者権限で起動し、以下のコマンドを実行する。<br>
                    <kbd>wsl --install -d Ubuntu</kbd>
                  </li>
                  <li>PCの再起動を行う。</li>
                  <li>以下のコマンドを実行する。<br>
                    <kbd>wsl --update</kbd><br>
                    <kbd>wsl -l -v</kbd>
                  </li>
                </ol>
              </details>
              <br>
              <details>
                <summary>Dockerインストール手順</summary>
                <ol>
                  <li><a href="https://docs.docker.com/get-docker/" target="_blank"
                      rel="noopener noreferrer">公式</a>よりDocker Desktop for Windowsをダウンロードする。</li>
                  <li>
                    各OSに合わせてDockerをインストールする。<br>
                    ウィザードはデフォルトのままで問題ない。
                  </li>
                  <li>
                    コマンドプロンプトまたはターミナルより以下をコマンドを実行する。<br>
                    <kbd>docker version</kbd>
                  </li>
                </ol>
              </details>

            </v-expansion-panel-content>
          </v-expansion-panel>

          <v-expansion-panel>
            <v-expansion-panel-header class="justify-self-start text-subtitle-1" disable-icon-rotate>
              <div>
                <v-icon color="red">mdi-database</v-icon>
                <span>MongoDB接続手順</span>
              </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="text-body-2">

              <v-alert text type="error" elevation="2" border="left">
                <div class="text-body-2">
                  NoSQLの為、直接DDLを流すことは無い。<br>
                </div>
              </v-alert>

              <details>
                <summary>ダウンロード手順</summary>
                <p>
                  <a href="https://www.mongodb.com/try/download/compass" target="_blank"
                    rel="noopener noreferrer">公式</a>より以下の手順でMongoDB Compass（クライアントツール）をローカルPCにダウンロードする。
                </p>
              </details>

              <br />

              <details>
                <summary>インストール手順</summary>
                <p>上記、「ダウンロード手順」を実施してあることを前提です。</p>
                <p>以下の手順でMongoDB Compassをインストールする。</p>
                <ol>
                  <li>ダウンロードした.exeファイルをダブルクリックして実行する。</li>
                  <li>インストーラの指示に従ってインストールを進めます。(基本的にウィザードに従う)</li>
                </ol>
              </details>

              <br />

              <details>
                <summary>接続手順</summary>
                <p>上記、「インストール手順」を実施してあることを前提です。</p>
                <ol>
                  <li>MongoDB Compassを起動します。</li>
                  <li>画面左のメニューから「Connections」の右の+のアイコンを開く。</li>
                  <li>以下を設定<br>
                    URI=mongodb://health_user:hbt4stnsegebg@172.17.20.208:27017/?authSource=admin<br>
                    Name=任意のわかりやすい名前(docker_mongo)<br>
                  </li>
                  <li>「Advanced Connection Options」を開く。</li>
                  <li>「Authentication」タブを選択する。</li>
                  <li>「Username/Password」タブを選択する。</li>
                  <li>以下を設定<br>
                    Username=health_user<br>
                    Password=hbt4stnsegebg
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
                  コマンドプロンプトまたはターミナルより以下をコマンドを実行する。
                </p>
                <kbd>$java -version</kbd>
                <p>
                  以下の結果が出力されていればOK。（メジャーバージョンである21系が出力されていればOK）
                </p>
                <kbd>openjdk version "21.0.8" 2025-07-15 LTS</kbd>
              </details>

              <br />

              <details>
                <summary>AWS CLI インストール手順（Windows）</summary>
                <p>
                  <a href="https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/install-cliv2-windows.html"
                    target="_blank" rel="noopener noreferrer">Windowsの場合</a>よりインストーラをダウンロードする。
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
                  上記インストールが正常に完了後、コマンドプロンプトより以下のコマンドを実行する。
                </p>
                <kbd>aws --version</kbd>
                <p>以下の結果が出力されていればOK。（バージョンは任意とする）</p>
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
                  任意のパスで上記のダウンロードしたzipを解凍
                </p>
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
                <summary>DB反映設定</summary>
                <kbd>ha-db/src/main/resources/generatorConfig.xml</kbd>
                <p>の"classPathEntry"タグのjarパスが正しい確認。違う場合は修正</p>
              </details>

              <br />

              <details>
                <summary>Mybatis Generator設定</summary>
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
                <br>
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
                <b>構成タブ</b>で以下を設定
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
                <b>環境タブ</b>で以下を設定
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
                        jdbc:mysql://127.0.0.1:3306/work3g?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Tokyo
                      </td>
                    </tr>
                    <tr>
                      <td>DB_USER</td>
                      <td>app_user</td>
                    </tr>
                    <tr>
                      <td>DB_PW</td>
                      <td>3w4tamudnxgr4</td>
                    </tr>
                  </tbody>
                </v-simple-table>

                <p>※1. MyBatis反映を行う場合、先にDockerで適当なアプリプロジェクトを起動させてFlywayでDBマイグレーションを実行させ、DBにテーブルを作成してから実行すること。</p>
                <p>※2. MyBatis反映を行う場合、Docker上のMySQLを起動させた状態で行うこと。（接続できないため）</p>

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
                <summary>Docker関連バッチファイルの修正</summary>
                <kbd>ha-build/docker/const.txt</kbd> を開き、以下を修正する。
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>BASE_DIR</td>
                      <td>"/mnt/c/app/git/work-3g"が指定されているが、C:\ ドライブ以降のパスをローカルリポジトリのルートディレクトリと合わせること。</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </details>

              <br />

              <details>
                <summary>バッチ共通ファイルの修正</summary>
                <kbd>ha-batch/docker/const.txt</kbd> を開き、以下を修正する。
                <v-simple-table>
                  <thead>
                    <tr>
                      <th>項目名</th>
                      <th>設定値</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>BASE_DIR</td>
                      <td>"/mnt/c/app/git/work-3g"が指定されているが、C:\ ドライブ以降のパスをローカルリポジトリのルートディレクトリと合わせること。</td>
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
          text: "Docker環境構築手順",
          disabled: true,
          href: "/wiki/dockerEnv",
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

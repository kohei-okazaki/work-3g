<template>
  <div>
    <AppBreadCrumbs :items="breadcrumbs" />
    <AppContentsTitle :title="breadcrumbs[breadcrumbs.length - 1].text" />
    <v-row justify="center" align="center">
      <v-col cols="12" sm="10" md="10">
        <div class="text-left">
          <v-expansion-panels multiple>
            <v-expansion-panel>
              <v-expansion-panel-header class="text-subtitle-1"
                >テーブル追加</v-expansion-panel-header
              >
              <v-expansion-panel-content class="text-body-2">
                <details>
                  <summary>DB側対応</summary>
                  <ol>
                    <li>
                      <p>
                        ha-asset/99_tool/menu/js/table.js
                        に新しいテーブル定義を追加
                      </p>
                    </li>
                    <li>
                      <p>
                        ha-asset/99_tool/menu/js/table.min.js
                        にtable.jsを圧縮させたファイルを設定
                      </p>
                    </li>
                    <li>
                      <p>
                        ha-tool/src/main/resources/tool.properties に以下を追加
                      </p>
                      <kbd>tool.target.tables=追加したいテーブル名(物理名)</kbd>
                    </li>
                    <li>
                      <p>ha-asset/02_db/DB.xlsx にテーブル定義を追加</p>
                    </li>
                    <li>
                      <p>
                        ha-tool/src/main/java/jp/co/ha/tool/ToolInvoker.javaを
                        GenerateType.DDL 指定で起動
                      </p>
                    </li>
                    <li>
                      <p>
                        ha-asset/02_db/ddl配下にsqlが作成されていることを確認
                      </p>
                    </li>
                    <li>
                      <p>
                        ha-db/src/main/resources/db/migrationに新しいバージョンのsqlファイルを作成
                      </p>
                    </li>
                    <li>
                      <p>コマンドプロンプトから以下のファイルを実行</p>
                      <ol>
                        <li><kbd>ha-build/bat/maven-build.bat local</kbd></li>
                        <li><kbd>ha-build/bat/maven-flyway.bat</kbd></li>
                      </ol>
                    </li>
                  </ol>
                </details>
                <br />
                <details>
                  <summary>Java側対応</summary>
                  <ol>
                    <li>
                      <p>
                        ha-db/src/main/resources/generatorConfig.xmlを開き、以下を追加
                      </p>
                      <kbd
                        >&lt;table schema="work3g"
                        tableName="追加するテーブル名" modelType="hierarchical"
                        /&gt;</kbd
                      >
                    </li>
                    <li>
                      <p>
                        ha-db/src/main/resources/generatorConfig.xml上で右クリック
                      </p>
                    </li>
                    <li>
                      <p>実行 押下</p>
                    </li>
                    <li>
                      <p>「Run MyBatis Generator」 押下</p>
                    </li>
                    <li>
                      <p>
                        作成されたMapper.xml内の物理テーブル名を大文字に置換すること
                      </p>
                      <div class="alert alert-danger" role="alert">
                        EC2環境のDBは大文字と小文字を区別するため
                      </div>
                    </li>
                  </ol>
                </details>
              </v-expansion-panel-content>
            </v-expansion-panel>

            <v-expansion-panel>
              <v-expansion-panel-header class="text-subtitle-1"
                >カラム追加</v-expansion-panel-header
              >
              <v-expansion-panel-content class="text-body-2">
                <details>
                  <summary>DB側対応</summary>
                  <ol>
                    <li>
                      <p>
                        ha-asset/99_tool/menu/js/table.js
                        に新しいテーブル定義を追加
                      </p>
                    </li>
                    <li>
                      <p>
                        ha-asset/99_tool/menu/js/table.min.js
                        にtable.jsを圧縮させたファイルを設定
                      </p>
                    </li>
                    <li>
                      <p>
                        ha-tool/src/main/resources/tool.properties に以下を追加
                      </p>
                      <kbd
                        >tool.target.tables=カラム追加したいテーブル名(物理名)</kbd
                      >
                    </li>
                    <li>
                      <p>ha-asset/02_db/DB.xlsx にテーブル定義を追加</p>
                    </li>
                    <li>
                      <p>
                        ha-tool/src/main/java/jp/co/ha/tool/ToolInvoker.javaを
                        GenerateType.DDL 指定で起動
                      </p>
                    </li>
                    <li>
                      <p>
                        ha-asset/02_db/ddl配下にsqlが作成されていることを確認
                      </p>
                    </li>
                    <li>
                      <p>
                        ha-db/src/main/resources/db/migrationに新しいバージョンのsqlファイルを作成
                      </p>
                      <p>
                        ただし、ファイル内容はddl配下にsqlから自作でALTER ADD
                        ~~を追加する
                      </p>
                      <p>カラム追加ページに基本の構文がある</p>
                    </li>
                    <li>
                      <p>コマンドプロンプトから以下のファイルを実行</p>
                      <ol>
                        <li><kbd>ha-build/bat/maven-build.bat local</kbd></li>
                        <li><kbd>ha-build/bat/maven-flyway.bat</kbd></li>
                      </ol>
                    </li>
                  </ol>
                </details>
                <br />
                <details>
                  <summary>Java側対応</summary>
                  <ol>
                    <li>
                      <p>
                        ha-db/src/main/resources/generatorConfig.xmlを開き、以下を追加
                      </p>
                      <kbd
                        >&lt;table schema="work3g"
                        tableName="カラム追加するテーブル名"
                        modelType="hierarchical" /&gt;</kbd
                      >
                    </li>
                    <li>
                      <p>
                        ha-db/src/main/resources/generatorConfig.xml上で右クリック
                      </p>
                    </li>
                    <li>
                      <p>実行 押下</p>
                    </li>
                    <li>
                      <p>「Run MyBatis Generator」 押下</p>
                    </li>
                    <li>
                      <p>
                        作成されたMapper.xml内の物理テーブル名を大文字に置換すること
                      </p>
                      <div class="alert alert-danger" role="alert">
                        EC2環境のDBは大文字と小文字を区別するため
                      </div>
                    </li>
                  </ol>
                </details>
              </v-expansion-panel-content>
            </v-expansion-panel>

            <v-expansion-panel>
              <v-expansion-panel-header class="text-subtitle-1"
                >DML作成</v-expansion-panel-header
              >
              <v-expansion-panel-content class="text-body-2">
                <details>
                  <summary>DB側対応</summary>
                  <ol>
                    <li>
                      <p>ha-asset/02_db/DB.xlsx に以下を対応</p>
                      <ol>
                        <li>
                          新規追加の場合、既存シートをコピーしテーブル名をDMLの物理名を指定しレコードを記載
                        </li>
                        <li>既存DMLにレコード追加の場合、レコードを追記</li>
                      </ol>
                    </li>
                    <li>
                      <p>
                        ha-tool/src/main/java/jp/co/ha/tool/ToolInvoker.javaを
                        GenerateType.DML 指定でスタンドアローンで起動
                      </p>
                    </li>
                    <li>
                      <p>
                        ha-db/src/main/resources/db/migrationに新しいバージョンのsqlファイルを作成
                      </p>
                      <p>ファイル内容はJavaで作成したDMLを追記</p>
                    </li>
                    <li>
                      <p>コマンドプロンプトから以下のファイルを実行</p>
                      <ol>
                        <li><kbd>ha-build/bat/maven-build.bat local</kbd></li>
                        <li><kbd>ha-build/bat/maven-flyway.bat</kbd></li>
                      </ol>
                    </li>
                  </ol>
                </details>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </div>
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
          text: "DB変更手順",
          disabled: true,
          href: "/wiki/dbMod",
        },
      ],
    };
  },
};
</script>
<style scoped>
</style>
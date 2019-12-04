var table = [
  {
    physicalName: "HEALTH_INFO",
    logicalName: "健康情報",
    column : [
      {
        logicalName: "健康情報ID",
        physicalName: "HEALTH_INFO_ID",
        type: "INTEGER",
        size: ""
      },
      {
        logicalName: "ユーザID",
        physicalName: "USER_ID",
        type: "VARCHAR",
        size: "16"
      },
      {
        logicalName: "身長",
        physicalName: "HEIGHT",
        type: "DECIMAL",
        size: "6, 3"
      },
      {
        logicalName: "体重",
        physicalName: "WEIGHT",
        type: "DECIMAL",
        size: "6, 3"
      },
      {
        logicalName: "BMI",
        physicalName: "BMI",
        type: "DECIMAL",
        size: "6, 3"
      },
      {
        logicalName: "標準体重",
        physicalName: "STANDARD_WEIGHT",
        type: "DECIMAL",
        size: "6, 3"
      },
      {
        logicalName: "健康情報ステータス",
        physicalName: "HEALTH_INFO_STATUS",
        type: "VARCHAR",
        size: 2
      },
      {
        logicalName: "健康情報作成日時",
        physicalName: "HEALTH_INFO_REG_DATE",
        type: "TIMESTAMP",
        size: ""
      },
      {
        logicalName: "更新日時",
        physicalName: "UPDATE_DATE",
        type: "TIMESTAMP",
        size: ""
      },
      {
        logicalName: "登録日時",
        physicalName: "REG_DATE",
        type: "TIMESTAMP",
        size: ""
      }
    ]
  },
  {
    physicalName: "ACCOUNT",
    logicalName: "アカウント情報",
    column : [
      {
        logicalName: "ユーザID",
        physicalName: "USER_ID",
        type: "VARCHAR",
        size: "16"
      },
      {
        logicalName: "パスワード",
        physicalName: "PASSWORD",
        type: "VARCHAR",
        size: "16"
      },
      {
        logicalName: "削除フラグ",
        physicalName: "DELETE_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        logicalName: "パスワード有効期限",
        physicalName: "PASSWORD_EXPIRE",
        type: "DATE",
        size: ""
      },
      {
        logicalName: "備考",
        physicalName: "REMARKS",
        type: "VARCHAR",
        size: "256"
      },
      {
        logicalName: "APIキー",
        physicalName: "API_KEY",
        type: "VARCHAR",
        size: "64"
      },
      {
        logicalName: "更新日時",
        physicalName: "UPDATE_DATE",
        type: "TIMESTAMP",
        size: ""
      },
      {
        logicalName: "登録日時",
        physicalName: "REG_DATE",
        type: "TIMESTAMP",
        size: ""
      }
    ]
  },
  {
    physicalName: "HEALTH_INFO_FILE_SETTING",
    logicalName: "健康情報ファイル設定",
    column : [
      {
        logicalName: "ユーザID",
        physicalName: "USER_ID",
        type: "VARCHAR",
        size: "16"
      },
      {
        logicalName: "ヘッダーフラグ",
        physicalName: "HEADER_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        logicalName: "フッターフラグ",
        physicalName: "FOOTER_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        logicalName: "マスクフラグ",
        physicalName: "MASK_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        logicalName: "囲い文字利用フラグ",
        physicalName: "ENCLOSURE_CHAR_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        logicalName: "更新日時",
        physicalName: "UPDATE_DATE",
        type: "TIMESTAMP",
        size: ""
      },
      {
        logicalName: "登録日時",
        physicalName: "REG_DATE",
        type: "TIMESTAMP",
        size: ""
      }
    ]
  },
  {
    physicalName: "MAIL_INFO",
    logicalName: "メール情報",
    column : [
      {
        logicalName: "ユーザID",
        physicalName: "USER_ID",
        type: "VARCHAR",
        size: "16"
      },
      {
        logicalName: "メールアドレス",
        physicalName: "MAIL_ADDRESS",
        type: "VARCHAR",
        size: "64"
      },
      {
        logicalName: "メールパスワード",
        physicalName: "MAIL_PASSWORD",
        type: "VARCHAR",
        size: "64"
      },
      {
        logicalName: "更新日時",
        physicalName: "UPDATE_DATE",
        type: "TIMESTAMP",
        size: ""
      },
      {
        logicalName: "登録日時",
        physicalName: "REG_DATE",
        type: "TIMESTAMP",
        size: ""
      }
    ]
  },
  {
      physicalName: "BMI_RANGE_MT",
      logicalName: "BMI範囲マスタ",
      column : [
        {
          logicalName: "BMI範囲ID",
          physicalName: "BMI_RANGE_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "範囲下限",
          physicalName: "RANGE_MIN",
          type: "INT",
          size: ""
        },
        {
          logicalName: "範囲上限",
          physicalName: "RANGE_MAX",
          type: "INT",
          size: ""
        },
        {
          logicalName: "肥満度ステータス",
          physicalName: "OVER_WEIGHT_STATUS",
          type: "VARCHAR",
          size: "2"
        },
        {
          logicalName: "更新日時",
          physicalName: "UPDATE_DATE",
          type: "TIMESTAMP",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "TIMESTAMP",
          size: ""
        }
      ]
    }
];

// 指定したテーブル名のカラム情報をすべて取得する
function getColumnList(tableName) {
  for (var i = 0; i < table.length; i++) {
    var tableData = table[i];
    if (tableData.physicalName === tableName) {
      return tableData.column;
    }
  }
}
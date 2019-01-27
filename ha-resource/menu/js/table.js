var table = [
  {
    physicalName: "HEALTH_INFO",
    logicalName: "健康情報",
    column : [
      {
        physicalName: "健康情報ID",
        logicalName: "HEALTH_INFO_ID",
        type: "INTEGER",
        size: ""
      },
      {
        physicalName: "ユーザID",
        logicalName: "USER_ID",
        type: "VARCHAR",
        size: "16"
      },
      {
        physicalName: "身長",
        logicalName: "HEIGHT",
        type: "DECIMAL",
        size: "6, 3"
      },
      {
        physicalName: "体重",
        logicalName: "WEIGHT",
        type: "DECIMAL",
        size: "6, 3"
      },
      {
        physicalName: "BMI",
        logicalName: "BMI",
        type: "DECIMAL",
        size: "6, 3"
      },
      {
        physicalName: "標準体重",
        logicalName: "STANDARD_WEIGHT",
        type: "DECIMAL",
        size: "6, 3"
      },
      {
        physicalName: "健康情報ステータス",
        logicalName: "HEALTH_INFO_STATUS",
        type: "VARCHAR",
        size: 2
      },
      {
        physicalName: "更新日時",
        logicalName: "UPDATE_DATE",
        type: "TIMESTAMP",
        size: ""
      },
      {
        physicalName: "登録日時",
        logicalName: "REG_DATE",
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
        physicalName: "ユーザID",
        logicalName: "USER_ID",
        type: "VARCHAR",
        size: "16"
      },
      {
        physicalName: "パスワード",
        logicalName: "PASSWORD",
        type: "VARCHAR",
        size: "16"
      },
      {
        physicalName: "削除フラグ",
        logicalName: "DELETE_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        physicalName: "パスワード有効期限",
        logicalName: "PASSWORD_EXPIRE",
        type: "DATE",
        size: ""
      },
      {
        physicalName: "備考",
        logicalName: "REMARKS",
        type: "VARCHAR",
        size: "256"
      },
      {
        physicalName: "APIキー",
        logicalName: "API_KEY",
        type: "VARCHAR",
        size: "64"
      },
      {
        physicalName: "更新日時",
        logicalName: "UPDATE_DATE",
        type: "TIMESTAMP",
        size: ""
      },
      {
        physicalName: "登録日時",
        logicalName: "REG_DATE",
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
        physicalName: "ユーザID",
        logicalName: "USER_ID",
        type: "VARCHAR",
        size: "16"
      },
      {
        physicalName: "ヘッダーフラグ",
        logicalName: "HEADER_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        physicalName: "フッターフラグ",
        logicalName: "FOOTER_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        physicalName: "マスクフラグ",
        logicalName: "MASK_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        physicalName: "囲い文字利用フラグ",
        logicalName: "ENCLOSURE_CHAR_FLAG",
        type: "VARCHAR",
        size: "1"
      },
      {
        physicalName: "更新日時",
        logicalName: "UPDATE_DATE",
        type: "TIMESTAMP",
        size: ""
      },
      {
        physicalName: "登録日時",
        logicalName: "REG_DATE",
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
        physicalName: "ユーザID",
        logicalName: "USER_ID",
        type: "VARCHAR",
        size: "16"
      },
      {
        physicalName: "メールアドレス",
        logicalName: "MAIL_ADDRESS",
        type: "VARCHAR",
        size: "64"
      },
      {
        physicalName: "メールパスワード",
        logicalName: "MAIL_PASSWORD",
        type: "VARCHAR",
        size: "64"
      },
      {
        physicalName: "更新日時",
        logicalName: "UPDATE_DATE",
        type: "TIMESTAMP",
        size: ""
      },
      {
        physicalName: "登録日時",
        logicalName: "REG_DATE",
        type: "TIMESTAMP",
        size: ""
      }
    ]
  }
];
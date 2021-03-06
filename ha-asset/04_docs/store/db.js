export const state = () => ({
  tables: [{
      physicalName: "HEALTH_INFO",
      logicalName: "健康情報",
      columns: [{
          logicalName: "健康情報ID",
          physicalName: "SEQ_HEALTH_INFO_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "ユーザID",
          physicalName: "SEQ_USER_ID",
          type: "INT",
          size: ""
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
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "BMI範囲マスタID",
          physicalName: "SEQ_BMI_RANGE_MT_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "更新日時",
          physicalName: "UPDATE_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    },
    {
      physicalName: "ACCOUNT",
      logicalName: "アカウント情報",
      columns: [{
          logicalName: "ユーザID",
          physicalName: "SEQ_USER_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "メールアドレス",
          physicalName: "MAILADDRESS",
          type: "VARCHAR",
          size: "64"
        },
        {
          logicalName: "パスワード",
          physicalName: "PASSWORD",
          type: "VARCHAR",
          size: "44"
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
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    },
    {
      physicalName: "HEALTH_INFO_FILE_SETTING",
      logicalName: "健康情報ファイル設定",
      columns: [{
          logicalName: "ユーザID",
          physicalName: "SEQ_USER_ID",
          type: "INT",
          size: ""
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
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    },
    {
      physicalName: "BMI_RANGE_MT",
      logicalName: "BMI範囲マスタ",
      columns: [{
          logicalName: "BMI範囲マスタID",
          physicalName: "SEQ_BMI_RANGE_MT_ID",
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
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    },
    {
      physicalName: "ACCOUNT_RECOVERY_TOKEN_DATA",
      logicalName: "アカウント回復トークン情報",
      columns: [{
          logicalName: "アカウント回復トークン情報ID",
          physicalName: "SEQ_ACCOUNT_RECOVERY_TOKEN_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "ユーザID",
          physicalName: "SEQ_USER_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "トークン",
          physicalName: "TOKEN",
          type: "VARCHAR",
          size: "64"
        },
        {
          logicalName: "トークン作成日時",
          physicalName: "TOKEN_CREATE_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "更新日時",
          physicalName: "UPDATE_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    },
    {
      physicalName: "API_COMMUNICATION_DATA",
      logicalName: "API通信情報",
      columns: [{
          logicalName: "API通信情報ID",
          physicalName: "SEQ_API_COMMUNICATION_DATA_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "API名",
          physicalName: "API_NAME",
          type: "VARCHAR",
          size: "64"
        },
        {
          logicalName: "ユーザID",
          physicalName: "SEQ_USER_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "HTTPステータス",
          physicalName: "HTTP_STATUS",
          type: "VARCHAR",
          size: "3"
        },
        {
          logicalName: "処理結果",
          physicalName: "RESULT",
          type: "VARCHAR",
          size: "1"
        },
        {
          logicalName: "エラー詳細",
          physicalName: "DETAIL",
          type: "VARCHAR",
          size: "256"
        },
        {
          logicalName: "リクエスト送信日時",
          physicalName: "REQUEST_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "レスポンス受信日時",
          physicalName: "RESPONSE_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "更新日時",
          physicalName: "UPDATE_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    },
    {
      physicalName: "ROOT_LOGIN_INFO",
      logicalName: "管理者サイトユーザログイン情報",
      columns: [{
          logicalName: "管理者サイトユーザログイン情報ID",
          physicalName: "SEQ_ROOT_LOGIN_INFO_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "パスワード",
          physicalName: "PASSWORD",
          type: "VARCHAR",
          size: "64"
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
          logicalName: "管理者サイトユーザ権限管理マスタID",
          physicalName: "SEQ_ROOT_USER_ROLE_MNG_MT_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "備考",
          physicalName: "REMARKS",
          type: "VARCHAR",
          size: "256"
        },
        {
          logicalName: "更新日時",
          physicalName: "UPDATE_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    },
    {
      physicalName: "ROOT_USER_ROLE_MNG_MT",
      logicalName: "管理者サイトユーザ権限管理マスタ",
      columns: [{
          logicalName: "管理者サイトユーザ権限管理マスタID",
          physicalName: "SEQ_ROOT_USER_ROLE_MNG_MT_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "更新日時",
          physicalName: "UPDATE_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    },
    {
      physicalName: "ROOT_USER_ROLE_DETAIL_MT",
      logicalName: "管理者サイトユーザ権限詳細マスタ",
      columns: [{
          logicalName: "管理者サイトユーザ権限詳細マスタID",
          physicalName: "SEQ_ROOT_USER_ROLE_DETAIL_MT_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "管理者サイトユーザ権限管理マスタID",
          physicalName: "SEQ_ROOT_USER_ROLE_MNG_MT_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "管理者サイト権限マスタID",
          physicalName: "SEQ_ROOT_ROLE_MT_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "更新日時",
          physicalName: "UPDATE_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    },
    {
      physicalName: "ROOT_ROLE_MT",
      logicalName: "管理者サイト権限マスタ",
      columns: [{
          logicalName: "管理者サイト権限マスタID",
          physicalName: "SEQ_ROOT_ROLE_MT_ID",
          type: "INT",
          size: ""
        },
        {
          logicalName: "ユーザ権限",
          physicalName: "ROLE",
          type: "VARCHAR",
          size: "2"
        },
        {
          logicalName: "ユーザ権限名",
          physicalName: "ROLE_NAME",
          type: "VARCHAR",
          size: "64"
        },
        {
          logicalName: "更新日時",
          physicalName: "UPDATE_DATE",
          type: "DATETIME",
          size: ""
        },
        {
          logicalName: "登録日時",
          physicalName: "REG_DATE",
          type: "DATETIME",
          size: ""
        }
      ]
    }
  ]
});

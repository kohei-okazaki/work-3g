# 健康情報APIドキュメント

## 作業ディレクトリへ移動
```
cd C:\app\git\work-3g\ha-asset\01_design\02_api\90_sphinx
```
## APIドキュメントプロジェクトを生成
※新しくドキュメントロジェクトを作成する場合、以下のコマンドを実行する

```
sphinx-quickstart docs
```

## pythonファイルからrstファイルを生成
```
sphinx-apidoc -f -o ./docs .
```

## rstファイルからhtmlファイルを生成
```
sphinx-build -b singlehtml ./docs ./docs/_build
```

## API一覧
* 健康情報登録API : health_info_regist_api.py
* 健康情報照会API : health_info_ref_api.py
* ヘルスチェックAPI : health_check_api.py
* トークン発行API : token_api.py
* 基礎健康情報計算API : basic_calc_api.py
* カロリー計算API : calorie_calc_api.py
* 肺活量計算API: breathing_capacity_api.py
# 健康情報APIドキュメント

## 作業ディレクトリへ移動
```
cd C:\app\git\work-3g\ha-resource\01_design\02_api\90_sphinx
```
## APIドキュメントプロジェクトを生成
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
* 健康情報登録API : health_info_regist.py
* 健康情報照会API : health_info_ref.py
* ヘルスチェックAPI : health_check.py
* 基礎健康情報計算API : basic_calc.py
* カロリー計算API : calorie_calc.py

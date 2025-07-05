# build.sh
# API Gateway + lambdaのビルド用のシェル。
# 前提：
# (1) AWS Cloud shellで以下のファイルをアップロードすること
#   - basic.mjs
#   - breathing_capacity.mjs
#   - calorie.mjs
# (2) カレンドディレクトリが「/home/cloudshell-user」であること。
# 

echo "build.sh start..."

mv samconfig.toml health-api-sam/samconfig.toml
mv template.yaml health-api-sam/template.yaml
mv basic.mjs health-api-sam/basic/index.mjs
mv breathing_capacity.mjs health-api-sam/breathing_capacity/index.mjs
mv calorie.mjs health-api-sam/calorie/index.mjs

cd health-api-sam

sam build

sam deploy --guided

echo "build.sh finish..."

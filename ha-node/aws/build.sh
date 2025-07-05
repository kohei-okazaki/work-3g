#!/bin/bash
#
# API Gateway + lambdaのビルド用のシェル
#
# 前提：
# (1) AWS Cloud shellで以下のファイルをアップロードすること
#   - basic.mjs
#   - breathing_capacity.mjs
#   - calorie.mjs
# (2) カレンドディレクトリが「/home/cloudshell-user」であること。
# 

HOME_DIR="/home/cloudshell-user"
BASE_DIR="${HOME_DIR}/health-api-sam"

echo "build.sh start..."

# conf
mv ${HOME_DIR}/samconfig.toml ${BASE_DIR}/samconfig.toml
mv ${HOME_DIR}/template.yaml ${BASE_DIR}/template.yaml

# module
mv ${HOME_DIR}/basic.mjs ${BASE_DIR}/basic/index.mjs
mv ${HOME_DIR}/breathing_capacity.mjs ${BASE_DIR}/breathing_capacity/index.mjs
mv ${HOME_DIR}/calorie.mjs ${BASE_DIR}/calorie/index.mjs

cd ${BASE_DIR}

# build
sam build
build_result=$?
if [ ${build_result} -eq 0 ]; then
  echo "build success."
else
  echo "build failure."
fi

# deploy
sam deploy --guided
deploy_result=$?
if [ ${deploy_result} -eq 0 ]; then
  echo "deploy success."
else
  echo "deploy failure."
fi

echo "build.sh finish..."

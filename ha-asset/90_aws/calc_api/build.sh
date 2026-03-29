#!/bin/bash
#
# API Gateway + Lambda(Python 3.14) のビルド用シェル
#
# 前提:
# (1) AWS CloudShell に以下をアップロードすること
#   - basic.py
#   - breathing_capacity.py
#   - calorie.py
#   - template.yaml
#   - samconfig.toml
# (2) カレントディレクトリが /home/cloudshell-user であること
# (3) health-api-sam 配下に basic / breathing_capacity / calorie ディレクトリがあること
#

HOME_DIR="/home/cloudshell-user"
BASE_DIR="${HOME_DIR}/health-api-sam"

echo "build.sh start..."

# conf
mv ${HOME_DIR}/samconfig.toml ${BASE_DIR}/samconfig.toml
mv ${HOME_DIR}/template.yaml ${BASE_DIR}/template.yaml

# module
mv ${HOME_DIR}/basic.py ${BASE_DIR}/basic/app.py
mv ${HOME_DIR}/breathing_capacity.py ${BASE_DIR}/breathing_capacity/app.py
mv ${HOME_DIR}/calorie.py ${BASE_DIR}/calorie/app.py

cd ${BASE_DIR}

# build
# sam build だとPython3.14がcloud shellで見つからずにエラーとなる
sam build --use-container
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

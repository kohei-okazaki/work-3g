#!/bin/bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "${SCRIPT_DIR}"

STACK_NAME="${STACK_NAME:-calc-api}"
ARTIFACT_BUCKET="${ARTIFACT_BUCKET:-}"
ARTIFACT_PREFIX="${ARTIFACT_PREFIX:-calc-api}"
DEPLOY_REGION="${DEPLOY_REGION:-ap-northeast-1}"
STAGE_NAME="${STAGE_NAME:-dev}"

if [ -z "${ARTIFACT_BUCKET}" ]; then
  echo "ARTIFACT_BUCKET is required."
  exit 1
fi

aws cloudformation package \
  --template-file template.yaml \
  --s3-bucket "${ARTIFACT_BUCKET}" \
  --s3-prefix "${ARTIFACT_PREFIX}" \
  --output-template-file packaged-template.yaml \
  --region "${DEPLOY_REGION}"

aws cloudformation validate-template \
  --template-body file://packaged-template.yaml \
  --region "${DEPLOY_REGION}"

aws cloudformation deploy \
  --template-file packaged-template.yaml \
  --stack-name "${STACK_NAME}" \
  --capabilities CAPABILITY_IAM \
  --no-fail-on-empty-changeset \
  --parameter-overrides StageName="${STAGE_NAME}" \
  --region "${DEPLOY_REGION}"

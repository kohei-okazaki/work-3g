#!/usr/bin/env bash
set -euo pipefail

: "${AWS_DEFAULT_REGION:?Set AWS_DEFAULT_REGION in the Commands action}"
: "${ECS_CLUSTER_NAME:?Set ECS_CLUSTER_NAME in the Commands action}"
: "${ECS_SERVICE_NAME:?Set ECS_SERVICE_NAME in the Commands action}"
test -s image-uri.txt
EXPECTED_IMAGE=$(cat image-uri.txt)

TASK_DEFINITION=$(aws ecs describe-services \
  --cluster "$ECS_CLUSTER_NAME" --services "$ECS_SERVICE_NAME" \
  --query 'services[0].taskDefinition' --output text)
if [ -z "$TASK_DEFINITION" ] || [ "$TASK_DEFINITION" = "None" ]; then
  echo "The configured ECS service was not found." >&2
  exit 1
fi

CURRENT_IMAGE=$(aws ecs describe-task-definition \
  --task-definition "$TASK_DEFINITION" \
  --query 'taskDefinition.containerDefinitions[?name==`ApiContainer`].image | [0]' \
  --output text)
if [ "$CURRENT_IMAGE" != "$EXPECTED_IMAGE" ]; then
  echo "ApiContainer image does not match the published :dev image." >&2
  echo "Check the Terraform image_tag and the pipeline target configuration." >&2
  exit 1
fi

# Keep the task definition and desired count managed by Terraform.
aws ecs update-service \
  --cluster "$ECS_CLUSTER_NAME" --service "$ECS_SERVICE_NAME" \
  --force-new-deployment \
  --query 'service.{service:serviceName,desired:desiredCount,taskDefinition:taskDefinition}'

aws ecs wait services-stable \
  --cluster "$ECS_CLUSTER_NAME" --services "$ECS_SERVICE_NAME"

DESIRED_COUNT=$(aws ecs describe-services \
  --cluster "$ECS_CLUSTER_NAME" --services "$ECS_SERVICE_NAME" \
  --query 'services[0].desiredCount' --output text)
if [ "$DESIRED_COUNT" = "0" ]; then
  echo "Deployment request completed, but desired_count=0; the API is not running."
else
  echo "The ECS service is stable. Check the application logs and health endpoint."
fi

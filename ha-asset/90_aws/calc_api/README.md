# calc_api

This directory manages the health calculation HTTP API with plain
CloudFormation. AWS SAM is not used.

## Files

- `template.yaml`: CloudFormation template for API Gateway HTTP API, Lambda
  functions, Lambda execution role, routes, integrations, and invoke
  permissions.
- `lambda/`: Lambda source code.
- `buildspec.yml`: CodeBuild buildspec that runs `aws cloudformation package`
  and `aws cloudformation deploy`.
- `build.sh`: Optional helper for running the same deployment from CloudShell
  or a local shell.

## CodeBuild settings

Create the CodeBuild project manually from the AWS console or AWS CLI.

Recommended settings:

- Source: this repository.
- Buildspec: `ha-asset/90_aws/calc_api/buildspec.yml`.
- Environment image: a Linux managed image with AWS CLI.
- Privileged mode: OFF.
- Artifacts: none.

Environment variables:

| Name | Value |
| --- | --- |
| `STACK_NAME` | `calc-api` |
| `ARTIFACT_BUCKET` | S3 bucket for CloudFormation package artifacts |
| `ARTIFACT_PREFIX` | `calc-api` |
| `DEPLOY_REGION` | `ap-northeast-1` |
| `STAGE_NAME` | `dev` |

Create `ARTIFACT_BUCKET` before starting the build. The Lambda zip artifact is
uploaded there by `aws cloudformation package`.

## CodeBuild service role

Start with the permissions below, then narrow resources for production use.

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "cloudformation:CreateChangeSet",
        "cloudformation:CreateStack",
        "cloudformation:DeleteChangeSet",
        "cloudformation:DescribeChangeSet",
        "cloudformation:DescribeStacks",
        "cloudformation:ExecuteChangeSet",
        "cloudformation:GetTemplateSummary",
        "cloudformation:UpdateStack"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "s3:GetBucketLocation",
        "s3:ListBucket"
      ],
      "Resource": "arn:aws:s3:::<ARTIFACT_BUCKET>"
    },
    {
      "Effect": "Allow",
      "Action": [
        "s3:GetObject",
        "s3:PutObject"
      ],
      "Resource": "arn:aws:s3:::<ARTIFACT_BUCKET>/*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "lambda:*",
        "apigateway:*",
        "iam:CreateRole",
        "iam:DeleteRole",
        "iam:GetRole",
        "iam:PassRole",
        "iam:AttachRolePolicy",
        "iam:DetachRolePolicy",
        "iam:PutRolePolicy",
        "iam:DeleteRolePolicy"
      ],
      "Resource": "*"
    }
  ]
}
```

## Manual deployment

```bash
cd ha-asset/90_aws/calc_api
ARTIFACT_BUCKET=<bucket-name> ./build.sh
```

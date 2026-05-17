#!/usr/bin/env node
import * as cdk from 'aws-cdk-lib';
import { HaApiStack } from '../lib/ha-api-stack';
import { HaDashboardStack } from '../lib/ha-dashboard-stack';
import { HaSharedInfraStack } from '../lib/ha-shared-infra-stack';

const app = new cdk.App();

const env = {
  account: process.env.CDK_DEFAULT_ACCOUNT,
  region: process.env.CDK_DEFAULT_REGION ?? 'ap-northeast-1',
};

const sharedInfraStack = new HaSharedInfraStack(app, 'HaSharedInfraStack', {
  env,
});

new HaDashboardStack(app, 'HaDashboardStack', {
  env,
  sharedInfraStack,
});

new HaApiStack(app, 'HaApiStack', {
  env,
  sharedInfraStack,
});

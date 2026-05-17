#!/usr/bin/env node
import * as cdk from 'aws-cdk-lib';
import { HaDashboardStack } from '../lib/ha-dashboard-stack';

const app = new cdk.App();

new HaDashboardStack(app, 'HaDashboardStack', {
  env: {
    account: process.env.CDK_DEFAULT_ACCOUNT,
    region: process.env.CDK_DEFAULT_REGION ?? 'ap-northeast-1',
  },
});

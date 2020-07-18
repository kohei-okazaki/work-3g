/**
 * 環境情報についてのスクリプト
 */
// import dotenv from 'dotenv'
var dotenv = require('dotenv');
// .envをprocess.envに割当て
dotenv.config();

const setConfig = {

    // EC2環境用
    ec2 : {
        name : 'ec2'
    },
    // 開発用
    local : {
        name : 'local'
    }
};

exports.config = setConfig[process.env.ENV];

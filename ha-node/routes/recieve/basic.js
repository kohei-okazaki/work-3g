var express = require('express');
var router = express.Router();
var util = require('../util/util');
var env = require('../conf/env');
var prettyjson = require('prettyjson');

/**
 * 健康情報計算の処理<br>
 * 身長と体重からBMIと標準体重を計算する
 *
 * @param req
 *            リクエスト情報
 * @param res
 *            レスポンス情報
 * @param next
 */
router.post('/', function(req, res, next) {

    console.log(prettyjson.render(req.body) + "\n");

    let meter_height = req.body['height'] / 100;
    let bmi = util.calc_bmi(req.body['weight'], meter_height);
    bmi = util.round(bmi, 3);

    let standard_weight = util.calc_standard_weight(meter_height);
    standard_weight = util.round(standard_weight, 3);

    let basic_health_info = {
        'height' : req.body['height'],
        'weight' : req.body['weight'],
        'bmi' : bmi,
        'standard_weight' : standard_weight
    }
    let res_body = {
        'result' : 0,
        'basic_health_info' : basic_health_info
    };

    console.log(prettyjson.render(res_body));

    res.json(res_body);
})

module.exports = router;

var express = require('express');
var router = express.Router();
var util = require('../util/util');
var prettyjson = require('prettyjson');
var PERCENTAGE = 100;

/**
 * 肺活量計算APIの受付<br>
 * 性別と年齢と身長と肺活量から「予測肺活量」「肺活量%」を計算する<br>
 * <b>予測肺活量の計算式</b><br>
 * <ul>
 * <li>男性の場合 = （27.63 - 0.122 * 年齢）* 身長（cm）</li>
 * <li>女性の場合 = （21.78 - 0.101 * 年齢）* 身長（cm）</li>
 * </ul>
 * <b>肺活量%の計算式</b><br>
 * <ul>
 * <li>肺活量 / 予測肺活量 * 100</li>
 * </ul>
 *
 * @param req
 *            リクエスト情報
 * @param res
 *            レスポンス情報
 * @param next
 */
router.get('/', function(req, res, next) {

  console.log(prettyjson.render(req.query) + "\n");

  let gender_info;
  if ("0" == req.query['gender']) {
    // 男性の場合
    gender_info = {
      "base_breathing_capacity_def": 3500,
      "base_val_def": 27.63,
      "minus_def": 0.122
    };
  } else if ("1" == req.query['gender']) {
    gender_info = {
      "base_breathing_capacity_def": 2500,
      "base_val_def": 21.78,
      "minus_def": 0.101
    };
  } else {
    throw new Error('gender is invalid. gender=' + req.query['gender']);
  }

  // 年齢部分の計算
  let calc_age = gender_info["minus_def"] * req.query['age'];
  calc_age = util.round(calc_age, 3);

  // 予測肺活量の計算
  let predict_breathing_capacity = (gender_info["base_val_def"] - calc_age) * req.query['height'];
  predict_breathing_capacity = util.round(predict_breathing_capacity, 3);

  // 肺活量％の計算
  let breathing_capacity_percentage = gender_info["base_breathing_capacity_def"] / predict_breathing_capacity * PERCENTAGE;
  breathing_capacity_percentage = util.round(breathing_capacity_percentage, 3);

  // レスポンスデータの作成
  let res_body = {
    'result': 0,
    'breathing_capacity_calc_result': {
      'predict_breathing_capacity': predict_breathing_capacity,
      'breathing_capacity_percentage': breathing_capacity_percentage,
    },
    'user_data': req.query
  };

  console.log(prettyjson.render(res_body));
  res.json(res_body);

})

module.exports = router;
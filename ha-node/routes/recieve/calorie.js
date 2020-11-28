var express = require('express');
var router = express.Router();
var util = require('../util/util');
var env = require('../conf/env');
var prettyjson = require('prettyjson');

/**
 * カロリー計算APIの受付<br>
 * 性別と年齢と身長と体重から「基礎代謝量」「1日の消費カロリー」を計算する<br>
 * <b>基礎代謝量の計算式</b><br>
 * <ul>
 * <li>男性の場合 = 13.397 * 体重(kg) + 4.799 * 身長(cm) - 5.677 * 年齢 + 88.362</li>
 * <li>女性の場合 = 9.247 * 体重(kg) + 3.098 * 身長(cm) - 4.33 * 年齢 + 447.593</li>
 * </ul>
 * <b>1日の消費カロリーの計算式</b><br>
 * <ul>
 * <li>基礎代謝量 + 生活活動代謝</li>
 * </ul>
 *
 * @param req
 *            リクエスト情報
 * @param res
 *            レスポンス情報
 * @param next
 */
router.post('/', function(req, res, next) {

    console.log(prettyjson.render(req.body) + "\n");

    let gender_info;
    if ("0" == req.body['gender']) {
        // 男性の場合
        gender_info = {
            "weight_def" : 13.397,
            "height_def" : 4.799,
            "age_def" : 5.677,
            "adjust_def" : 88.362
        };
    } else if ("1" == req.body['gender']) {
        gender_info = {
            "weight_def" : 9.247,
            "height_def" : 3.098,
            "age_def" : 4.33,
            "adjust_def" : 447.593
        };
    } else {
        throw new Error('gender is invalid. gender=' + req.body['gender']);
    }

    // 体重部分の計算
    let calc_weight = gender_info["weight_def"] * req.body['weight']
    calc_weight = util.round(calc_weight, 3);

    // 身長部分の計算
    let calc_height = gender_info["height_def"] * req.body['height']
    calc_height = util.round(calc_height, 3);

    // 年齢部分の計算
    let calc_age = gender_info["age_def"] * req.body['age']
    calc_age = util.round(calc_age, 3);

    // 生活活動代謝の計算
    let base_metabolism = calc_weight + calc_height - calc_age
            + gender_info["adjust_def"];
    base_metabolism = util.round(base_metabolism, 3);

    // 1日の消費カロリーの計算
    let lost_calorie_per_day = base_metabolism
            + req.body['life_work_metabolism'];
    lost_calorie_per_day = util.round(lost_calorie_per_day, 3);

    // レスポンスデータの作成
    let res_body = {
        'result' : 0,
        'calorie_calc_result' : {
            'base_metabolism' : base_metabolism,
            'lost_calorie_per_day' : lost_calorie_per_day,
        },
        'user_data' : req.body
    }

    console.log(prettyjson.render(res_body));
    res.json(res_body);

})

module.exports = router;

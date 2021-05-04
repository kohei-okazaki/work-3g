/**
 * 健康情報計算APIで使用するutil
 */

/**
 * 四捨五入を行う
 * 
 * @param val
 *            四捨五入したい値
 * @param index
 *            四捨五入したい少数値
 * @returns 四捨五入した値
 */
exports.round = function (val, index) {
  return Math.floor(val * Math.pow(10, index)) / Math.pow(10, index);
}

/**
 * BMIを計算する
 * 
 * @param weight
 *            体重(kg)
 * @param height
 *            身長(m)
 * @returns BMI
 */
exports.calc_bmi = function (weight, height) {
  return weight / (height * height);
}

/**
 * 標準体重を計算する
 * 
 * @param height
 *            身長(m)
 * @returns 標準体重
 */
exports.calc_standard_weight = function (height) {
  return height * height * 22;
}
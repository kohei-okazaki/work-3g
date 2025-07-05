export const handler = async (event) => {

  const PERCENTAGE = 100;

  // リクエスト情報
  let request_data = {
    "gender": parseInt(event.queryStringParameters.gender),
    "age": parseInt(event.queryStringParameters.age),
    "height": parseFloat(event.queryStringParameters.height)
  };

  let gender_info;
  if ("0" == request_data["gender"]) {
    // 男性の場合
    gender_info = {
      "base_breathing_capacity_def": 3500,
      "base_val_def": 27.63,
      "minus_def": 0.122
    };
  } else if ("1" == request_data["gender"]) {
    gender_info = {
      "base_breathing_capacity_def": 2500,
      "base_val_def": 21.78,
      "minus_def": 0.101
    };
  } else {
    throw new Error('gender is invalid. gender=' + request_data["gender"]);
  }

  // 年齢部分の計算
  let calc_age = gender_info["minus_def"] * request_data['age'];
  calc_age = Math.floor(calc_age * Math.pow(10, 3)) / Math.pow(10, 3);

  // 予測肺活量の計算
  let predict_breathing_capacity = (gender_info["base_val_def"] - calc_age) * request_data['height'];
  predict_breathing_capacity = Math.floor(predict_breathing_capacity * Math.pow(10, 3)) / Math.pow(10, 3);

  // 肺活量％の計算
  let breathing_capacity_percentage = gender_info["base_breathing_capacity_def"] / predict_breathing_capacity * PERCENTAGE;
  breathing_capacity_percentage = Math.floor(breathing_capacity_percentage * Math.pow(10, 3)) / Math.pow(10, 3);

  return {
    statusCode: 200,
    headers: {
      // CORS対策用
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      result: 0,
      breathing_capacity_calc_result: {
        predict_breathing_capacity: predict_breathing_capacity,
        breathing_capacity_percentage: breathing_capacity_percentage,
      },
      user_data: request_data,
    }),
  };
};

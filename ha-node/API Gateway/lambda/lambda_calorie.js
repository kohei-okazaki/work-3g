export const handler = async (event) => {

  // リクエスト情報
  let request_data = {
    "gender": parseInt(event.queryStringParameters.gender),
    "age": parseInt(event.queryStringParameters.age),
    "height": parseFloat(event.queryStringParameters.height),
    "weight": parseFloat(event.queryStringParameters.weight),
    "life_work_metabolism": parseFloat(event.queryStringParameters.life_work_metabolism)
  };

  let gender_info;
  if (0 == request_data["gender"]) {
    // 男性の場合
    gender_info = {
      "weight_def": 13.397,
      "height_def": 4.799,
      "age_def": 5.677,
      "adjust_def": 88.362
    };
  } else if (1 == request_data["gender"]) {
    gender_info = {
      "weight_def": 9.247,
      "height_def": 3.098,
      "age_def": 4.33,
      "adjust_def": 447.593
    };
  } else {
    throw new Error('gender is invalid. gender=' + request_data["gender"]);
  }

  // 体重部分の計算
  let calc_weight = gender_info["weight_def"] * request_data["weight"];
  calc_weight = Math.floor(calc_weight * Math.pow(10, 3)) / Math.pow(10, 3);

  // 身長部分の計算
  let calc_height = gender_info["height_def"] * request_data["height"];
  calc_height = Math.floor(calc_height * Math.pow(10, 3)) / Math.pow(10, 3);

  // 年齢部分の計算
  let calc_age = gender_info["age_def"] * request_data["age"];
  calc_age = Math.floor(calc_age * Math.pow(10, 3)) / Math.pow(10, 3);

  // 生活活動代謝の計算
  let base_metabolism = calc_weight + calc_height - calc_age + gender_info["adjust_def"];
  base_metabolism = Math.floor(base_metabolism * Math.pow(10, 3)) / Math.pow(10, 3);

  // 1日の消費カロリーの計算
  let lost_calorie_per_day = base_metabolism + request_data["life_work_metabolism"];
  lost_calorie_per_day = Math.floor(lost_calorie_per_day * Math.pow(10, 3)) / Math.pow(10, 3);

  return {
    statusCode: 200,
    headers: {
      // CORS対策用
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      result: 0,
      calorie_calc_result: {
        base_metabolism: base_metabolism,
        lost_calorie_per_day: lost_calorie_per_day,
      },
      user_data: request_data,
    }),
  };
};

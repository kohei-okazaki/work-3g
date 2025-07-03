export const handler = async (event) => {

  // 身長(cm)
  let height = parseFloat(event.queryStringParameters.height);
  // 体重(kg)
  let weight = parseFloat(event.queryStringParameters.weight);
  // 身長(m)
  let meter_height = height / 100;
  // BMI
  let bmi = weight / (meter_height * meter_height);
  bmi = Math.floor(bmi * Math.pow(10, 3)) / Math.pow(10, 3);
  // 標準体重
  let standard_weight = 22 * (meter_height * meter_height);
  standard_weight = Math.floor(standard_weight * Math.pow(10, 3)) / Math.pow(10, 3);

  // 基礎健康情報
  let basic_health_info = {
    'height': height,
    'weight': weight,
    'bmi': bmi,
    'standard_weight': standard_weight
  }

  return {
    statusCode: 200,
    headers: {
      // CORS対策用
      "Access-Control-Allow-Origin": "*",
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      result: 0,
      basic_health_info: basic_health_info,
    }),
  };
};

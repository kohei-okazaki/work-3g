const apiBaseURL =
  process.env.API_BASE_URL ||
  process.env.api_base_url ||
  "http://localhost:8000/api/root/";

module.exports = {
  // 環境名
  name: "dev",
  // API基底URL
  api_base_url: apiBaseURL,
};

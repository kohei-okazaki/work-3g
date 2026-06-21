const apiBaseURL = process.env.API_BASE_URL;

if (!apiBaseURL) {
  throw new Error("API_BASE_URL is required for the dev environment.");
}

module.exports = {
  // 環境名
  name: "dev",
  // API基底URL
  api_base_url: apiBaseURL,
};

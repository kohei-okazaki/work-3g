const apiBaseURL = process.env.API_BASE_URL || process.env.api_base_url || 'http://localhost:8082/api/root/'

module.exports = {
  name: 'production',
  api_base_url: apiBaseURL
}

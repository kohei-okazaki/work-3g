var jwt = require('jsonwebtoken');
var errorMessage = require('./error_message');

/**
 * Token認証に関連するUtil
 */

// トークンキー
exports.key = "developer";

/**
 * Token認証処理を行う
 *
 * @param req
 *            リクエスト情報
 * @param res
 *            レスポンス情報
 * @param next
 *            Next
 */
exports.token_auth = function(req, res, next) {

    try {
        let token_data = req.headers['x-node-token'];
        if (!token_data) {
            // tokenが未指定の場合
            return res.json({
                "result" : 1,
                "detail" : errorMessage.not_found_token
            });
        }

        // token認証
        jwt.verify(token_data, this.key, function(err, decoded) {
            if (err) {
                return res.json({
                    "result" : 1,
                    "detail" : errorMessage.invalid_token
                });
            }
            req.decoded = decoded;
            next();
        });
    } catch (Error) {
        console.log(Error);
    }
}
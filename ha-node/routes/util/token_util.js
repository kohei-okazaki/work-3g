var jwt = require('jsonwebtoken');

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
                "detail" : 'Token is not found'
            });
        }

        // token認証
        jwt.verify(token_data, this.key, function(err, decoded) {
            if (err) {
                return res.json({
                    "result" : 1,
                    "detail" : 'Token is invalid'
                });
            }
            req.decoded = decoded;
            next();
        });
    } catch (Error) {
        console.log(Error);
    }
}
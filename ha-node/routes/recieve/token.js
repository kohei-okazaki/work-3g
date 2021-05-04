var express = require('express');
var router = express.Router();
var tokenUtil = require('../util/token_util');
var prettyjson = require('prettyjson');
var jwt = require('jsonwebtoken');

/**
 * 認証Token発行API
 *
 * @param req
 *            リクエスト情報
 * @param res
 *            レスポンス情報
 * @param next
 */
router.post('/', function (req, res, next) {

  try {
    console.log(prettyjson.render(req.body) + "\n");

    var token = jwt.sign(req.body, tokenUtil.key, {
      expiresIn: '1h'
    });

    var res_body = {
      "result": 0,
      "token": token
    };
    console.log(prettyjson.render(res_body));

    res.json(res_body);
  } catch (Error) {
    console.log(Error);
  }

})

module.exports = router;
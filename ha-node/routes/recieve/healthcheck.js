var express = require('express');
var router = express.Router();
var prettyjson = require('prettyjson');

router.get('/', function (req, res, next) {
  console.log(prettyjson.render(req.query) + "\n");

  let res_body = {
    'result': 0,
  };

  console.log(prettyjson.render(res_body));

  res.json(res_body);
});

module.exports = router;
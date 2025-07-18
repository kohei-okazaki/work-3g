var createError = require('http-errors');
var express = require('express');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var dotenv = require('dotenv');
var tokenUtil = require('./routes/util/token_util');
var errorMessage = require('./routes/util/error_message');

// ルーティング関数
var basicRouter = require('./routes/recieve/basic');
var calorieRouter = require('./routes/recieve/calorie');
var breathingCapacityRouter = require('./routes/recieve/breathing_capacity');
var tokenRouter = require('./routes/recieve/token');
var healthcheckRouter = require('./routes/recieve/healthcheck');

var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({
  extended: false
}));
app.use(cookieParser());
app.use('/token', tokenRouter);
app.use('/healthcheck', healthcheckRouter);

// .envをprocess.envに割当て
dotenv.config();
const setConfig = {
  // ローカル環境用
  local: {
    name: "local環境",
    port: 8084
  },
  // dev1環境用
  dev1: {
    name: "dev1環境",
    port: 3000
  }
}
const config = setConfig[process.env.ENV];
console.log(config.name);

// Token認証用フィルタ
app.use(function (req, res, next) {
  tokenUtil.token_auth(req, res, next);
});

app.use('/basic', basicRouter);
app.use('/calorie', calorieRouter);
app.use('/breathing_capacity', breathingCapacityRouter);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  // res.status(err.status || 200);
  let err_response = {
    "status": 1,
    "detail": errorMessage.fail_healthinfo_calc
  }
  res.json(err_response);
});

module.exports = app;
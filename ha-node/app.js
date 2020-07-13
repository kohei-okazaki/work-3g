var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var dotenv = require('dotenv');

// ルーティング関数
var basicRouter = require('./routes/recieve/basic');
var calorieRouter = require('./routes/recieve/calorie');

var app = express();

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({
    extended : false
}));
app.use(cookieParser());

app.use('/basic', basicRouter);
app.use('/calorie', calorieRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
    next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get('env') === 'development' ? err : {};

    // 環境ファイルを読込
    // dotenv.config();
    // console.log("環境名=" + process.env.ENV);

    // render the error page
    res.status(err.status || 200);
    let err_response = {
        "status" : 1,
        "detail" : "健康情報の計算に失敗しました"
    }
    res.json(err_response);
});

module.exports = app;

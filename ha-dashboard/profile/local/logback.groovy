import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.*
import static ch.qos.logback.core.spi.FilterReply.*

scan("30 seconds")

def FILE_PATH = "C:/app/logs/";
def ENCODE = "UTF-8";
def appenderList = ["STDOUT", "DEBUG_FILE", "INFO_FILE", "WARN_FILE", "ERROR_FILE"];

appender("STDOUT", ConsoleAppender) {

  target = "System.out"

  encoder(PatternLayoutEncoder) {
    charset = Charset.forName("${ENCODE}")
    pattern = "%date{yyyy/MM/dd HH:mm:ss} [%thread] %X{id} %highlight(%-5level) %logger{10} - %msg%n"
  }

  filter(ThresholdFilter) {
    level = DEBUG
  }
}

// DEBUGログの出力設定
appender("DEBUG_FILE", RollingFileAppender) {

  file = "${FILE_PATH}/dashboard-debug.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "dashboard-debug_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    charset = Charset.forName("${ENCODE}")
    pattern = "%date{yyyy/MM/dd HH:mm:ss} [%thread] %X{id} %-5level %logger{10} - %msg%n"
  }

  filter(LevelFilter) {
    level = DEBUG
    onMatch = ACCEPT
    onMismatch = DENY
  }

}

// INFOログの出力設定
appender("INFO_FILE", RollingFileAppender) {

  file = "${FILE_PATH}/dashboard-info.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "dashboard-info_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    charset = Charset.forName("${ENCODE}")
    pattern = "%date{yyyy/MM/dd HH:mm:ss} [%thread] %X{id} %-5level %logger{10} - %msg%n"
  }

  filter(LevelFilter) {
    level = INFO
    onMatch = ACCEPT
    onMismatch = DENY
  }

}

// WARNログの出力する設定
appender("WARN_FILE", RollingFileAppender) {

  file = "${FILE_PATH}/dashboard-warn.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "dashboard-warn_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    charset = Charset.forName("${ENCODE}")
    pattern = "%date{yyyy/MM/dd HH:mm:ss} [%thread] %X{id} %-5level %logger{10} - %msg%n"
  }

  filter(LevelFilter) {
    level = WARN
    onMatch = ACCEPT
    onMismatch = DENY
  }
}

// ERRORログの出力する設定
appender("ERROR_FILE", RollingFileAppender) {

  file = "${FILE_PATH}/dashboard-error.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "dashboard-error_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    charset = Charset.forName("${ENCODE}")
    pattern = "%date{yyyy/MM/dd HH:mm:ss} [%thread] %X{id} %-5level %logger{10} - %msg%n"
  }

  filter(LevelFilter) {
    level = ERROR
    onMatch = ACCEPT
    onMismatch = DENY
  }
}


// アプリで出力されるログのログレベルを設定
logger("jp.co.ha", DEBUG, appenderList, false)
logger("org", INFO, appenderList, false)

root(INFO, appenderList)

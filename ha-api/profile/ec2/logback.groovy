import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.*
import static ch.qos.logback.core.spi.FilterReply.*

scan("30 seconds")

def FILE_PATH = "/var/log/app";
def ENCODE = "UTF-8";
def appenderList = ["DEBUG_FILE", "INFO_FILE", "WARN_FILE", "ERROR_FILE"];

// DEBUGログの出力設定
appender("DEBUG_FILE", RollingFileAppender) {

  file = "${FILE_PATH}/api-debug.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "api-debug_%d{yyyy-MM-dd}.log"
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

  file = "${FILE_PATH}/api-info.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "api-info_%d{yyyy-MM-dd}.log"
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

  file = "${FILE_PATH}/api-warn.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "api-warn_%d{yyyy-MM-dd}.log"
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

  file = "${FILE_PATH}/api-error.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "api-error_%d{yyyy-MM-dd}.log"
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

root(INFO, appenderList)

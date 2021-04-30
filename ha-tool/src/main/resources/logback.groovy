import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.*

scan("30 seconds")

def FILE_PATH = "C:/app/logs/";
def ENCODE = "UTF-8";

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

appender("FILE", RollingFileAppender) {

  file = "${FILE_PATH}/tool.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "tool_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    charset = Charset.forName("${ENCODE}")
    pattern = "%date{yyyy/MM/dd HH:mm:ss} [%thread] %X{id} %-5level %logger{10} - %msg%n"
  }

  filter(ThresholdFilter) {
    level = DEBUG
  }

}

// アプリで出力されるログのログレベルを設定
logger("jp.co.ha", DEBUG, appenderList, false)

root(INFO, appenderList)

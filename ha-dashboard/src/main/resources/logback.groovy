import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.*

scan("30 seconds")

def FILE_PATH = "D:/app/logs";
def ENCODE = "UTF-8";

appender("STDOUT", ConsoleAppender) {

  target = "System.out"

  encoder(PatternLayoutEncoder) {
    charset = Charset.forName("${ENCODE}")
    pattern = "%d [%thread] %-5level %logger{10} - %msg%n"
  }

  filter(ThresholdFilter) {
    level = DEBUG
  }
}

appender("FILE", RollingFileAppender) {

  file = "${FILE_PATH}/dashboard.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "dashboard_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    charset = Charset.forName("${ENCODE}")
    pattern = "%d [%thread] %-5level %logger{10} - %message%n"
  }

  filter(ThresholdFilter) {
    level = DEBUG
  }

}

// Mybatisで発行されるSQLのログ設定
logger("jp.co.ha.db.mapper", DEBUG, ["STDOUT", "FILE"], false)

root(INFO, ["STDOUT", "FILE"])

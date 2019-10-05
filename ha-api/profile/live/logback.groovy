import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.*

scan("30 seconds")

def FILE_PATH = "D:/app/logs/";
def ENCODE = "UTF-8";
context.name = "api"

appender("FILE", RollingFileAppender) {

  file = "${FILE_PATH}/api.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "api_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    charset = Charset.forName("${ENCODE}")
    pattern = "%d [%thread] %X{id} %-5level %logger{10} - %msg%n"
  }

  filter(ThresholdFilter) {
    level = INFO
  }

}

// Mybatisで発行されるSQLのログ設定
logger("jp.co.ha.db.mapper", INFO, ["STDOUT", "FILE"], false)

root(INFO, ["STDOUT", "FILE"])
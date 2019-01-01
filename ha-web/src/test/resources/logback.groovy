import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import static ch.qos.logback.classic.Level.*

scan("30 seconds")

appender("STDOUT", ConsoleAppender) {

  target = "System.out"

  encoder(PatternLayoutEncoder) {
    pattern = "%d [%thread] %-5level %logger{10} - %msg%n"
  }

  filter(ThresholdFilter) {
    level = DEBUG
  }
}

appender("FILE", RollingFileAppender) {

  file = "C:/logs/test/main.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "main_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    pattern = "%d [%thread] %-5level %logger{10} - %message%n"
  }

  filter(ThresholdFilter) {
    level = DEBUG
  }

}

logger("jp.co.ha.db.mapper", DEBUG, ["STDOUT", "FILE"], false)

root(INFO, ["STDOUT", "FILE"])
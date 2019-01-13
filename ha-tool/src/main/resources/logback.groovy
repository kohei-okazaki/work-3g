import ch.qos.logback.classic.encoder.PatternLayoutEncoder
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

}

appender("FILE", RollingFileAppender) {

  file = "C:/logs/tool/tool.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "main_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    pattern = "%d [%thread] %-5level %logger{10} - %message%n"
  }

}

root(INFO, ["STDOUT", "FILE"])
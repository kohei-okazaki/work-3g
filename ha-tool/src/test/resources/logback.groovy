import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import static ch.qos.logback.classic.Level.*

appender("STDOUT", ConsoleAppender) {

  encoder(PatternLayoutEncoder) {
    pattern = "%d [%thread] %-5level %logger{10} - %msg%n"
  }

}

appender("FILE", RollingFileAppender) {

  file = "logs/main.log"

  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "main_%d{yyyy-MM-dd}.log"
    maxHistory = 30
  }

  encoder(PatternLayoutEncoder) {
    pattern = "%d [%thread] %-5level %logger{10} - %message%n"
  }

}
root(INFO, ["STDOUT", "FILE"])
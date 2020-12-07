@echo off

rem ----------------------------------------------------------------------------------------
rem 共通処理バッチ
rem クラスパスの設定と文字コードをUTF-8に変更
rem ----------------------------------------------------------------------------------------

cls

rem 基底ディレクトリ
set BASE_DIR=C:\app\git\work-3g
set REPO=C:\Users\hoge\.m2\repository

rem クラスパス
set CLASSPATH=%REPO%\joda-time\joda-time\2.8.1\joda-time-2.8.1.jar;%REPO%\org\apache\httpcomponents\httpclient\4.5.9\httpclient-4.5.9.jar;%REPO%\org\apache\httpcomponents\httpcore\4.4.11\httpcore-4.4.11.jar;%REPO%\com\amazonaws\aws-java-sdk-ses\1.11.831\aws-java-sdk-ses-1.11.831.jar;%REPO%\com\amazonaws\aws-java-sdk-s3\1.11.831\aws-java-sdk-s3-1.11.831.jar;%REPO%\com\amazonaws\aws-java-sdk-core\1.11.831\aws-java-sdk-core-1.11.831.jar;%REPO%\com\amazonaws\aws-java-sdk\1.11.831\aws-java-sdk-1.11.831.jar;%REPO%\com\fasterxml\jackson\core\jackson-databind\2.12.0\jackson-databind-2.12.0.jar;%REPO%\com\fasterxml\jackson\core\jackson-core\2.12.0\jackson-core-2.12.0.jar;%REPO%\com\fasterxml\jackson\core\jackson-annotations\2.12.0\jackson-annotations-2.12.0.jar;%REPO%\com\fasterxml\jackson\dataformat\jackson-dataformat-csv\2.12.0\jackson-dataformat-csv-2.12.0.jar;%REPO%\com\fasterxml\jackson\dataformat\jackson-dataformat-xml\2.12.0\jackson-dataformat-xml-2.12.0.jar;%REPO%\com\fasterxml\jackson\module\jackson-module-jaxb-annotations\2.12.0\jackson-module-jaxb-annotations-2.12.0.jar;%REPO%\jakarta\xml\bind\jakarta.xml.bind-api\2.3.2\jakarta.xml.bind-api-2.3.2.jar;%REPO%\jakarta\activation\jakarta.activation-api\1.2.1\jakarta.activation-api-1.2.1.jar;%REPO%\org\codehaus\woodstox\stax2-api\4.2\stax2-api-4.2.jar;%REPO%\com\fasterxml\woodstox\woodstox-core\6.0.1\woodstox-core-6.0.1.jar;%REPO%\mysql\mysql-connector-java\8.0.22\mysql-connector-java-8.0.22.jar;%REPO%\com\google\protobuf\protobuf-java\3.6.1\protobuf-java-3.6.1.jar;%REPO%\org\mybatis\mybatis\3.5.6\mybatis-3.5.6.jar;%REPO%\org\mybatis\mybatis-spring\2.0.6\mybatis-spring-2.0.6.jar;%REPO%\org\apache\commons\commons-dbcp2\2.8.0\commons-dbcp2-2.8.0.jar;%REPO%\org\apache\commons\commons-pool2\2.8.1\commons-pool2-2.8.1.jar;%REPO%\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;%REPO%\commons-cli\commons-cli\1.4\commons-cli-1.4.jar;%REPO%\org\aspectj\aspectjrt\1.9.6\aspectjrt-1.9.6.jar;%REPO%\org\aspectj\aspectjweaver\1.9.6\aspectjweaver-1.9.6.jar;%REPO%\org\slf4j\slf4j-api\1.7.30\slf4j-api-1.7.30.jar;%REPO%\org\slf4j\jcl-over-slf4j\1.7.30\jcl-over-slf4j-1.7.30.jar;%REPO%\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;%REPO%\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;%REPO%\org\codehaus\groovy\groovy\3.0.7\groovy-3.0.7.jar;%REPO%\org\thymeleaf\thymeleaf-spring5\3.0.11.RELEASE\thymeleaf-spring5-3.0.11.RELEASE.jar;%REPO%\org\thymeleaf\thymeleaf\3.0.11.RELEASE\thymeleaf-3.0.11.RELEASE.jar;%REPO%\ognl\ognl\3.1.12\ognl-3.1.12.jar;%REPO%\org\javassist\javassist\3.20.0-GA\javassist-3.20.0-GA.jar;%REPO%\org\attoparser\attoparser\2.0.6.RELEASE\attoparser-2.0.6.RELEASE.jar;%REPO%\org\unbescape\unbescape\1.1.6.RELEASE\unbescape-1.1.6.RELEASE.jar;%REPO%\nz\net\ultraq\thymeleaf\thymeleaf-layout-dialect\2.4.1\thymeleaf-layout-dialect-2.4.1.jar;%REPO%\nz\net\ultraq\thymeleaf\thymeleaf-expression-processor\1.1.3\thymeleaf-expression-processor-1.1.3.jar;%REPO%\javax\inject\javax.inject\1\javax.inject-1.jar;%REPO%\javax\validation\validation-api\2.0.1.Final\validation-api-2.0.1.Final.jar;%REPO%\org\hibernate\validator\hibernate-validator\6.1.6.Final\hibernate-validator-6.1.6.Final.jar;%REPO%\org\jboss\logging\jboss-logging\3.3.2.Final\jboss-logging-3.3.2.Final.jar;%REPO%\com\fasterxml\classmate\1.3.4\classmate-1.3.4.jar;%REPO%\javax\servlet\javax.servlet-api\4.0.1\javax.servlet-api-4.0.1.jar;%REPO%\org\apache\commons\commons-lang3\3.9\commons-lang3-3.9.jar;%REPO%\javax\mail\javax.mail-api\1.6.2\javax.mail-api-1.6.2.jar;%REPO%\org\apiguardian\apiguardian-api\1.1.0\apiguardian-api-1.1.0.jar;%REPO%\net\bytebuddy\byte-buddy\1.8.15\byte-buddy-1.8.15.jar;%REPO%\org\apache\commons\commons-exec\1.3\commons-exec-1.3.jar;%REPO%\com\google\guava\guava\25.0-jre\guava-25.0-jre.jar;%REPO%\com\google\code\findbugs\jsr305\1.3.9\jsr305-1.3.9.jar;%REPO%\org\checkerframework\checker-compat-qual\2.0.0\checker-compat-qual-2.0.0.jar;%REPO%\com\google\errorprone\error_prone_annotations\2.1.3\error_prone_annotations-2.1.3.jar;%REPO%\com\google\j2objc\j2objc-annotations\1.1\j2objc-annotations-1.1.jar;%REPO%\org\codehaus\mojo\animal-sniffer-annotations\1.14\animal-sniffer-annotations-1.14.jar;%REPO%\com\squareup\okhttp3\okhttp\3.11.0\okhttp-3.11.0.jar;%REPO%\com\squareup\okio\okio\1.14.0\okio-1.14.0.jar;%REPO%\org\springframework\spring-context\5.3.1\spring-context-5.3.1.jar;%REPO%\org\springframework\spring-expression\5.3.1\spring-expression-5.3.1.jar;%REPO%\org\springframework\spring-context-support\5.3.1\spring-context-support-5.3.1.jar;%REPO%\org\springframework\spring-core\5.3.1\spring-core-5.3.1.jar;%REPO%\org\springframework\spring-jcl\5.3.1\spring-jcl-5.3.1.jar;%REPO%\org\springframework\spring-web\5.3.1\spring-web-5.3.1.jar;%REPO%\org\springframework\spring-webmvc\5.3.1\spring-webmvc-5.3.1.jar;%REPO%\org\springframework\spring-beans\5.3.1\spring-beans-5.3.1.jar;%REPO%\org\springframework\spring-aop\5.3.1\spring-aop-5.3.1.jar;%REPO%\org\springframework\spring-jdbc\5.3.1\spring-jdbc-5.3.1.jar;%REPO%\org\springframework\spring-tx\5.3.1\spring-tx-5.3.1.jar;%REPO%\org\springframework\data\spring-data-commons\2.4.1\spring-data-commons-2.4.1.jar;%REPO%\jp\co\ha\common\1.0.0\common-1.0.0.jar;%REPO%\jp\co\ha\db\1.0.0\db-1.0.0.jar;%REPO%\jp\co\ha\web\1.0.0\web-1.0.0.jar;%REPO%\jp\co\ha\business\1.0.0\business-1.0.0.jar;

rem バッチ起動クラス
set MAIN=jp.co.ha.batch.invoke.BatchEntry

rem Java
set JAVA=C:\app\pleiades\java\8\bin\java

rem 文字コードをUTF-8に変更する
chcp 65001

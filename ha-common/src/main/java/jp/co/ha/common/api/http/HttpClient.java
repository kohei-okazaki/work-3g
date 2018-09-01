package jp.co.ha.common.api.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.BeanUtil;

/**
 * HTTPクライアント
 *
 */
public class HttpClient {

	/** ロガー */
	private static final Logger LOG = LoggerFactory.getAppLogger(HttpClient.class);

	/** HTTPクライアント 設定情報 */
	private HttpClientConfig conf;

	/** HTTPステータス */
	private HttpStatus httpStatus;
	/** レスポンス情報 */
	private String responseBody;

	/**
	 * コンストラクタ<br>
	 *
	 * @param conf
	 *     HTTPクライアントの設定情報保持クラス
	 */
	public HttpClient(HttpClientConfig conf) {
		this.conf = conf;
	}

	/**
	 * Httpステータスを返す<br>
	 *
	 * @return httpStatus
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * responseBodyを返す<br>
	 *
	 * @return responseBody
	 */
	public String getResponseBody() {
		return responseBody;
	}

	/**
	 * リクエスト情報を送信する
	 */
	public void send() {
		HttpURLConnection connection = null;
		BufferedReader br = null;

		try {
			URL url = new URL(this.conf.getRequestUrl());
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(this.conf.getTimeout());
			connection.setRequestMethod(this.conf.getHttpMethod().name());

			connection.connect();
			this.httpStatus = HttpStatus.of(connection.getResponseCode());

			if (HttpStatus.OK == this.httpStatus) {
				String encoding = connection.getContentEncoding();
				if (BeanUtil.isNull(encoding)) {
					encoding = Charset.UTF_8.getName();
				}
				br = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));

				String line = null;
				StringBuffer result = new StringBuffer();
				while (BeanUtil.notNull(line = br.readLine())) {
					result.append(line);
				}
				this.responseBody = result.toString();
			} else {
				LOG.warn("HTTP ステータス = " + this.httpStatus);
			}
		} catch (MalformedURLException e) {
			LOG.error("", e);
		} catch (IOException e) {
			LOG.error("", e);
		} finally {
			try {
				if (BeanUtil.notNull(br)) {
					br.close();
				}
			} catch (IOException e) {
				LOG.error("", e);
			}
			if (BeanUtil.notNull(connection)) {
				connection.disconnect();
			}
		}
	}
}

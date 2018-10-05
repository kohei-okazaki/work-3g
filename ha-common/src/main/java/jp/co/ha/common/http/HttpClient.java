package jp.co.ha.common.http;

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
	private static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);

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
		try {
			URL url = new URL(this.conf.getRequestUrl());
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(this.conf.getTimeout());
			connection.setRequestMethod(this.conf.getHttpMethod().getValue());

			connection.connect();
			this.httpStatus = HttpStatus.of(connection.getResponseCode());
		} catch (MalformedURLException e) {
			LOG.error("", e);
		} catch (IOException e) {
			LOG.error("", e);
		}

		String encoding = getEncoding(connection.getContentEncoding());

		try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding))) {
			if (HttpStatus.OK == this.httpStatus) {
				String line = null;
				StringBuffer result = new StringBuffer();
				while (BeanUtil.notNull(line = br.readLine())) {
					result.append(line);
				}
				this.responseBody = result.toString();
			} else {
				LOG.warn("HTTP ステータス = " + this.httpStatus + "(" + this.httpStatus.getValue() + ")");
			}
		} catch (IOException e) {
			LOG.error("", e);
		} finally {
			if (BeanUtil.notNull(connection)) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 文字Encodeを返す<br>
	 *
	 * @param encoding
	 *     Encode
	 * @return
	 */
	private String getEncoding(String encoding) {
		if (BeanUtil.isNull(encoding)) {
			encoding = Charset.UTF_8.getName();
		}
		return encoding;
	}
}

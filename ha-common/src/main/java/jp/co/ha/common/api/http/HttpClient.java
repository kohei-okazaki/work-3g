package jp.co.ha.common.api.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * HTTPクライアント
 *
 */
public class HttpClient {

	private static final Logger LOG = LoggerFactory.getAppLogger(HttpClient.class);

	/** リクエストURL */
	private String requestUrl;
	/** タイムアウトまでの時間 */
	private int timeout;
	/** HTTPメソッド */
	private HttpMethod httpMethod;

	/** HTTPステータス */
	private HttpStatus httpStatus;
	/** レスポンス情報 */
	private String responseBody;

	/**
	 * コンストラクタ<br>
	 *
	 * @param requestUrl
	 *     リクエストURL
	 */
	public HttpClient(String requestUrl) {
		this.requestUrl = requestUrl;
		this.timeout = 30000;
	}

	/**
	 * requestUrlを設定する<br>
	 *
	 * @param requestUrl
	 *     リクエストURL
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * timeoutを設定する<br>
	 *
	 * @param timeout
	 *     タイムアウトまでの時間
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * HTTPメソッドを設定する<br>
	 *
	 * @param httpMethod
	 *     HttpMethod
	 */
	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * リクエスト情報を送信する
	 */
	public void send() {
		HttpURLConnection connection;
		try {
			URL url = new URL(this.requestUrl);
			connection = (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

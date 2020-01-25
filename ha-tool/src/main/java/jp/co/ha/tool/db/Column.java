package jp.co.ha.tool.db;

/**
 * カラム情報保持クラス
 *
 * @since 1.0
 */
public class Column {

	/** カラム名 */
	private String name;
	/** コメント */
	private String comment;
	/** カラム定義 */
	private String type;

	/**
	 * nameを返す
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * nameを設定する
	 *
	 * @param name
	 *     カラム名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * commentを返す
	 *
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * commentを設定する
	 *
	 * @param comment
	 *     コメント
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * typeを返す
	 *
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * typeを設定する
	 *
	 * @param type
	 *     カラム定義
	 */
	public void setType(String type) {
		this.type = type;
	}

}

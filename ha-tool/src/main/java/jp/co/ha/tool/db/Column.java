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
	/** プライマリー */
	private boolean isPrimary;
	/** シーケンス */
	private boolean isSequence;
	/** 暗号化 */
	private boolean isCrypt;
	/** NotNull制約 */
	private boolean isNotNull;

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

	/**
	 * isPrimaryを返す
	 *
	 * @return isPrimary
	 */
	public boolean isPrimary() {
		return isPrimary;
	}

	/**
	 * isPrimaryを設定する
	 *
	 * @param isPrimary
	 */
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	/**
	 * isSequenceを返す
	 *
	 * @return isSequence
	 */
	public boolean isSequence() {
		return isSequence;
	}

	/**
	 * isSequenceを設定する
	 *
	 * @param isSequence
	 */
	public void setSequence(boolean isSequence) {
		this.isSequence = isSequence;
	}

	/**
	 * isCryptを返す
	 *
	 * @return isCrypt
	 */
	public boolean isCrypt() {
		return isCrypt;
	}

	/**
	 * isCryptを設定する
	 *
	 * @param isCrypt
	 */
	public void setCrypt(boolean isCrypt) {
		this.isCrypt = isCrypt;
	}

	/**
	 * isNotNullを返す
	 *
	 * @return isNotNull
	 */
	public boolean isNotNull() {
		return isNotNull;
	}

	/**
	 * isNotNullを設定する
	 *
	 * @param isNotNull
	 */
	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

}

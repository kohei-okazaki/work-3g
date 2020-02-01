package jp.co.ha.business.dto;

import java.util.List;

/**
 * お知らせ情報一覧リストDto
 *
 * @since 1.0
 */
public class NewsListDto {

	/** お知らせ情報一覧リスト */
	private List<NewsDto> newsDtoList;

	/**
	 * newsDtoListを返す
	 *
	 * @return newsDtoList
	 */
	public List<NewsDto> getNewsDtoList() {
		return newsDtoList;
	}

	/**
	 * newsDtoListを設定する
	 *
	 * @param newsDtoList
	 *     お知らせ情報一覧リスト
	 */
	public void setNewsDtoList(List<NewsDto> newsDtoList) {
		this.newsDtoList = newsDtoList;
	}

	/**
	 * お知らせ情報Dto
	 *
	 * @since 1.0
	 */
	public static class NewsDto {

		/** 順序 */
		private Integer index;
		/** タイトル */
		private String title;
		/** 日付 */
		private String date;
		/** 詳細 */
		private String detail;

		/**
		 * indexを返す
		 *
		 * @return index
		 */
		public Integer getIndex() {
			return index;
		}

		/**
		 * indexを設定する
		 *
		 * @param index
		 *     順序
		 */
		public void setIndex(Integer index) {
			this.index = index;
		}

		/**
		 * titleを返す
		 *
		 * @return title
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * titleを設定する
		 *
		 * @param title
		 *     タイトル
		 */
		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * dateを返す
		 *
		 * @return date
		 */
		public String getDate() {
			return date;
		}

		/**
		 * dateを設定する
		 *
		 * @param date
		 *     日付
		 */
		public void setDate(String date) {
			this.date = date;
		}

		/**
		 * detailを返す
		 *
		 * @return detail
		 */
		public String getDetail() {
			return detail;
		}

		/**
		 * detailを設定する
		 *
		 * @param detail
		 *     詳細
		 */
		public void setDetail(String detail) {
			this.detail = detail;
		}

	}
}

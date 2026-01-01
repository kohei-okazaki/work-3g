package jp.co.ha.root.contents.appuser.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import jp.co.ha.common.util.PagingView;
import jp.co.ha.root.contents.appuser.response.AppUserListApiResponse.AccountResponse;

/**
 * ユーザ情報検索サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface AppUserService {

    /**
     * ユーザ情報リストを検索する
     * 
     * @param pageable
     *     Pageable
     * @return ユーザ情報リスト
     */
    List<AccountResponse> getUserList(Pageable pageable);

    /**
     * PagingViewを返す
     * 
     * @param pageable
     *     Pageable
     * @return PagingView
     */
    PagingView getPagingView(Pageable pageable);
}

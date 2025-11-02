package jp.co.ha.db.mapper.composite;

import java.util.List;

import jp.co.ha.db.entity.composite.CompositeRootUserInfo;

/**
 * 以下のテーブルの複合EntityのMapper
 * <ul>
 * <li>root_login_info</li>
 * <li>root_user_role_mng_mt</li>
 * <li>root_user_role_detail_mt</li>
 * <li>root_role_mt</li>
 * </ul>
 *
 * @version 1.0.0
 */
public interface CompositeRootUserInfoMapper {

    /**
     * ログインIDに紐づく管理者サイト複合ユーザ情報の一覧を検索する
     *
     * @param seqRootLoginInfoId
     *     ログインID
     * @return 管理者サイト複合ユーザ情報
     */
    List<CompositeRootUserInfo> selectBySeqRootLoginInfoId(Long seqRootLoginInfoId);
}

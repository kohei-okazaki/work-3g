package jp.co.ha.business.db.crud.read.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.AccountRecoveryTokenSearchService;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.AccountRecoveryTokenData;
import jp.co.ha.db.entity.AccountRecoveryTokenDataExample;
import jp.co.ha.db.entity.AccountRecoveryTokenDataExample.Criteria;
import jp.co.ha.db.mapper.AccountRecoveryTokenDataMapper;

/**
 * アカウント回復トークン検索サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class AccountRecoveryTokenSearchServiceImpl
        implements AccountRecoveryTokenSearchService {

    /** AccountRecoveryTokenDataMapper */
    @Autowired
    private AccountRecoveryTokenDataMapper mapper;

    @Override
    public Optional<AccountRecoveryTokenData> findBySeqUserIdAndTokenAndValidTokenCreateDate(
            Long seqUserId, String token) {

        AccountRecoveryTokenDataExample example = new AccountRecoveryTokenDataExample();
        Criteria creteria = example.createCriteria();

        // ユーザID
        creteria.andSeqUserIdEqualTo(seqUserId);
        // トークン
        creteria.andTokenEqualTo(token);
        // トークン作成日時
        creteria.andTokenCreateDateGreaterThan(getTargetTokenDate());

        List<AccountRecoveryTokenData> list = mapper.selectByExample(example);

        // 同一トークンは複数レコード存在しない想定のため、先頭1件を使用
        return Optional.ofNullable(CollectionUtil.isEmpty(list) ? null : list.get(0));
    }

    /**
     * 検索対象(システム日時 - 1時間)のトークン作成日時を取得
     *
     * @return 検索対象のトークン作成日時
     */
    private static LocalDateTime getTargetTokenDate() {
        LocalDateTime sysdate = DateTimeUtil.getSysDate();
        sysdate = sysdate.minusHours(1);
        return sysdate;
    }

}

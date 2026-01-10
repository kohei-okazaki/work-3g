package jp.co.ha.root.contents.appuser.service;

import static jp.co.ha.common.db.SelectOption.SortType.*;
import static jp.co.ha.common.type.CommonFlag.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.root.contents.appuser.response.AppUserListApiResponse;
import jp.co.ha.root.contents.appuser.response.AppUserListApiResponse.AccountResponse;

/**
 * ユーザ情報検索サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    /** ユーザ検索サービス */
    @Autowired
    private UserSearchService service;

    @Override
    public List<AccountResponse> getUserList(Pageable pageable) {

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_USER_ID", DESC)
                .pageable(pageable)
                .build();

        List<AppUserListApiResponse.AccountResponse> list = service
                .findAll(selectOption).stream()
                .map(e -> {
                    AppUserListApiResponse.AccountResponse response = new AppUserListApiResponse.AccountResponse();
                    BeanUtil.copy(e, response);
                    response.setDeleteFlag(
                            e.isDeleteFlag() ? TRUE.getValue() : FALSE.getValue());
                    response.setHeaderFlag(
                            e.isHeaderFlag() ? TRUE.getValue() : FALSE.getValue());
                    response.setFooterFlag(
                            e.isFooterFlag() ? TRUE.getValue() : FALSE.getValue());
                    response.setMaskFlag(
                            e.isMaskFlag() ? TRUE.getValue() : FALSE.getValue());
                    response.setEnclosureCharFlag(
                            e.isEnclosureCharFlag() ? TRUE.getValue() : FALSE.getValue());
                    return response;
                }).collect(Collectors.toList());

        return list;
    }

    @Override
    public PagingView getPagingView(Pageable pageable) {
        return PagingViewFactory.getPageView(pageable, "account?page",
                service.countBySeqUserId(null));
    }

}

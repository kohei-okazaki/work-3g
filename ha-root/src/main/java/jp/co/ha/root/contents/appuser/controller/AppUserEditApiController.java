package jp.co.ha.root.contents.appuser.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.component.UserComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.User;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.appuser.request.AppUserEditApiRequest;
import jp.co.ha.root.contents.appuser.response.AppUserEditApiResponse;

/**
 * ユーザ情報編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class AppUserEditApiController
        extends BaseRootApiController<AppUserEditApiRequest, AppUserEditApiResponse> {

    /** ユーザComponent */
    @Autowired
    private UserComponent userComponent;

    /**
     * ユーザ情報編集処理
     *
     * @param seqUserId
     *     ユーザID
     * @param request
     *     ユーザ情報編集APIリクエスト
     * @return ユーザ情報編集APIレスポンス
     * @throws BaseException
     *     ユーザ情報の編集に失敗した場合
     */
    @PutMapping(value = "account/{seq_user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUserEditApiResponse> edit(
            @PathVariable(name = "seq_user_id", required = true) Long seqUserId,
            @Valid @RequestBody AppUserEditApiRequest request) throws BaseException {

        if (!userComponent.findById(seqUserId).isPresent()) {
            // 指定したユーザIDのuserが存在しない場合
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("user is not found"));
        }

        User entity = new User();
        BeanUtil.copy(request, entity);
        entity.setSeqUserId(seqUserId);
        userComponent.updateUserById(entity);

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected AppUserEditApiResponse getResponse() {
        return new AppUserEditApiResponse();
    }

}

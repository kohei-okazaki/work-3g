package jp.co.ha.root.contents.tools.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.read.RootRoleMtSearchService;
import jp.co.ha.db.entity.RootRoleMt;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.tools.request.RoleMtListApiRequest;
import jp.co.ha.root.contents.tools.response.RoleMtListApiResponse;
import jp.co.ha.root.contents.tools.response.RoleMtListApiResponse.Role;

/**
 * 権限マスタリスト取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class RoleMtListApiController
        extends BaseRootApiController<RoleMtListApiRequest, RoleMtListApiResponse> {

    /** 管理者サイト権限マスタ検索サービス */
    @Autowired
    private RootRoleMtSearchService rootRoleMtSearchService;

    /**
     * 権限マスタリスト取得
     *
     * @param request
     *     権限マスタリスト取得APIリクエスト
     * @return 権限マスタリスト取得APIレスポンス
     */
    @GetMapping(value = "roles", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<RoleMtListApiResponse> get(RoleMtListApiRequest request) {

        List<RootRoleMt> roleList = rootRoleMtSearchService.findAll();
        RoleMtListApiResponse response = getSuccessResponse();
        response.setRoles(roleList.stream().map(e -> {
            Role role = new Role();
            role.setValue(e.getRole());
            role.setLabel(e.getRoleName());
            return role;
        }).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }

    @Override
    protected RoleMtListApiResponse getResponse() {
        return new RoleMtListApiResponse();
    }
}

package jp.co.ha.dashboard.healthinfo.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ha.business.dto.HealthInfoRefDetailDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoRefDetailService;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.web.controller.BaseWebController;

/**
 * 健康管理_健康情報照会詳細画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("healthinforeference")
public class HeathInfoRefDetailController implements BaseWebController {

    /** セッション管理サービス */
    @Autowired
    private SessionManageService sessionService;
    /** 健康情報詳細サービス */
    @Autowired
    private HealthInfoRefDetailService healthInfoRefDetailService;

    /**
     * 詳細画面
     *
     * @param model
     *     Model
     * @param seqHealthInfoId
     *     健康情報ID
     * @param request
     *     HttpServletRequest
     * @return 照会前画面
     * @throws BaseException
     *     基底例外
     */
    @GetMapping(value = "/detail")
    public String detail(Model model,
            @RequestParam(name = "seqHealthInfoId", required = false) Optional<Integer> seqHealthInfoId,
            HttpServletRequest request) throws BaseException {

        // 健康情報ID
        Integer seqHealthInfoIdVal = seqHealthInfoId
                .orElseThrow(() -> new SystemException(CommonErrorCode.DB_NO_DATA,
                        "リクエスト情報が不正です. 健康情報ID=" + seqHealthInfoId));
        // ユーザID
        String userId = sessionService
                .getValue(request.getSession(), "userId", String.class).get();

        HealthInfoRefDetailDto dto = new HealthInfoRefDetailDto();
        dto.setSeqHealthInfoId(seqHealthInfoIdVal);
        dto.setUserId(userId);

        // 詳細情報
        HealthInfoRefDetailDto detail = healthInfoRefDetailService
                .getHealthInfoRefDetailDto(dto);
        if (detail == null) {
            model.addAttribute("errorMessage", "健康情報IDの指定が不正なため、健康情報がありません。");
        } else {
            model.addAttribute("detail", detail);
        }
        return getView(DashboardView.HEALTH_INFO_REF_DETAIL);
    }

}

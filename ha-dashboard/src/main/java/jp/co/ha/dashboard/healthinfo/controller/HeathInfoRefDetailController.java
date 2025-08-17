package jp.co.ha.dashboard.healthinfo.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.ha.business.dto.HealthInfoRefDetailDto;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoRefDetailService;
import jp.co.ha.dashboard.view.DashboardView;

/**
 * 健康管理_健康情報照会詳細画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("healthinforeference")
public class HeathInfoRefDetailController implements BaseWebController {

    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** 健康情報詳細サービス */
    @Autowired
    private HealthInfoRefDetailService detailService;

    /**
     * 詳細画面
     *
     * @param model
     *     {@linkplain Model}
     * @param seqHealthInfoId
     *     健康情報ID
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return 照会前画面
     * @throws BaseException
     *     基底例外
     */
    @GetMapping("/detail")
    public String detail(Model model,
            @RequestParam(name = "seqHealthInfoId", required = false) Optional<String> seqHealthInfoId,
            HttpServletRequest request) throws BaseException {

        // 健康情報ID
        String healthInfoId = seqHealthInfoId.orElseThrow(
                () -> new SystemException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                        "リクエスト情報が不正です. 健康情報ID=" + seqHealthInfoId));
        if (!RegexType.HALF_NUMBER.is(healthInfoId)) {
            // 数値以外の場合
            throw new SystemException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                    "健康情報IDが数字以外です. 健康情報ID=" + healthInfoId);
        }

        // sessionよりユーザID取得
        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        HealthInfoRefDetailDto dto = new HealthInfoRefDetailDto();
        dto.setSeqHealthInfoId(Long.parseLong(healthInfoId));
        dto.setSeqUserId(seqUserId);

        // 詳細情報
        Optional<HealthInfoRefDetailDto> detail = detailService
                .getHealthInfoRefDetailDto(dto);

        if (detail.isPresent()) {
            model.addAttribute("detail", detail.get());
        } else {
            model.addAttribute("errorMessage", "健康情報IDの指定が不正なため、健康情報がありません。");
        }
        return getView(model, DashboardView.HEALTH_INFO_REF_DETAIL);
    }

}

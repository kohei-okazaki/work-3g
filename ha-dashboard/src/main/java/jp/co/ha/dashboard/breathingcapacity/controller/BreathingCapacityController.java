package jp.co.ha.dashboard.breathingcapacity.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.component.BreathingCapacityApiComponent;
import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.business.dto.BreathingCapacityDto;
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.breathingcapacity.form.BreathingCapacityForm;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.User;

/**
 * 健康管理_肺活量計算画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("breathingcapacity")
public class BreathingCapacityController implements BaseWebController {

    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** 肺活量計算APIComponent */
    @Autowired
    private BreathingCapacityApiComponent component;
    /** ユーザ情報検索サービス */
    @Autowired
    private UserSearchService userSearchService;

    /**
     * Formを返す
     * 
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return BreathingCapacityForm
     */
    @ModelAttribute("breathingCapacityForm")
    public BreathingCapacityForm setUpForm(HttpServletRequest request) {
        BreathingCapacityForm form = new BreathingCapacityForm();

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();
        Optional<User> user = userSearchService.findById(seqUserId);
        form.setGender(user.get().getGenderType().toString());
        return form;
    }

    /**
     * 肺活量計算前画面
     *
     * @param model
     *     {@linkplain Model}
     * @return 肺活量計算画面
     */
    @GetMapping("/index")
    public String index(Model model) {
        return getView(model, DashboardView.BREATHING_CAPACITY_CALC);
    }

    /**
     * 肺活量計算処理後画面
     *
     * @param model
     *     {@linkplain Model}
     * @param form
     *     {@linkplain BreathingCapacityForm}
     * @param result
     *     {@linkplain BindingResult}
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return 肺活量計算画面
     * @throws BaseException
     *     基底例外
     */
    @PostMapping("/index")
    public String calc(Model model, @Valid BreathingCapacityForm form,
            BindingResult result, HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            return getView(model, DashboardView.BREATHING_CAPACITY_CALC);
        }

        // DTOに変換
        BreathingCapacityDto dto = new BreathingCapacityDto();
        BeanUtil.copy(form, dto, (src, dest) -> {
            BreathingCapacityForm srcForm = (BreathingCapacityForm) src;
            BreathingCapacityDto destDto = (BreathingCapacityDto) dest;
            destDto.setGenderType(GenderType.of(srcForm.getGender()));
        });

        BreathingCapacityDto calcResult = component.calc(dto, sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get());
        model.addAttribute("calcResult", calcResult);

        return getView(model, DashboardView.BREATHING_CAPACITY_CALC);
    }

}

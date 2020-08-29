package jp.co.ha.dashboard.breathingcapacity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.component.BreathingCapacityComponent;
import jp.co.ha.business.dto.BreathingCapacityDto;
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.breathingcapacity.form.BreathingCapacityForm;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.web.controller.BaseWebController;

/**
 * 健康管理_肺活量計算画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("breathingcapacity")
public class BreathingCapacityController implements BaseWebController {

    /** SessionComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** 肺活量計算Component */
    @Autowired
    private BreathingCapacityComponent component;

    /**
     * Formを返す
     *
     * @return BreathingCapacityForm
     */
    @ModelAttribute("breathingCapacityForm")
    public BreathingCapacityForm setUpForm() {
        BreathingCapacityForm form = new BreathingCapacityForm();
        form.setGender(GenderType.MALE.getValue());
        return form;
    }

    /**
     * 肺活量計算前画面
     *
     * @return 肺活量計算画面
     */
    @GetMapping("/index")
    public String index() {
        return getView(DashboardView.BREATHING_CAPACITY_CALC);
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
            return getView(DashboardView.BREATHING_CAPACITY_CALC);
        }

        // DTOに変換
        BreathingCapacityDto dto = new BreathingCapacityDto();
        BeanUtil.copy(form, dto, (src, dest) -> {
            BreathingCapacityForm srcForm = (BreathingCapacityForm) src;
            BreathingCapacityDto destDto = (BreathingCapacityDto) dest;
            destDto.setGenderType(GenderType.of(srcForm.getGender()));
        });

        BreathingCapacityDto calcResult = component.calc(dto, sessionComponent
                .getValue(request.getSession(), "userId", String.class).get());
        model.addAttribute("calcResult", calcResult);

        return getView(DashboardView.BREATHING_CAPACITY_CALC);
    }

}

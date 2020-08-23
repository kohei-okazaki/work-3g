package jp.co.ha.dashboard.breathingcapacity.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.healthInfo.type.GenderType;
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
     * @return 肺活量計算画面
     */
    public String calc(Model model, @Valid BreathingCapacityForm form,
            BindingResult result) {

        if (result.hasErrors()) {
            return getView(DashboardView.BREATHING_CAPACITY_CALC);
        }

        return getView(DashboardView.BREATHING_CAPACITY_CALC);
    }

}

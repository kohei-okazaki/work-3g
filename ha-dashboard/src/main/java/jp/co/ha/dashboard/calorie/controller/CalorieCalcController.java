package jp.co.ha.dashboard.calorie.controller;

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

import jp.co.ha.business.component.CalorieApiComponent;
import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.calorie.form.CalorieCalcForm;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.web.controller.BaseWebController;

/**
 * 健康管理_カロリー計算画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("caloriecalc")
public class CalorieCalcController implements BaseWebController {

    /** SessionComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** カロリー計算Component */
    @Autowired
    private CalorieApiComponent calorieCalcComponent;

    /**
     * Formを返す
     *
     * @return CalorieCalcForm
     */
    @ModelAttribute("calorieCalcForm")
    public CalorieCalcForm setUpForm() {
        CalorieCalcForm form = new CalorieCalcForm();
        form.setGender(GenderType.MALE.getValue());
        return form;
    }

    /**
     * カロリー計算前画面
     *
     * @param model
     *     {@linkplain Model}
     * @return カロリー計算画面
     */
    @GetMapping("/index")
    public String index(Model model) {
        return getView(model, DashboardView.CALORIE_CALC);
    }

    /**
     * カロリー計算後画面
     *
     * @param model
     *     {@linkplain Model}
     * @param form
     *     {@linkplain CalorieCalcForm}
     * @param result
     *     {@linkplain BindingResult}
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return カロリー計算後画面
     * @throws BaseException
     *     基底例外
     */
    @PostMapping("/index")
    public String calc(Model model, @Valid CalorieCalcForm form, BindingResult result,
            HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            return getView(model, DashboardView.CALORIE_CALC);
        }

        // DTOに変換
        CalorieCalcDto dto = new CalorieCalcDto();
        BeanUtil.copy(form, dto, (src, dest) -> {
            CalorieCalcForm srcForm = (CalorieCalcForm) src;
            CalorieCalcDto destDto = (CalorieCalcDto) dest;
            destDto.setGenderType(GenderType.of(srcForm.getGender()));
        });

        CalorieCalcDto calcResult = calorieCalcComponent.calc(dto, sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get());
        model.addAttribute("calcResult", calcResult);

        return getView(model, DashboardView.CALORIE_CALC);
    }
}

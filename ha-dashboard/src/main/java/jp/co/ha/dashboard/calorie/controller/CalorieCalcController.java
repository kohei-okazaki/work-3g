package jp.co.ha.dashboard.calorie.controller;

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

import jp.co.ha.business.component.CalorieApiComponent;
import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.calorie.form.CalorieCalcForm;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.User;

/**
 * 健康管理_カロリー計算画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("caloriecalc")
public class CalorieCalcController implements BaseWebController {

    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** カロリー計算APIComponent */
    @Autowired
    private CalorieApiComponent calorieCalcComponent;
    /** ユーザ関連Component */
    @Autowired
    private UserComponent userComponent;

    /**
     * Formを返す
     * 
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return CalorieCalcForm
     * @throws BusinessException
     *     年齢の算出に失敗したとき
     */
    @ModelAttribute("calorieCalcForm")
    public CalorieCalcForm setUpForm(HttpServletRequest request)
            throws BusinessException {

        CalorieCalcForm form = new CalorieCalcForm();

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();
        Optional<User> user = userComponent.findById(seqUserId);

        form.setAge(userComponent.getAge(user.get().getBirthDate()));
        form.setGender(user.get().getGenderType().toString());

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
        BeanUtil.copy(form, dto);

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        Optional<User> user = userComponent.findById(seqUserId);

        dto.setGenderType(GenderType.of(user.get().getGenderType().intValue()));

        CalorieCalcDto calcResult = calorieCalcComponent.calc(dto, seqUserId);

        model.addAttribute("calcResult", calcResult);

        return getView(model, DashboardView.CALORIE_CALC);
    }
}

package jp.co.ha.dashboard.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.dashboard.form.CalorieCalcForm;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.web.controller.BaseWebController;

/**
 * 健康管理_カロリー計算画面コントローラ
 *
 */
@Controller
@RequestMapping("calorieCalc")
public class CalorieCalcController implements BaseWebController {

	/**
	 * Formを返す
	 *
	 * @return CalorieCalcForm
	 */
	@ModelAttribute("calorieCalcForm")
	public CalorieCalcForm setUpForm() {
		CalorieCalcForm form = new CalorieCalcForm();
		return form;
	}

	/**
	 * カロリー計算前画面
	 *
	 * @return カロリー計算画面
	 */
	@GetMapping(value = "/index")
	public String index() {
		return getView(DashboardView.CALORIE_CALC);
	}

	/**
	 * カロリー計算後画面
	 *
	 * @param model
	 *     Model
	 * @param form
	 *     カロリー計算画面フォーム
	 * @param result
	 *     妥当性チェック結果
	 * @return
	 * @throws BaseException
	 *     基底例外
	 */
	@PostMapping(value = "/calc")
	public String calc(Model model, @Valid CalorieCalcForm form, BindingResult result) throws BaseException {

		if (result.hasErrors()) {
			return getView(DashboardView.CALORIE_CALC);
		}

		return getView(DashboardView.CALORIE_CALC);
	}
}

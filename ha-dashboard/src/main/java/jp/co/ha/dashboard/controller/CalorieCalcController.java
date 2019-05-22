package jp.co.ha.dashboard.controller;

import java.util.function.BiConsumer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.dto.CalorieCalcDto;
import jp.co.ha.business.healthInfo.type.BodyType;
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.form.CalorieCalcForm;
import jp.co.ha.dashboard.service.CalorieCalcService;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.web.controller.BaseWebController;

/**
 * 健康管理_カロリー計算画面コントローラ
 *
 */
@Controller
@RequestMapping("calorieCalc")
public class CalorieCalcController implements BaseWebController {

	/** カロリー計算サービス */
	@Autowired
	private CalorieCalcService calorieCalcService;

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
	 *     Model
	 *
	 * @return カロリー計算画面
	 */
	@GetMapping(value = "/index")
	public String index(Model model) {
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
	@PostMapping(value = "/index")
	public String calc(Model model, @Valid CalorieCalcForm form, BindingResult result) throws BaseException {

		if (result.hasErrors()) {
			return getView(DashboardView.CALORIE_CALC);
		}

		// DTOに変換
		CalorieCalcDto dto = new CalorieCalcDto();
		BeanUtil.copy(form, dto, new BiConsumer<>() {

			@Override
			public void accept(Object src, Object dest) {
				CalorieCalcForm form = (CalorieCalcForm) src;
				CalorieCalcDto dto = (CalorieCalcDto) dest;
				dto.setGenderType(GenderType.of(form.getGender()));
				dto.setBodyType(BodyType.of(form.getBodyType()));
			}
		});

		CalorieCalcDto calcResult = calorieCalcService.calc(dto);
		model.addAttribute("calcResult", calcResult);

		return getView(DashboardView.CALORIE_CALC);
	}
}

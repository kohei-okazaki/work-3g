package jp.co.ha.business.healthInfo.service.impl;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.ha.business.healthInfo.HealthInfoGraphModel;
import jp.co.ha.business.healthInfo.service.HealthInfoGraphService;

/**
 * 健康情報グラフ作成サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoGraphServiceImpl implements HealthInfoGraphService {

    @Override
    public void putGraph(Model model, Supplier<HealthInfoGraphModel> supplier) {

        HealthInfoGraphModel graphModel = supplier.get();

        model.addAttribute("label", graphModel.getHealthInfoRegDateList());
        model.addAttribute("weight", graphModel.getWeightList());
        model.addAttribute("bmi", graphModel.getBmiList());
        model.addAttribute("standardWeight", graphModel.getStandardWeightList());

    }

}

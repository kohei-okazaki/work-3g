package jp.co.ha.business.healthInfo.service;

import java.util.function.Supplier;

import org.springframework.ui.Model;

import jp.co.ha.business.healthInfo.HealthInfoGraphModel;

/**
 * 健康情報グラフ作成サービスインターフェース
 *
 * @since 1.0
 */
public interface HealthInfoGraphService {

    /**
     * 健康情報グラフを作成する
     *
     * @param model
     *     Model
     * @param supplier
     *     健康情報グラフへの変換Supplier。呼び出し元で健康情報グラフのインスタンスを返すSupplier処理を実装する
     */
    void putGraph(Model model, Supplier<HealthInfoGraphModel> supplier);

}

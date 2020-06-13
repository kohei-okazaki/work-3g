package jp.co.ha.tool.source;

import java.util.LinkedHashMap;
import java.util.Map;

import jp.co.ha.tool.source.type.AccessType;

/**
 * 自動生成JavaソースのMethodクラス
 *
 * @version 1.0.0
 */
public abstract class Method {

    /** 当メソッドのフィールド情報 */
    protected Field field;
    /** メソッドのアクセスタイプ */
    protected AccessType accessType;
    /** AnnotationMap */
    protected Map<Class<?>, String> annotationMap = new LinkedHashMap<>();
    /** 戻り値 */
    protected Class<?> returnType;
    /** 抽象メソッドかどうか */
    protected boolean isAbstract;
    /** 引数情報 */
    protected Signature signature;

    /**
     * コンストラクタ
     *
     * @param field
     *     当メソッドのフィールド情報
     * @param accessType
     *     メソッドのアクセスタイプ
     */
    public Method(Field field, AccessType accessType) {
        this.field = field;
        this.accessType = accessType;
    }

    /**
     * メソッド名を返す
     *
     * @return メソッド名
     */
    protected abstract String getMethodName();

    /**
     * Annotationを追加する
     *
     * @param clazz
     *     クラス型
     * @param option
     *     追加オプション
     */
    public void addAnnotation(Class<?> clazz, String option) {
        this.annotationMap.put(clazz, option);
    }

    /**
     * returnTypeを設定する
     *
     * @param returnType
     *     戻り値
     */
    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    /**
     * isAbstractを設定する
     *
     * @param isAbstract
     *     抽象メソッドかどうか
     */
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     * signatureを設定する
     *
     * @param signature
     *     引数情報
     */
    public void setSignature(Signature signature) {
        this.signature = signature;
    }

}

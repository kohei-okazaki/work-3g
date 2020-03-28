package jp.co.ha.tool.gen;

import java.io.Serializable;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.type.Charset;

/**
 * 自動生成ファイル情報を保持するクラス<br>
 * 基本的にSQLファイルもJavaファイルも文字コードはUTF-8とする
 *
 * @version 1.0.0
 */
public class GenerateFile implements Serializable {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = -4425711664703846016L;
    /** 出力先ファイルパス */
    private String outputPath;
    /** ファイル名 */
    private String fileName;
    /** ファイル内容 */
    private String data;
    /** 文字コード */
    private Charset charset = Charset.UTF_8;

    /**
     * outputPathを返す
     *
     * @return outputPath
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * outputPathを設定する
     *
     * @param outputPath
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * fileNameを返す
     *
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * fileNameを設定する
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * dataを返す
     *
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * dataを設定する
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * charsetを返す
     *
     * @return charset
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * charsetを設定する
     *
     * @param charset
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

}

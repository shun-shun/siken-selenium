package strategy;

import java.util.List;

import org.openqa.selenium.WebDriver;

import data.Question;

/**
 * 戦略
 */
public interface Strategy {

	/**
	 * 初期化処理
	 * @param span
	 */
	public abstract void init(List<String> select);

	/**
	 * 設定処理
	 * @param driver Seleniumドライバ
	 */
	public abstract void set(WebDriver driver);

	/**
	 * 出力処理
	 * @param question 設問データ
	 */
	public abstract void out(Question question);

	/**
	 * 最大問題数の取得
	 * @return 最大問題数
	 */
	public abstract int getNumberOf();
}

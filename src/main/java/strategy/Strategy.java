package strategy;

import java.util.List;

import org.openqa.selenium.WebDriver;

import data.Question;

/**
 * 戦略
 */
public interface Strategy {

	/**
	 * 設定処理
	 * @param driver Seleniumドライバ
	 */
	public abstract void init(WebDriver driver);

	/**
	 * 出力処理
	 * @param question 設問データ
	 */
	public abstract void out(Question question);

	/**
	 * ベースURLの取得
	 * @return url 問題のURL
	 */
	public abstract String getUrl();

	/**
	 * 年度配列を取得
	 * @return 年度配列
	 */
	public abstract List<String> getNendo();

	/**
	 * 問題配列を取得
	 * @return 問題配列
	 */
	public abstract List<String> getQuestionList();

}

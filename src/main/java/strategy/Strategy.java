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
	 * 
	 * @param driver
	 * @return
	 */
	public abstract Question execute(WebDriver driver, String url);

	/**
	 * 出力処理
	 * @param question 設問データ
	 */
	public abstract void out(Question question);

	public abstract String getUrl();

	public abstract List<String> getNendo();

	public abstract List<String> getQuestionList();

}

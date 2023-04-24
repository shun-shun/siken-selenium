package main;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

import data.Question;
import strategy.Strategy;

/**
 * 指針クラス
 */
public class Policy {

	/** 戦略 */
	private Strategy strategy;

	public Policy(Strategy strategy) {
		this.strategy = strategy;
	}



	/**
	 * 初期化処理
	 * @param driver Seleniumドライバ
	 */
	public void set(WebDriver driver) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		strategy.set(driver);
	}

	/**
	 * 出力処理
	 * @param question 設問データ
	 */
	public void out(Question question) {
		strategy.out(question);
	}

	/**
	 * 設問の最大問題数を取得
	 * @return 最大問題数
	 */
	public int getNumberOf() {
		return strategy.getNumberOf();
	}
}

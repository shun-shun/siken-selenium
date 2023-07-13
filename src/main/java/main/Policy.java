package main;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
	public void init(WebDriver driver) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		strategy.init(driver);
	}

	public void execute(WebDriver driver, String url) {
		Question question = strategy.execute(driver, url);
		strategy.out(question);
		return;
	}

	public List<String> getItem() {
		final String url = strategy.getUrl();
		List<String> nendo = strategy.getNendo();
		List<String> question = strategy.getQuestionList();
		List<String> item = new ArrayList<>();
		for (String n : nendo) {
			for (String q : question) {
				item.add(url + n + q);
			}
		}
		return item;
	}
}

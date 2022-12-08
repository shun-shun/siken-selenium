package main;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.Utils;
import data.Question;
import data.Question.SELECTION;
import strategy.Ap_Strategy;
import strategy.Ip_Strategy;
import strategy.Sc_Strategy;
import strategy.Strategy;

public class Main {

	/** 戦略群 */
	private static Map<Integer, Strategy> strategies = new HashMap<>();
	
	static {
		// 応用の戦略
		strategies.put(1, new Ap_Strategy());
		// 支援士の戦略
		strategies.put(2, new Sc_Strategy());
		// iパスの戦略
		strategies.put(3, new Ip_Strategy());
	}
	
	public static void main(String[] args) {
		// 戦略を選択する(1:応用　2:支援士　3:iパス)
		Strategy strategy = strategies.get(3);
		// ポリシーを組み立てる
		Policy policy = new Policy(strategy);
		
		// Seleniumドライバの生成
		WebDriver driver = new ChromeDriver();
		// 戦略ごとに初期化
		policy.init(driver);

		for (int i = 1; i <= policy.getNumberOf(); i++) {
			try {
				execute(policy, driver, i);
			} catch (NoSuchElementException e) {
				// 取得できない場合は無視
			}
			// 次の問題へ
			next(driver);
		}
		
		// Seleniumドライバの終了
		driver.quit();
	}

	private static void execute(Policy policy, WebDriver driver, int i) {
		Question question;
		if(i == 1) {
			// 1問目のみ
			question = first(driver);
		} else {
			// 2問目以降
			question = after(driver);
		}
		// 出力
		policy.out(question);
	}

	/**
	 * 初回問題のみのスクレイピング
	 * 
	 * @param driver Seleniumドライバ
	 * @return 設問データ(年度と問題文のみ)
	 */
	private static Question first(final WebDriver driver) {
		Question question = new Question();

		String year = driver.findElement(By.cssSelector(".main > div:nth-child(4)")).getText();
		question.setYear(Utils.crlfToSpace(year));

		String title = driver.findElement(By.cssSelector(".main > div:nth-child(3)")).getText();
		question.setTitle(Utils.crlfToSpace(title));

		return common(driver, question);
	}

	/**
	 * 2問目以降のスクレイピング
	 * 
	 * @param driver Seleniumドライバ
	 * @return 設問データ(年度と問題文のみ)
	 */
	private static Question after(final WebDriver driver) {
		Question question = new Question();

		String year = driver.findElement(By.cssSelector(".main > div:nth-child(5)")).getText();
		question.setYear(Utils.crlfToSpace(year));

		String title = driver.findElement(By.cssSelector(".main > div:nth-child(4)")).getText();
		question.setTitle(Utils.crlfToSpace(title));

		return common(driver, question);
	}

	/**
	 * 共通スクレイピング
	 * @param driver Seleniumドライバ
	 * @param question 設問データ(年度と問題文のみ)
	 * @return 設問データ
	 */
	private static Question common(final WebDriver driver, Question question) {

		String divA = driver.findElement(By.id("select_a")).getText();
		question.setA(Utils.crlfToSpace(divA));

		String divI = driver.findElement(By.id("select_i")).getText();
		question.setI(Utils.crlfToSpace(divI));

		String divU = driver.findElement(By.id("select_u")).getText();
		question.setU(Utils.crlfToSpace(divU));

		String divE = driver.findElement(By.id("select_e")).getText();
		question.setE(Utils.crlfToSpace(divE));

		Utils.await();
		driver.findElement(By.id("showAnswerBtn")).click();

		WebElement ans = driver.findElement(By.id("answerChar"));
		SELECTION selection = SELECTION.toSelection(ans.getText());
		question.setAns(selection);

		return question;
	}

	/**
	 * 次の設問ボタンを押下
	 * @param driver Seleniumドライバ
	 */
	private static void next(WebDriver driver) {
		
		WebDriverWait wait =  new WebDriverWait(driver, Duration.ofSeconds(10));
	    WebElement element=  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".bottomBtns > .submit")));
	    element.click();		

		Utils.await();
	}

}

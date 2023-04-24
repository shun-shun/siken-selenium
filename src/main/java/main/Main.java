package main;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.Utils;
import data.Question;
import data.Question.SELECTION;
import strategy.Ap_Strategy;
import strategy.Db_Strategy;
import strategy.Fe_Strategy;
import strategy.Ip_Strategy;
import strategy.Nw_Strategy;
import strategy.Pm_Strategy;
import strategy.Sc_Strategy;
import strategy.Sg_Strategy;
import strategy.Strategy;

public class Main {

	/** 戦略群 */
	private static Map<Integer, Strategy> strategies = new HashMap<>();

	/** 【デバッグ用】エラーが発生した問題の年度 */
	private static String errorYear = "";

	static {
		// 応用の戦略
		strategies.put(1, new Ap_Strategy());
		// 支援士の戦略
		strategies.put(2, new Sc_Strategy());
		// iパスの戦略
		strategies.put(3, new Ip_Strategy());
		// 基本の戦略
		strategies.put(4, new Fe_Strategy());
		// 基本の戦略
		strategies.put(5, new Sg_Strategy());
		// 基本の戦略
		strategies.put(6, new Pm_Strategy());
		// 基本の戦略
		strategies.put(7, new Nw_Strategy());
		// 基本の戦略
		strategies.put(8, new Db_Strategy());
	}

	public static void main(String[] args) {
		int input = getArgument(args);
		// 戦略を選択する(1:応用　2:支援士　3:iパス)
		Strategy strategy = strategies.get(1);

		List<String> select = new ArrayList<String>();
		select.add("アルゴリズムとプログラミング");
		strategy.init(select);

		// ポリシーを組み立てる
		Policy policy = new Policy(strategy);
		// Seleniumドライバの生成
		WebDriver driver = setOption();
		// 戦略ごとに初期化
		policy.set(driver);

		for (int i = 1; i <= policy.getNumberOf(); i++) {
			try {
				execute(policy, driver, i);
			} catch (Exception e) {
				// 取得できない場合は無視
				System.out.println(errorYear);
				e.printStackTrace();
			}
			// 次の問題へ
			next(driver);
		}

		// Seleniumドライバの終了
		driver.quit();
	}

	private static WebDriver setOption() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		final String UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1 Safari/605.1.15";
		options.addArguments("--user-agent=" + UA);
		WebDriver driver = new ChromeDriver(options);
		return driver;
	}

	private static int getArgument(String[] args) {
		int input = 0;
		if(args.length == 1) {
			try {
				input = Integer.parseInt(args[0]);
			}catch (NumberFormatException e) {
				System.err.println("引数は数値で選択してください.");
				System.exit(1);
			}
		} else if(args.length == 0) {
			input = 1;
		} else {
			System.err.println("引数は1つのみ設定できます.");
			System.exit(1);
		}
		return input;
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
		errorYear = year;

		String title = driver.findElement(By.cssSelector(".main > div:nth-child(3)")).getText();
		question.setTitle(Utils.crlfToSpace(title));

		try {
			String url = driver.findElement(By.cssSelector("#mainCol > div.main.kako.doujou > div:nth-child(3) > div > img")).getAttribute("src");
			question.setUrl(url);
		} catch(NoSuchElementException e) {
			// 画像が見つからない場合は無視
		}

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
		errorYear = year;

		String title = driver.findElement(By.cssSelector(".main > div:nth-child(4)")).getText();
		question.setTitle(Utils.crlfToSpace(title));

		try {
			String url = driver.findElement(By.cssSelector("#mainCol > div.main.kako.doujou > div:nth-child(4) > div > img")).getAttribute("src");
			question.setUrl(url);
		} catch(NoSuchElementException e) {
			// 画像が見つからない場合は無視
		}
		return common(driver, question);
	}

	/**
	 * 共通スクレイピング
	 * @param driver Seleniumドライバ
	 * @param question 設問データ(年度と問題文のみ)
	 * @return 設問データ
	 */
	private static Question common(final WebDriver driver, Question question) {

		// 分類のテキストを抽出
		String clazzP = driver.findElement(By.cssSelector("#mainCol > div.main.kako.doujou > p")).getText();
		// 「»」以降の文字を削除
		String clazz1 = clazzP.replaceAll("\\s».*", "");
		question.setClazz1(clazz1);
		// 先頭から最初の「»」までを削除し、「»」以降の文字を削除（中央の文字のみ抽出）
		String clazz2 = clazzP.replaceAll("^.*?»\\s", "").replaceAll("\\s».*", "");
		question.setClazz2(clazz2);
		// 先頭から最後の「»」までを削除（最後の文字のみ抽出）
		String clazz3 = clazzP.replaceAll("^.*»", "");
		question.setClazz3(clazz3);

		try {
			try {
				// 選択肢がテキストの場合
				String divA = driver.findElement(By.id("select_a")).getText();
				question.setA(Utils.crlfToSpace(divA));
				String divI = driver.findElement(By.id("select_i")).getText();
				question.setI(Utils.crlfToSpace(divI));
				String divU = driver.findElement(By.id("select_u")).getText();
				question.setU(Utils.crlfToSpace(divU));
				String divE = driver.findElement(By.id("select_e")).getText();
				question.setE(Utils.crlfToSpace(divE));
			} catch(NoSuchElementException e) {
				//  選択肢が画像の場合（選択肢それぞれに画像の場合）
				String srcA = driver.findElement(By.id("select_a")).findElement(By.tagName("img")).getAttribute("src");
				question.setUrlA(srcA);
				String srcI = driver.findElement(By.id("select_i")).findElement(By.tagName("img")).getAttribute("src");
				question.setUrlI(srcI);
				String srcU = driver.findElement(By.id("select_u")).findElement(By.tagName("img")).getAttribute("src");
				question.setUrlU(srcU);
				String srcE = driver.findElement(By.id("select_e")).findElement(By.tagName("img")).getAttribute("src");
				question.setUrlE(srcE);
			}
		} catch(NoSuchElementException e) {
			//  選択肢が画像の場合（画像が１つだけの場合）
			String srcMain = driver.findElement(By.className("selectList")).findElement(By.tagName("img")).getAttribute("src");
			question.setUrlMain(srcMain);
		}

		// 次の問題をクリック
		Utils.await();
		driver.findElement(By.id("showAnswerBtn")).click();
		Utils.await();

		WebElement ans = driver.findElement(By.id("answerChar"));
		SELECTION selection = SELECTION.toSelection(ans.getText());
		question.setAns(selection.getValue());
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

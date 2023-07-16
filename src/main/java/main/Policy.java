package main;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.Utils;
import data.Question;
import data.Question.SELECTION;
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

	/**
	 * スクレイピング処理
	 * @param driver Seleniumドライバ
	 * @param url 問題へのURL
	 */
	public void execute(WebDriver driver, String url) {
		Question question = scraping(driver, url);
		strategy.out(question);
		return;
	}

	private Question scraping(WebDriver driver, String url) {
		// 問題へアクセス
		driver.get(url);
		Utils.await();
		Question question = new Question();
		// URLの設定
		question.setUrl(url);
		// 問題の年度を抽出
		String year = driver.findElement(By.cssSelector("#mainCol > div.main.kako > h2")).getText();
		question.setYear(year);
		// 分類のテキストを抽出
		String q;
		try {
			q = driver.findElement(By.cssSelector("#mondai")).getText();
		} catch(NoSuchElementException e) {
			q = driver.findElement(By.cssSelector("#mainCol > div.main.kako > div:nth-child(4)")).getText();
		}
		question.setTitle(Utils.crlfToSpace(q));
		// 分類のテキストを抽出
		String clazzP = driver.findElement(By.cssSelector("#mainCol > div.main.kako > p")).getText();
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
			} catch (NoSuchElementException e) {
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
		} catch (NoSuchElementException e) {
			//  選択肢が画像の場合（画像が１つだけの場合）
			String srcMain = driver.findElement(By.className("selectList")).findElement(By.tagName("img"))
					.getAttribute("src");
			question.setUrlMain(srcMain);
		}

		// 次の問題をクリック
		Utils.await();
		driver.findElement(By.id("showAnswerBtn")).click();
		Utils.await();

		try {
			WebElement ans = driver.findElement(By.id("answerChar"));
			SELECTION selection = SELECTION.toSelection(ans.getText());
			question.setAns(selection.getValue());
		} catch (NoSuchElementException  | IllegalArgumentException e) {
			// 何もしない
			System.err.println(question.getTitle());
		}
		return question;
	}

	/**
	 * URLリストの取得
	 * @return 年度と問題数を組み合わせたURL
	 */
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

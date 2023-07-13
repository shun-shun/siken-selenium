package strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import common.Utils;
import data.Question;
import data.Question.SELECTION;

/**
 * 応用の戦略
 */
public class Ap_Strategy implements Strategy {

	/** アクセス先 */
	private static final String URL = "https://www.ap-siken.com/kakomon/";

	/** ファイル名 */
	private static final String FILE_NAME = "ap.csv";

	/** 年度 */
	private List<String> nendo = new ArrayList<>();

	/** 問題番号 */
	private List<String> questionList = new ArrayList<>();

	public Ap_Strategy() {
		nendo.add("05_haru");
		nendo.add("04_aki");
		nendo.add("04_haru");
		nendo.add("03_aki");
		nendo.add("03_haru");
		nendo.add("02_aki");
		nendo.add("01_aki");
		nendo.add("31_haru");
		nendo.add("30_haru");
		nendo.add("30_aki");
		nendo.add("29_aki");
		nendo.add("29_haru");
		nendo.add("28_aki");
		nendo.add("28_haru");
		nendo.add("27_aki");
		nendo.add("27_haru");
		nendo.add("26_aki");
		nendo.add("26_haru");
		nendo.add("25_aki");
		nendo.add("25_haru");
		nendo.add("24_aki");
		nendo.add("24_haru");
		nendo.add("23_aki");
		nendo.add("23_haru");
		nendo.add("22_aki");
		nendo.add("22_haru");
		nendo.add("21_aki");
		nendo.add("21_haru");

		questionList.add("/q1.html");
		questionList.add("/q2.html");
		questionList.add("/q3.html");
		questionList.add("/q4.html");
		questionList.add("/q5.html");
		questionList.add("/q6.html");
		questionList.add("/q7.html");
		questionList.add("/q8.html");
		questionList.add("/q9.html");
		questionList.add("/q10.html");
		questionList.add("/q11.html");
		questionList.add("/q12.html");
		questionList.add("/q13.html");
		questionList.add("/q14.html");
		questionList.add("/q15.html");
		questionList.add("/q16.html");
		questionList.add("/q17.html");
		questionList.add("/q18.html");
		questionList.add("/q19.html");
		questionList.add("/q20.html");
		questionList.add("/q21.html");
		questionList.add("/q22.html");
		questionList.add("/q23.html");
		questionList.add("/q24.html");
		questionList.add("/q25.html");
		questionList.add("/q26.html");
		questionList.add("/q27.html");
		questionList.add("/q28.html");
		questionList.add("/q29.html");
		questionList.add("/q30.html");
		questionList.add("/q31.html");
		questionList.add("/q32.html");
		questionList.add("/q33.html");
		questionList.add("/q34.html");
		questionList.add("/q35.html");
		questionList.add("/q36.html");
		questionList.add("/q37.html");
		questionList.add("/q38.html");
		questionList.add("/q39.html");
		questionList.add("/q40.html");
		questionList.add("/q41.html");
		questionList.add("/q42.html");
		questionList.add("/q43.html");
		questionList.add("/q44.html");
		questionList.add("/q45.html");
		questionList.add("/q46.html");
		questionList.add("/q47.html");
		questionList.add("/q48.html");
		questionList.add("/q49.html");
		questionList.add("/q50.html");
		questionList.add("/q51.html");
		questionList.add("/q52.html");
		questionList.add("/q53.html");
		questionList.add("/q54.html");
		questionList.add("/q55.html");
		questionList.add("/q56.html");
		questionList.add("/q57.html");
		questionList.add("/q58.html");
		questionList.add("/q59.html");
		questionList.add("/q60.html");
		questionList.add("/q61.html");
		questionList.add("/q62.html");
		questionList.add("/q63.html");
		questionList.add("/q64.html");
		questionList.add("/q65.html");
		questionList.add("/q66.html");
		questionList.add("/q67.html");
		questionList.add("/q68.html");
		questionList.add("/q69.html");
		questionList.add("/q70.html");
		questionList.add("/q71.html");
		questionList.add("/q72.html");
		questionList.add("/q73.html");
		questionList.add("/q74.html");
		questionList.add("/q75.html");
		questionList.add("/q76.html");
		questionList.add("/q77.html");
		questionList.add("/q78.html");
		questionList.add("/q79.html");
		questionList.add("/q80.html");
	}

	@Override
	public void init(WebDriver driver) {
		driver.get(URL);
		Utils.await();
	}

	@Override
	public void out(Question question) {
		try (Writer writer = new FileWriter(FILE_NAME, true);) {
			StatefulBeanToCsv<Question> beanToCsv = new StatefulBeanToCsvBuilder<Question>(writer).build();
			beanToCsv.write(question);
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Question execute(WebDriver driver, String url) {
		driver.get(url);
		Utils.await();
		Question question = new Question();
		String year = driver.findElement(By.cssSelector("#mainCol > div.main.kako > h2")).getText();
		question.setYear(year);
		// 分類のテキストを抽出
		String q = driver.findElement(By.cssSelector("#mondai")).getText();
		question.setTitle(q);
		question.setUrl(url);
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
		} catch (NoSuchElementException e) {
			// 何もしない
			System.err.println(question.getTitle());
		}
		return question;
	}

	public String getUrl() {
		return URL;
	}

	@Override
	public List<String> getNendo() {
		return this.nendo;
	}

	@Override
	public List<String> getQuestionList() {
		return this.questionList;
	}

}

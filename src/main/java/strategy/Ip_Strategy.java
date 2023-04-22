package strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import common.Utils;
import data.Question;

public class Ip_Strategy implements Strategy {

	/** アクセス先 */
	private static final String URL = "https://www.itpassportsiken.com/ipkakomon.php";

	/** 最大問題数 */
	private static int IP_NUMBER_OF_QUESTIONS = 0;

	/** ファイル名 */
	private static final String FILE_NAME = "ip.csv";

	/** 表示項目 */
	private static Map<String, String> span = new HashMap<>();

	/** 選択 */
	private static Map<String,String> xPath = new HashMap<>();

	/** 選択項目 */
	private List<String> select;

	static {

	}

	@Override
	public void init(List<String> select) {
		this.select = select;
	}

	@Override
	public void set(WebDriver driver) {
		driver.get(URL);
		Utils.await();
		// 分野タブを選択
		driver.findElement(By.cssSelector("#tabs > ul > li:nth-child(2) > a")).click();
		Utils.await();

		// 全項目チェックをOFF
		for(String s: select) {
			// 表示項目のパスを抽出
			String keySpan = span.get(s);
			// 問題数のみ抽出
			String span = driver.findElement(By.cssSelector(keySpan)).getText();
			// 問題数を加算
			IP_NUMBER_OF_QUESTIONS += Integer.parseInt(span.replaceAll("^.*\\(", "").replaceAll("\\)$", ""));

			// チェックボックスのパスを抽出
			String keyXPath = xPath.get(s);
			// チェックボックをクリック
			driver.findElement(By.cssSelector(keyXPath)).click();
			Utils.await();
		}

		driver.findElement(By.cssSelector(".submit")).click();
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
	public int getNumberOf() {
		return IP_NUMBER_OF_QUESTIONS;
	}

}

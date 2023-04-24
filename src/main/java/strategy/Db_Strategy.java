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

public class Db_Strategy implements Strategy {

	/** アクセス先 */
	private static final String URL = "https://www.db-siken.com/sckakomon.php";

	/** 最大問題数 */
	private static int DB_NUMBER_OF_QUESTIONS = 0;

	/** ファイル名 */
	private static final String FILE_NAME = "db.csv";

	/** 表示項目 */
	private static Map<String, String> span = new HashMap<>();

	/** 選択 */
	private static Map<String,String> xPath = new HashMap<>();

	/** 選択項目 */
	private List<String> select;

	static {
		xPath.put("データベース方式"," #sp_all > label:nth-child(1) > input[type=checkbox]");
		xPath.put("データベース設計"," #sp_all > label:nth-child(2) > input[type=checkbox]");
		xPath.put("データ操作"," #sp_all > label:nth-child(3) > input[type=checkbox]");
		xPath.put("トランザクション処理"," #sp_all > label:nth-child(4) > input[type=checkbox]");
		xPath.put("データベース応用"," #sp_all > label:nth-child(5) > input[type=checkbox]");
		xPath.put("コンピュータ構成要素"," #other_all > label:nth-child(1) > input[type=checkbox]");
		xPath.put("システム構成要素"," #other_all > label:nth-child(2) > input[type=checkbox]");
		xPath.put("ネットワーク"," #other_all > label:nth-child(3) > input[type=checkbox]");
		xPath.put("セキュリティ"," #other_all > label:nth-child(4) > input[type=checkbox]");
		xPath.put("システム開発技術"," #other_all > label:nth-child(5) > input[type=checkbox]");
		xPath.put("ソフトウェア開発管理技術"," #other_all > label:nth-child(6) > input[type=checkbox]");
		
		span.put("データベース方式"," #sp_all > label:nth-child(1) > span");
		span.put("データベース設計"," #sp_all > label:nth-child(2) > span");
		span.put("データ操作"," #sp_all > label:nth-child(3) > span");
		span.put("トランザクション処理"," #sp_all > label:nth-child(4) > span");
		span.put("データベース応用"," #sp_all > label:nth-child(5) > span");
		span.put("コンピュータ構成要素"," #other_all > label:nth-child(1) > span");
		span.put("システム構成要素"," #other_all > label:nth-child(2) > span");
		span.put("ネットワーク"," #other_all > label:nth-child(3) > span");
		span.put("セキュリティ"," #other_all > label:nth-child(4) > span");
		span.put("システム開発技術"," #other_all > label:nth-child(5) > span");
		span.put("ソフトウェア開発管理技術"," #other_all > label:nth-child(6) > span");
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
		driver.findElement(By.cssSelector("#bunya > div.check_all_wrap > button:nth-child(2)")).click();
		Utils.await();

		for (String s : select) {
			// 表示項目のパスを抽出
			String keySpan = span.get(s);
			// 問題数のみ抽出
			String span = driver.findElement(By.cssSelector(keySpan)).getText();
			// 問題数を加算
			DB_NUMBER_OF_QUESTIONS += Integer.parseInt(span.replaceAll("^.*\\(", "").replaceAll("\\)$", ""));

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
		return DB_NUMBER_OF_QUESTIONS;
	}


}

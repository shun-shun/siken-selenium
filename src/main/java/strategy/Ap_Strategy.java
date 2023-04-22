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

/**
 * 応用の戦略
 */
public class Ap_Strategy implements Strategy {

	/** アクセス先 */
	private static final String URL = "https://www.ap-siken.com/apkakomon.php";

	/** 最大問題数 */
	private static int AP_NUMBER_OF_QUESTIONS = 0;

	/** ファイル名 */
	private static final String FILE_NAME = "ap.csv";

	/** 表示項目 */
	private static Map<String, String> span = new HashMap<>();

	/** 選択 */
	private static Map<String,String> xPath = new HashMap<>();

	/** 選択項目 */
	private List<String> select;

	static {
		xPath.put("基礎理論"," #te_all > label:nth-child(1) > input[type=checkbox]");
		xPath.put("アルゴリズムとプログラミング"," #te_all > label:nth-child(2) > input[type=checkbox]");
		xPath.put("コンピュータ構成要素"," #te_all > label:nth-child(3) > input[type=checkbox]");
		xPath.put("システム構成要素"," #te_all > label:nth-child(4) > input[type=checkbox]");
		xPath.put("ソフトウェア"," #te_all > label:nth-child(5) > input[type=checkbox]");
		xPath.put("ハードウェア"," #te_all > label:nth-child(6) > input[type=checkbox]");
		xPath.put("ヒューマンインタフェース"," #te_all > label:nth-child(7) > input[type=checkbox]");
		xPath.put("マルチメディア"," #te_all > label:nth-child(8) > input[type=checkbox]");
		xPath.put("データベース"," #te_all > label:nth-child(9) > input[type=checkbox]");
		xPath.put("ネットワーク"," #te_all > label:nth-child(10) > input[type=checkbox]");
		xPath.put("セキュリティ"," #te_all > label:nth-child(11) > input[type=checkbox]");
		xPath.put("システム開発技術"," #te_all > label:nth-child(12) > input[type=checkbox]");
		xPath.put("ソフトウェア開発管理技術"," #te_all > label:nth-child(13) > input[type=checkbox]");
		xPath.put("プロジェクトマネジメント"," #ma_all > label:nth-child(1) > input[type=checkbox]");
		xPath.put("サービスマネジメント"," #ma_all > label:nth-child(2) > input[type=checkbox]");
		xPath.put("システム監査"," #ma_all > label:nth-child(3) > input[type=checkbox]");
		xPath.put("システム戦略"," #st_all > label:nth-child(1) > input[type=checkbox]");
		xPath.put("システム企画"," #st_all > label:nth-child(2) > input[type=checkbox]");
		xPath.put("経営戦略マネジメント"," #st_all > label:nth-child(3) > input[type=checkbox]");
		xPath.put("技術戦略マネジメント"," #st_all > label:nth-child(4) > input[type=checkbox]");
		xPath.put("ビジネスインダストリ"," #st_all > label:nth-child(5) > input[type=checkbox]");
		xPath.put("企業活動"," #st_all > label:nth-child(6) > input[type=checkbox]");
		xPath.put("法務"," #st_all > label:nth-child(7) > input[type=checkbox]");

		span.put("基礎理論"," #te_all > label:nth-child(1) > span");
		span.put("アルゴリズムとプログラミング"," #te_all > label:nth-child(2) > span");
		span.put("コンピュータ構成要素"," #te_all > label:nth-child(3) > span");
		span.put("システム構成要素"," #te_all > label:nth-child(4) > span");
		span.put("ソフトウェア"," #te_all > label:nth-child(5) > span");
		span.put("ハードウェア"," #te_all > label:nth-child(6) > span");
		span.put("ヒューマンインタフェース"," #te_all > label:nth-child(7) > span");
		span.put("マルチメディア"," #te_all > label:nth-child(8) > span");
		span.put("データベース"," #te_all > label:nth-child(9) > span");
		span.put("ネットワーク"," #te_all > label:nth-child(10) > span");
		span.put("セキュリティ"," #te_all > label:nth-child(11) > span");
		span.put("システム開発技術"," #te_all > label:nth-child(12) > span");
		span.put("ソフトウェア開発管理技術"," #te_all > label:nth-child(13) > span");
		span.put("プロジェクトマネジメント"," #ma_all > label:nth-child(1) > span");
		span.put("サービスマネジメント"," #ma_all > label:nth-child(2) > span");
		span.put("システム監査"," #ma_all > label:nth-child(3) > span");
		span.put("システム戦略"," #st_all > label:nth-child(1) > span");
		span.put("システム企画"," #st_all > label:nth-child(2) > span");
		span.put("経営戦略マネジメント"," #st_all > label:nth-child(3) > span");
		span.put("技術戦略マネジメント"," #st_all > label:nth-child(4) > span");
		span.put("ビジネスインダストリ"," #st_all > label:nth-child(5) > span");
		span.put("企業活動"," #st_all > label:nth-child(6) > span");
		span.put("法務"," #st_all > label:nth-child(7) > span");
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

		for(String s: select) {
			// 表示項目のパスを抽出
			String keySpan = span.get(s);
			// 問題数のみ抽出
			String span = driver.findElement(By.cssSelector(keySpan)).getText();
			// 問題数を加算
			AP_NUMBER_OF_QUESTIONS += Integer.parseInt(span.replaceAll("^.*\\(", "").replaceAll("\\)$", ""));

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
		System.out.println(AP_NUMBER_OF_QUESTIONS);
	}

	@Override
	public int getNumberOf() {
		return AP_NUMBER_OF_QUESTIONS;
	}

}

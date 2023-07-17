package strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import common.Utils;
import data.Question;

public class ScStrategy implements Strategy {

	/** アクセス先 */
	private static final String URL = "https://www.sc-siken.com/kakomon/";

	/** ファイル名 */
	private static final String FILE_NAME = "sc.csv";

	/** 年度 */
	private List<String> nendo = new ArrayList<>();

	/** 問題番号 */
	private List<String> questionList = new ArrayList<>();

	public ScStrategy() {
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
		nendo.add("23_toku");
		nendo.add("22_aki");
		nendo.add("22_haru");
		nendo.add("21_aki");
		nendo.add("21_haru");

		questionList.add("/am2_1.html");
		questionList.add("/am2_2.html");
		questionList.add("/am2_3.html");
		questionList.add("/am2_4.html");
		questionList.add("/am2_5.html");
		questionList.add("/am2_6.html");
		questionList.add("/am2_7.html");
		questionList.add("/am2_8.html");
		questionList.add("/am2_9.html");
		questionList.add("/am2_10.html");
		questionList.add("/am2_11.html");
		questionList.add("/am2_12.html");
		questionList.add("/am2_13.html");
		questionList.add("/am2_14.html");
		questionList.add("/am2_15.html");
		questionList.add("/am2_16.html");
		questionList.add("/am2_17.html");
		questionList.add("/am2_18.html");
		questionList.add("/am2_19.html");
		questionList.add("/am2_20.html");
		questionList.add("/am2_21.html");
		questionList.add("/am2_22.html");
		questionList.add("/am2_23.html");
		questionList.add("/am2_24.html");
		questionList.add("/am2_25.html");

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

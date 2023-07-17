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

public class CoStrategy implements Strategy {

	/** アクセス先 */
	private static final String URL = "https://www.sc-siken.com/kakomon/";

	/** ファイル名 */
	private static final String FILE_NAME = "common.csv";

	/** 年度 */
	private List<String> nendo = new ArrayList<>();

	/** 問題番号 */
	private List<String> questionList = new ArrayList<>();

	public CoStrategy() {
//		nendo.add("05_haru");
//		nendo.add("04_aki");
//		nendo.add("04_haru");
//		nendo.add("03_aki");
//		nendo.add("03_haru");
//		nendo.add("02_aki");
//		nendo.add("01_aki");
//		nendo.add("31_haru");
//		nendo.add("30_haru");
//		nendo.add("30_aki");
//		nendo.add("29_aki");
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

		questionList.add("/am1_1.html");
		questionList.add("/am1_2.html");
		questionList.add("/am1_3.html");
		questionList.add("/am1_4.html");
		questionList.add("/am1_5.html");
		questionList.add("/am1_6.html");
		questionList.add("/am1_7.html");
		questionList.add("/am1_8.html");
		questionList.add("/am1_9.html");
		questionList.add("/am1_10.html");
		questionList.add("/am1_11.html");
		questionList.add("/am1_12.html");
		questionList.add("/am1_13.html");
		questionList.add("/am1_14.html");
		questionList.add("/am1_15.html");
		questionList.add("/am1_16.html");
		questionList.add("/am1_17.html");
		questionList.add("/am1_18.html");
		questionList.add("/am1_19.html");
		questionList.add("/am1_20.html");
		questionList.add("/am1_21.html");
		questionList.add("/am1_22.html");
		questionList.add("/am1_23.html");
		questionList.add("/am1_24.html");
		questionList.add("/am1_25.html");
		questionList.add("/am1_26.html");
		questionList.add("/am1_27.html");
		questionList.add("/am1_28.html");
		questionList.add("/am1_29.html");
		questionList.add("/am1_30.html");

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

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

public class SgStrategy implements Strategy {

	/** アクセス先 */
	private static final String URL = "https://www.sg-siken.com/kakomon/";

	/** ファイル名 */
	private static final String FILE_NAME = "sg.csv";

	/** 年度 */
	private List<String> nendo = new ArrayList<>();

	/** 問題番号 */
	private List<String> questionList = new ArrayList<>();

	public SgStrategy() {
//		nendo.add("sample");
		nendo.add("01_aki");
		nendo.add("31_haru");
		nendo.add("30_haru");
		nendo.add("30_aki");
		nendo.add("29_aki");
		nendo.add("29_haru");
		nendo.add("28_aki");
		nendo.add("28_haru");

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

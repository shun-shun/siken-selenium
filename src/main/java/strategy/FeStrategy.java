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

public class FeStrategy implements Strategy {

	/** アクセス先 */
	private static final String URL = "https://www.fe-siken.com/kakomon/";

	/** ファイル名 */
	private static final String FILE_NAME = "fe.csv";

	/** 年度 */
	private List<String> nendo = new ArrayList<>();

	/** 問題番号 */
	private List<String> questionList = new ArrayList<>();

	public FeStrategy() {
//		nendo.add("05_haru");
//		nendo.add("sample");
//		nendo.add("03_menjo");
//		nendo.add("02_menjo");
//		nendo.add("01_aki");
//		nendo.add("31_haru");
//		nendo.add("30_haru");
//		nendo.add("30_aki");
//		nendo.add("29_aki");
//		nendo.add("29_haru");
//		nendo.add("28_aki");
//		nendo.add("28_haru");
//		nendo.add("27_aki");
//		nendo.add("27_haru");
//		nendo.add("26_aki");
//		nendo.add("26_haru");
//		nendo.add("25_aki");
//		nendo.add("25_haru");
//		nendo.add("24_aki");
//		nendo.add("24_haru");
//		nendo.add("23_aki");
//		nendo.add("23_toku");
//		nendo.add("22_aki");
//		nendo.add("22_haru");
//		nendo.add("21_aki");
//		nendo.add("21_haru");
		nendo.add("20_aki/");
		nendo.add("20_haru/");
		nendo.add("19_aki/");
		nendo.add("19_haru/");
		nendo.add("18_aki/");
		nendo.add("18_haru/");
		nendo.add("17_aki/");
		nendo.add("17_haru/");
		nendo.add("16_aki/");
		nendo.add("16_haru/");
		nendo.add("15_aki/");
		nendo.add("15_haru/");
		nendo.add("14_aki/");
		nendo.add("14_haru/");
		nendo.add("13_aki/");
		nendo.add("13_haru/");

//		questionList.add("/a1.html");
//		questionList.add("/a2.html");
//		questionList.add("/a3.html");
//		questionList.add("/a4.html");
//		questionList.add("/a5.html");
//		questionList.add("/a6.html");
//		questionList.add("/a7.html");
//		questionList.add("/a8.html");
//		questionList.add("/a9.html");
//		questionList.add("/a10.html");
//		questionList.add("/a11.html");
//		questionList.add("/a12.html");
//		questionList.add("/a13.html");
//		questionList.add("/a14.html");
//		questionList.add("/a15.html");
//		questionList.add("/a16.html");
//		questionList.add("/a17.html");
//		questionList.add("/a18.html");
//		questionList.add("/a19.html");
//		questionList.add("/a20.html");
//		questionList.add("/a21.html");
//		questionList.add("/a22.html");
//		questionList.add("/a23.html");
//		questionList.add("/a24.html");
//		questionList.add("/a25.html");
//		questionList.add("/a26.html");
//		questionList.add("/a27.html");
//		questionList.add("/a28.html");
//		questionList.add("/a29.html");
//		questionList.add("/a30.html");
//		questionList.add("/a31.html");
//		questionList.add("/a32.html");
//		questionList.add("/a33.html");
//		questionList.add("/a34.html");
//		questionList.add("/a35.html");
//		questionList.add("/a36.html");
//		questionList.add("/a37.html");
//		questionList.add("/a38.html");
//		questionList.add("/a39.html");
//		questionList.add("/a40.html");
//		questionList.add("/a41.html");
//		questionList.add("/a42.html");
//		questionList.add("/a43.html");
//		questionList.add("/a44.html");
//		questionList.add("/a45.html");
//		questionList.add("/a46.html");
//		questionList.add("/a47.html");
//		questionList.add("/a48.html");
//		questionList.add("/a49.html");
//		questionList.add("/a50.html");
//		questionList.add("/a51.html");
//		questionList.add("/a52.html");
//		questionList.add("/a53.html");
//		questionList.add("/a54.html");
//		questionList.add("/a55.html");
//		questionList.add("/a56.html");
//		questionList.add("/a57.html");
//		questionList.add("/a58.html");
//		questionList.add("/a59.html");
//		questionList.add("/a60.html");
		
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

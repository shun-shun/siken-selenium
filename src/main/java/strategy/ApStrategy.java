package strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import common.Utils;
import data.Question;
import data.Setting;

/**
 * 応用の戦略
 */
public class ApStrategy implements Strategy {

	/** 設定ファイルパス */
	private static final String AP_CONF_JSON = "ap.conf.json";

	/** アクセス先 */
	private static final String URL = "https://www.ap-siken.com/kakomon/";

	/** ファイル名 */
	private static final String FILE_NAME = "out/ap.csv";
	
	/** 設定情報 */
	private Setting setting;

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
		return this.setting.getNendo();
	}

	@Override
	public List<String> getQuestionList() {
		return this.setting.getQuestionList();
	}
	
	@Override
	public String getConfJson() {
		return AP_CONF_JSON;
	}
	
	@Override
	public void setSetting(Setting setting) {
		this.setting = setting;
	}

}

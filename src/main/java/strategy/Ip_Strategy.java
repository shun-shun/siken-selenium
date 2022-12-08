package strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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
	private static final int IP_NUMBER_OF_QUESTIONS = 364;
	
	/** ファイル名 */
	private static final String FILE_NAME = "ip.csv";

	@Override
	public void init(WebDriver driver) {
		driver.get(URL);
		Utils.await();
		driver.findElement(By.cssSelector("#tabs > ul > li:nth-child(2) > a")).click();
		Utils.await();
		driver.findElement(By.cssSelector("#bunya > div.check_all_wrap > button:nth-child(2)")).click();
		Utils.await();
		driver.findElement(By.cssSelector("#te_all > label:nth-child(11) > input[type=checkbox]")).click();
		Utils.await();
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

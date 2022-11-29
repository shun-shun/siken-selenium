package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import data.Question;
import data.Question.SELECTION;

public class Main {

	private static final int NUMBER_OF_QUESTIONS = 675;

	private static final long WAIT_TIME_MILL = 1000;

	private static boolean FIRST = true;

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();

		init(driver);

		for (int i = 1; i <= NUMBER_OF_QUESTIONS; i++) {
			await();

			try {
				execute(driver);
			} catch (NoSuchElementException e) {
				// nothing...
			}

			next(driver);

			FIRST = false;
		}

		driver.quit();

	}

	private static void await() {
		final int target = (int) (Math.random() * 10) + 1;
		for (int i = 0; i < target; i++) {
			try {
				Thread.sleep(WAIT_TIME_MILL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void execute(WebDriver driver) throws NoSuchElementException {
		Question question = new Question();

		WebElement year = null;
		if (FIRST) {
			year = driver.findElement(By.cssSelector(".main > div:nth-child(4)"));
		} else {
			year = driver.findElement(By.cssSelector(".main > div:nth-child(5)"));
		}
		final String y = year.getText();
		if (y != null) {
			question.setYear(y.replaceAll("\r\n", " ").replaceAll("\r", " ").replaceAll("\n", " "));
		}

		WebElement title = null;
		if (FIRST) {
			title = driver.findElement(By.cssSelector(".main > div:nth-child(3)"));
		} else {
			title = driver.findElement(By.cssSelector(".main > div:nth-child(4)"));
		}
		question.setTitle(title.getText());

		WebElement divA = driver.findElement(By.id("select_a"));
		question.setA(divA.getText());

		WebElement divI = driver.findElement(By.id("select_i"));
		question.setI(divI.getText());

		WebElement divU = driver.findElement(By.id("select_u"));
		question.setU(divU.getText());

		WebElement divE = driver.findElement(By.id("select_e"));
		question.setE(divE.getText());

		driver.findElement(By.id("showAnswerBtn")).click();

		WebElement ans = driver.findElement(By.id("answerChar"));

		SELECTION selection = SELECTION.toSelection(ans.getText());
		question.setAns(selection);

		try (Writer writer = new FileWriter("text.csv", true);) {
			StatefulBeanToCsv<Question> beanToCsv = new StatefulBeanToCsvBuilder<Question>(writer).build();
			beanToCsv.write(question);
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		}
	}

	private static void init(WebDriver driver) {
		driver.get("https://www.sc-siken.com/sckakomon.php");

		await();

		driver.findElement(By.cssSelector(".submit")).click();
	}

	private static void next(WebDriver driver) {
		driver.findElement(By.cssSelector(".bottomBtns > .submit")).click();

		await();
	}

}

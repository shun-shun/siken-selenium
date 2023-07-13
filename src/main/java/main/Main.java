package main;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import strategy.ApStrategy;
import strategy.Strategy;

public class Main {

	/** 戦略群 */
	private static Map<Integer, Strategy> strategies = new HashMap<>();

	static {
		// 応用の戦略
		strategies.put(1, new ApStrategy());
	}

	public static void main(String[] args) {
		int input = getArgument(args);
		// ポリシーを組み立てる
		Policy policy = new Policy(strategies.get(1));
		// Seleniumドライバの生成
		WebDriver driver = setOption();
		// 戦略ごとに初期化
		policy.init(driver);

		List<String> item = policy.getItem();
		for (String url : item) {
			while(true) {
				try {
					// 実行
					policy.execute(driver, url);
					break;
				} catch(TimeoutException e) {
					continue;
				}
			}
		}
		// Seleniumドライバの終了
		driver.quit();
	}

	private static WebDriver setOption() {
		System.setProperty("webdriver.chrome.driver", "C:\\free\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		//要素が見つからない場合最大で20秒間待つよう指定
	    Duration waitTime = Duration.ofSeconds(20);
	    driver.manage().timeouts().pageLoadTimeout(waitTime);
	    driver.manage().timeouts().implicitlyWait(waitTime);
		return driver;
	}

	private static int getArgument(String[] args) {
		int input = 0;
		if (args.length == 1) {
			try {
				input = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("引数は数値で選択してください.");
				System.exit(1);
			}
		} else if (args.length == 0) {
			input = 1;
		} else {
			System.err.println("引数は1つのみ設定できます.");
			System.exit(1);
		}
		return input;
	}

}

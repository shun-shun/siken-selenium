package main;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import strategy.ApStrategy;
import strategy.FeStrategy;
import strategy.ItpStrategy;
import strategy.SgStrategy;
import strategy.Strategy;

public class Main {

	/** 戦略群 */
	private static Map<Integer, Strategy> strategies = new HashMap<>();

	static {
		// 応用の戦略
		strategies.put(1, new ApStrategy());
		strategies.put(2, new FeStrategy());
		strategies.put(3, new SgStrategy());
		strategies.put(4, new ItpStrategy());
	}

	public static void main(String[] args) {
		int input = getArgument(args);
		// ポリシーを組み立てる
		Policy policy = new Policy(strategies.get(4));
		// Seleniumドライバの生成
		WebDriver driver = setOption();
		// 戦略ごとに初期化
		policy.init(driver);

		double start = System.nanoTime();
		List<String> item = policy.getItem();
		int i = 1;
		for (String url : item) {
			while (true) {
				try {
					// 実行
					policy.execute(driver, url);
					break;
				} catch (TimeoutException e) {
					continue;
				}
			}
			double end = System.nanoTime();
			System.out.println("全体：" + item.size() + " 現在：" + i++ + " 処理時間：" + ((end - start) / Math.pow(10, 9))  + "s");
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

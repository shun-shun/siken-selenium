package main;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import common.Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import strategy.ApStrategy;
import strategy.CoStrategy;
import strategy.DbStrategy;
import strategy.FeStrategy;
import strategy.ItpStrategy;
import strategy.NwStrategy;
import strategy.ScStrategy;
import strategy.SgStrategy;
import strategy.Strategy;

public class Main {

	/** 出力先フォルダパス */
	private static final String OUT = "out";
	
	/** 戦略群 */
	private static Map<Integer, Strategy> strategies = new HashMap<>();

	static {
		Utils.mkdir(OUT);
		// 応用の戦略
		strategies.put(1, new ApStrategy());
		strategies.put(2, new FeStrategy());
		strategies.put(3, new SgStrategy());
		strategies.put(4, new ItpStrategy());
		strategies.put(5, new ScStrategy());
		strategies.put(6, new CoStrategy());
		strategies.put(7, new DbStrategy());
		strategies.put(8, new NwStrategy());
	}

	public static void main(String[] args) {
		int input = getArgument(args);
		// ポリシーを組み立てる
		Policy policy = new Policy(strategies.get(input));
		// Seleniumドライバの生成
		WebDriver driver = setOption();
		// 戦略ごとに初期化
		policy.setup(driver);

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
		    double time = (end - start) / Math.pow(10, 9);
		    double timeLeft = (item.size() - i) * (time / i);
			// 残り時間を秒単位で切り上げる
		    int secondsLeft = (int) Math.ceil(timeLeft);
		    
		    // 時間、分、秒に変換
		    int hours = secondsLeft / 3600;
		    int minutes = (secondsLeft % 3600) / 60;
		    int seconds = secondsLeft % 60;
		    
		    // 残り時間を時/分/秒形式で表示
		    System.out.printf("全体：%d 現在：%d 処理時間：%.2fs 残り時間：%02d:%02d:%02d\n", item.size(), i++, time, hours, minutes, seconds);
		}
		// Seleniumドライバの終了
		driver.quit();
	}

	private static WebDriver setOption() {

		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\owner\\.cache\\selenium\\chromedriver\\win32\\114.0.5735.90\\chromedriver.exe");
		WebDriver driver = WebDriverManager.chromedriver().create();

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
			System.err.println("引数は1つのみ設定できます. >" + Arrays.asList(args));
			System.exit(1);
		}
		return input;
	}

}

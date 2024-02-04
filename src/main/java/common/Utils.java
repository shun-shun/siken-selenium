package common;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import data.Conf;

/**
 * 共通処理
 */
public class Utils {

	private static long WAIT_TIME_MILL;
	
	private static final String CONF_PATH = "conf.json";
	
	static {
		String json = "";
        try {
        	json = Utils.readAll(CONF_PATH);
        	ObjectMapper mapper = new ObjectMapper();
        	Conf setting = mapper.readValue(json, Conf.class);
        	WAIT_TIME_MILL = setting.getWaitTimeMill();
        } catch (JsonProcessingException e) {
            // JSON処理のエラーログを出力し、システムを異常終了させます。
            System.err.println("JSONの解析に失敗しました: " + e.getMessage());
            System.exit(1); // 異常終了を示す
        } catch (IOException e) {
        	// エラーログを出力し、システムを異常終了させます。
        	System.err.println("設定ファイルの読み込みに失敗しました: " + e.getMessage());
        	System.exit(1); // 異常終了を示す
		}
	}

	/**
	 * ランダムで待ち時間を設定
	 */
	public static void await() {
		final int target = (int) (Math.random() * 5) + 1;
		for (int i = 0; i < target; i++) {
			try {
				Thread.sleep(WAIT_TIME_MILL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static String readAll(final String path) throws IOException {
		return Files.lines(Paths.get(path), Charset.forName("UTF-8"))
				.collect(Collectors.joining(System.getProperty("line.separator")));
	}

	public static void mkdir(String path){
		Path p = Paths.get(path);
		if (!Files.exists(p)) {
            try {
                Files.createDirectories(p); 
            } catch (IOException e) {
                System.err.println("Failed to create directory: " + path + " due to " + e.getMessage());
                System.exit(1); 
            }
        }
	}

	/**
	 * 文字列から改行コードを空白へ変換する
	 * @param in 対象の文字列
	 * @return 変換後の文字列
	 */
	public static String crlfToSpace(final String in) {
		if (in == null) {
			return null;
		}
		final String out = in.replaceAll("\r\n", " ").replaceAll("\r", " ").replaceAll("\n", " ");
		return out;
	}
}

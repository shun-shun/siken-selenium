package common;

/**
 * 共通処理
 */
public class Utils {

	/** 待ち時間(ミリ秒) */
	private static final long WAIT_TIME_MILL = 1000;

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

	/**
	 * 文字列から改行コードを空白へ変換する
	 * @param in 対象の文字列
	 * @return 変換後の文字列
	 */
	public static String crlfToSpace(final String in) {
		if(in == null) {
			return null;
		}
		final String out = in.replaceAll("\r\n", " ").replaceAll("\r", " ").replaceAll("\n", " ");
		return out;
	}
}

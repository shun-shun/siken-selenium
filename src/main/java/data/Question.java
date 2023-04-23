package data;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;

@Data
public class Question {

	@CsvBindByPosition(position = 0)
	@CsvBindByName(column = "年度", required = true)
	private String year;
	
	@CsvBindByPosition(position = 1)
	@CsvBindByName(column = "大分類", required = true)
	private String clazz1;
	
	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "中分類", required = true)
	private String clazz2;
	
	@CsvBindByPosition(position = 3)
	@CsvBindByName(column = "小分類", required = true)
	private String clazz3;

	@CsvBindByPosition(position = 4)
	@CsvBindByName(column = "問題", required = true)
	private String title;

	@CsvBindByPosition(position = 5)
	@CsvBindByName(column = "URL", required = false)
	private String url;
	
	@CsvBindByPosition(position = 6)
	@CsvBindByName(column = "URL_A", required = false)
	private String urlA;
	
	@CsvBindByPosition(position = 7)
	@CsvBindByName(column = "URL_I", required = false)
	private String urlI;
	
	@CsvBindByPosition(position = 8)
	@CsvBindByName(column = "URL_U", required = false)
	private String urlU;
	
	@CsvBindByPosition(position = 9)
	@CsvBindByName(column = "URL_E", required = false)
	private String urlE;

	@CsvBindByPosition(position = 10)
	@CsvBindByName(column = "ア", required = false)
	private String a;

	@CsvBindByPosition(position = 11)
	@CsvBindByName(column = "イ", required = false)
	private String i;

	@CsvBindByPosition(position = 12)
	@CsvBindByName(column = "ウ", required = false)
	private String u;

	@CsvBindByPosition(position = 13)
	@CsvBindByName(column = "エ", required = false)
	private String e;

	@CsvBindByPosition(position = 14)
	@CsvBindByName(column = "答え", required = true)
	private String ans;
	

	public enum SELECTION {

		A("ア"),
		I("イ"),
		U("ウ"),
		E("エ");

		private String value;

		SELECTION(String string) {
			this.value = string;
		}

		public String getValue() {
			return value;
		}

		public static SELECTION toSelection(String value) {
			for (SELECTION select : values()) {
				if (select.getValue().equals(value)) {
					return select;
				}
			}
			throw new IllegalArgumentException("指定された選択肢が存在しません: " + value);
		}

		@Override
		public String toString() {
			return this.value;
		}
	}
}

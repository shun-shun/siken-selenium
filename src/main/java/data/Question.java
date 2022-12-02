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
	@CsvBindByName(column = "問題", required = true)
	private String title;
	
	@CsvBindByPosition(position = 2)
	@CsvBindByName(column = "URL", required = false)
	private String url;
	
	@CsvBindByPosition(position = 3)
	@CsvBindByName(column = "ア", required = true)
	private String a;
	
	@CsvBindByPosition(position = 4)
	@CsvBindByName(column = "イ", required = true)
	private String i;
	
	@CsvBindByPosition(position = 5)
	@CsvBindByName(column = "ウ", required = true)
	private String u;
	
	@CsvBindByPosition(position = 6)
	@CsvBindByName(column = "エ", required = true)
	private String e;
	
	@CsvBindByPosition(position = 7)
	@CsvBindByName(column = "答え", required = true)
	private SELECTION ans;
	
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
			throw new IllegalArgumentException("指定された選択肢が存在しません");
		}
		
		@Override
		public String toString() {
			return this.value;
		}
	}
}

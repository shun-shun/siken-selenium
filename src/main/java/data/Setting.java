package data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Setting {

	/** 年度 */
	private List<String> nendo = new ArrayList<>();

	@JsonProperty("questionSuffix")
	/** 問題番号 */
	private List<String> questionList = new ArrayList<>();

}

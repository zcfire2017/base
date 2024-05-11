import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class One {

	@Test
	public void test() {
		var list = new ArrayList<TestBO>();
		for (int i = 0; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				var id = j + i * 10;
				list.add(new TestBO("test" + id, id, i));
			}
		}

		
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TestBO {
		private String name;

		private Integer id;

		private Integer pId;
	}
}
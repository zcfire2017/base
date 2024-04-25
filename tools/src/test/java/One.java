import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class One {

	@Test
	public void test() {
		var list = new ArrayList<TestBO>();
		for (var i = 1; i < 10; i++) {
			var info = new TestBO("abc" + i, i, LocalDateTime.now().plusDays(i));
			list.add(info);
		}
		var ps = 2D;
		var p = list.group(t -> t.age);

		var t = (1, 2, 3);

		System.out.println(list);
	}

	@AllArgsConstructor
	@Getter
	@Setter
	@NoArgsConstructor
	public class TestBO {
		private String name;

		private Integer age;

		private LocalDateTime time;
	}
}
import com.base.tools.task.ZTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class One {

	@Test
	public void test() {
		var tasl = ZTask.create();
		BigDecimal a = BigDecimal.ONE;
		var t = tasl.execute(() -> {
		}, a);

		tasl.await();

		var b = 1;
	}

	@AllArgsConstructor
	@Getter
	@Setter
	@NoArgsConstructor
	public static class TestBO {
		private String name;

		private Integer age;

		private LocalDateTime time;
	}
}
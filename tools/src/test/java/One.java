import com.base.tools.time.TimeSpan;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.base.extensions.java.time.Duration.DayUnit.d;

public class One {

	@Test
	public void test() {
		var begin = LocalDate.now();
		var end = begin.plusDays(10);
		var diff = end - begin;
		var s = diff.totalSeconds;
		var diff2 = new TimeSpan(2, 0, 0);
		var t = diff2 - diff;


		var lTime = LocalTime.of(10, 10, 10, 20);
		var ld = begin + lTime;

		// 获取当前日期时间
		var now = LocalDateTime.now().time;
		var b = 2 d;
	}
}
import com.base.tools.time.TimeSpan;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

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
	}
}
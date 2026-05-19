package Required;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

	int start = 0;
	int stop = 5;

	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result);
		if (start < stop) {
			String TestName = result.getTestName();
			System.out.println(TestName + "if itteration failed" + start + "trying for next itteration" + (start + 1));
			start++;
			return true;
		}
		return false;
	}

}

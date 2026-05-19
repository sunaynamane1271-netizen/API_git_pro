package Required;

import org.testng.annotations.DataProvider;

public class dataDriven_dataProvdr {
	@DataProvider
	public Object[][] request_Data() {
		return new Object[][] { { "Nayna", "SrQA" }, { "Sunayna", "QASDET" }, { "Mane", "QALead" } };
	}

}

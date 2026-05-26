package dz1;

public class Test {
    TestResult tyoeResult;
    String testName;
    String extension;

    public Test() {

    }

    public Test(TestResult tyoeResult, String testName, String extension) {
        this.tyoeResult = tyoeResult;
        this.testName = testName;
        this.extension = extension;
    }

    public TestResult getTyoeResult() {
        return tyoeResult;
    }

    public String getTestName() {
        return testName;
    }

    public String getExtension() {
        return extension;
    }

    public void setTypeResult(TestResult tyoeResult) {
        this.tyoeResult = tyoeResult;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

}

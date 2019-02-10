package hudson.plugins.performance.parsers;

import hudson.plugins.performance.reports.PerformanceReport;
import hudson.plugins.performance.reports.PerformanceReportTest;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PhantomParserTest {
    @Test
    public void testParse() throws Exception {
        PhantomParser phantomParser = new PhantomParser(null, PerformanceReportTest.DEFAULT_PERCENTILES);
        File summaryLogFile = new File(getClass().getResource("/phout.txt").toURI());
        PerformanceReport performanceReport = phantomParser.parse(summaryLogFile);

        assertEquals(13, performanceReport.samplesCount());
        assertEquals(6, performanceReport.countErrors());
        assertEquals(465, performanceReport.getAverage());
        assertTrue(performanceReport.getAverageSizeInKb() - 0.47 < 0.01);
        assertEquals(241, performanceReport.getMin());
        assertEquals(1510, performanceReport.getMax());
        assertEquals(294, performanceReport.getMedian());
    }
}

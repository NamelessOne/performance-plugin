package hudson.plugins.performance.parsers;

import hudson.Extension;
import hudson.plugins.performance.data.HttpSample;
import hudson.plugins.performance.descriptors.PerformanceReportParserDescriptor;
import hudson.plugins.performance.reports.PerformanceReport;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PhantomParser extends AbstractParser{

    @Extension
    public static class DescriptorImpl extends PerformanceReportParserDescriptor {
        @Override
        public String getDisplayName() {
            return "Phantom";
        }
    }

    @Override
    public String getDefaultGlobPattern() {
        return "**/phout.txt";
    }

    public PhantomParser(String glob, String percentiles) {
        //TODO
        super(glob, percentiles, PerformanceReport.INCLUDE_ALL);
    }

    @DataBoundConstructor
    public PhantomParser(String glob, String percentiles, String filterRegex) {
        super(glob, percentiles, filterRegex);
    }

    @Override
    PerformanceReport parse(File reportFile) throws Exception {
        //TODO
        clearDateFormat();

        final PerformanceReport report = createPerformanceReport();
        report.setExcludeResponseTime(excludeResponseTime);
        report.setReportFileName(reportFile.getName());

        try (final BufferedReader fileReader = new BufferedReader(new FileReader(reportFile))) {
            parsePhantom(fileReader, report);
        }

        return report;
    }

    private void parsePhantom(BufferedReader fileReader, PerformanceReport report) throws IOException {
        String line;
        while (((line = fileReader.readLine()) != null)) {
            ArrayList<String> segments = new ArrayList(Arrays.asList(line.split(" ")));
            while (segments.contains("")) {
                segments.remove("");
            }

            if (segments.size() == 11) {
                //TODO
                HttpSample sample = new HttpSample();
                long epoch = (long)(Double.parseDouble(segments.get(0)) * 1000);
                sample.setDate(new Date(epoch));
                sample.setUri("");
                sample.setDuration(Long.valueOf(segments.get(1)));
                sample.setSizeInKb(Double.valueOf(segments.get(8)) / 1024);
                sample.setHttpCode(segments.get(10));
                report.addSample(sample);
            } else if (segments.size() == 12) {
                //TODO
                HttpSample sample = new HttpSample();
                long epoch = (long)(Double.parseDouble(segments.get(0)) * 1000);
                sample.setDate(new Date(epoch));
                sample.setUri(segments.get(1));
                sample.setDuration(Long.valueOf(segments.get(2)));
                sample.setSizeInKb(Double.valueOf(segments.get(9)) / 1024);
                sample.setHttpCode(segments.get(11));
                report.addSample(sample);
            }
        }
    }
}

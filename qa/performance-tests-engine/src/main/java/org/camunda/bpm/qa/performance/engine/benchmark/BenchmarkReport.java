/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.qa.performance.engine.benchmark;

import java.io.File;

import org.camunda.bpm.qa.performance.engine.framework.aggregate.BenchmarkAggregator;
import org.camunda.bpm.qa.performance.engine.framework.aggregate.TabularResultSet;
import org.camunda.bpm.qa.performance.engine.framework.report.HtmlReportBuilder;
import org.camunda.bpm.qa.performance.engine.util.FileUtil;
import org.camunda.bpm.qa.performance.engine.util.JsonUtil;

/**
 * @author Daniel Meyer
 *
 */
public class BenchmarkReport {

  public static void main(String[] args) {

    final String resultsFolder = "target"+File.separatorChar+"results";
    final String reportsFolder = "target"+File.separatorChar+"reports";

    final String htmlReportFilename = reportsFolder + File.separatorChar + "benckmark-report.html";
    final String jsonReportFilename = reportsFolder + File.separatorChar + "benckmark-report.json";

    // make sure reports folder exists
    File reportsFolderFile = new File(reportsFolder);
    if(!reportsFolderFile.exists()) {
      reportsFolderFile.mkdir();
    }

    BenchmarkAggregator aggregator = new BenchmarkAggregator(resultsFolder);
    TabularResultSet aggregatedResults = aggregator.execute();

    // write Json report
    JsonUtil.writeObjectToFile(jsonReportFilename, aggregatedResults);

    // format HTML report
    HtmlReportBuilder reportWriter = new HtmlReportBuilder(aggregatedResults)
      .name("Benchmark Report")
      .resultDetailsFolder(".."+File.separatorChar+"results"+File.separatorChar)
      .createImageLinks(true)
      .jsonSource("benckmark-report.json");

    String report = reportWriter.execute();

    FileUtil.writeStringToFile(report, htmlReportFilename);

  }
}
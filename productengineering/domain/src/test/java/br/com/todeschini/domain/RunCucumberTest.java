package br.com.todeschini.domain;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("br/com/todeschini/domain")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.todeschini.domain")
public class RunCucumberTest {
}

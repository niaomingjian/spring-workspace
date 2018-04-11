package com.nmj.spring.jasperreports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class JasperReportApp implements CommandLineRunner {

  @Autowired
  private SimpleReportFiller simpleReportFiller;

  @Autowired
  private SimpleReportExporter simpleExporter;

  @Override
  public void run(String... args) {

    // compile reports
    simpleReportFiller.setReportFileName("employeeEmailReport.jrxml");
    simpleReportFiller.compileReport();

    simpleReportFiller.setReportFileName("employeeReport.jrxml");
    simpleReportFiller.compileReport();

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("title", "Employee Report Example");
    parameters.put("minSalary", 15000.0);
    parameters.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");

    simpleReportFiller.setParameters(parameters);
    
    // fill reports
    simpleReportFiller.fillReport();

    simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());

    simpleExporter.exportToPdf("employeeReport.pdf", "baeldung");
    simpleExporter.exportToXlsx("employeeReport.xlsx", "Employee Data");
    simpleExporter.exportToCsv("employeeReport.csv");
    simpleExporter.exportToHtml("employeeReport.html");

  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(JasperReportApp.class, args);
  }
}

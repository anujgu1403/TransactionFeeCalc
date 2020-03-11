package com.calc.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author anujkumar
 *
 * Common configurations
 *
 */

@Data
@Component
public class FileConfigProperties {

    @Getter
    @Value("${file.location}")
    private String fileLocation;

    @Getter
    @Value("${file.inputFileName}")
    private String inputFileName;

    @Getter
    @Value("${file.inputFileExtn}")
    private String inputFileExtn;

    @Getter
    @Value("${file.outputFileName}")
    private String outputFileName;

    @Getter
    @Value("${file.outputFileExtn}")
    private String outputFileExtn;
}

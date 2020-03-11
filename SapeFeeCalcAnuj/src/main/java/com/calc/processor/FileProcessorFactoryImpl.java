package com.calc.processor;

import org.springframework.stereotype.Component;

/**
 * @author anujkumar
 *
 * To returns the different types of fileProcessor
 *
 */
@Component
public class FileProcessorFactoryImpl implements FileProcessorFactory{

    /**
     * return the fileProcessor based on the fileType which need to be processed
     *
     * @param fileType
     * @return FileProcessor
     *
     * */
    public FileProcessor getFileProcessor(String fileType){
        FileProcessor fileProcessor=null;
        switch(fileType){
            case "csv": fileProcessor = new CsvFileProcessor();

            //We can extend it further for other file type processors i.e. XML, EXCEL and TEXT
        }
        return fileProcessor;
    }
}

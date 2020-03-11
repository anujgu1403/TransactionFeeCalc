package com.calc.processor;

/**
 * @author anujkumar
 *
 * To class returns the different types of fileProcessor
 *
 */
public interface FileProcessorFactory {
    /**
     * return the fileProcessor based on the fileType which need to be processed
     *
     * @param fileType
     * @return FileProcessor
     *
     * */
    FileProcessor getFileProcessor(String fileType);
}

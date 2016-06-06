package com.olehbalandyuk.studyproject.application.exceptions;

/**
 * Base exception for StudyProject project.
 * All more specific exceptions should be
 * inherited from this exception
 */
public class StudyProjectException extends Exception {

    public StudyProjectException() {
        super();
    }

    public StudyProjectException(String message) {
        super(message);
    }
}

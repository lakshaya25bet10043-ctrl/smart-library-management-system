package com.library.exception;

public class MemberNotFoundException extends LibraryException {

    public MemberNotFoundException(int memberId) {
        super("Member with ID " + memberId + " was not found.");
    }
}
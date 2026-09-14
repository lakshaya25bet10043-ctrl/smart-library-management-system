package com.library.model;

public class Member extends Person {

    private String membershipType;

    public Member(int id, String name, String email, String membershipType) {
        super(id, name, email);
        this.membershipType = membershipType;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    @Override
    public String getRole() {
        return "Library Member";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Membership: " + membershipType);
    }
}
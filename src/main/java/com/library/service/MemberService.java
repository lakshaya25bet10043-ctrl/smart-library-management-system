package com.library.service;

import com.library.model.Member;
import com.library.repository.MemberRepository;

import java.util.List;

public class MemberService implements Searchable<Member> {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public void addMember(Member member) {
        repository.addMember(member);
    }

    @Override
    public Member searchById(int id) {
        return repository.findById(id);
    }

    public void displayAllMembers() {

        List<Member> members = repository.getAllMembers();

        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        System.out.println("\n========== MEMBER LIST ==========");

        for (Member member : members) {
            member.displayInfo();
            System.out.println("----------------------------------------");
        }
    }

    public boolean removeMember(int memberId) {
        return repository.removeMember(memberId);
    }

    public int getMemberCount() {
        return repository.getMemberCount();
    }
}
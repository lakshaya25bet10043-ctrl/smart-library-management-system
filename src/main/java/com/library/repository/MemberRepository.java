package com.library.repository;

import com.library.model.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    private final List<Member> members;
    private final Map<Integer, Member> memberMap;

    public MemberRepository() {
        members = new ArrayList<>();
        memberMap = new HashMap<>();
    }

    public void addMember(Member member) {

        if (memberMap.containsKey(member.getId())) {
            System.out.println("Member ID already exists.");
            return;
        }

        members.add(member);
        memberMap.put(member.getId(), member);

        System.out.println("Member added successfully.");
    }

    public Member findById(int memberId) {
        return memberMap.get(memberId);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members);
    }

    public boolean removeMember(int memberId) {

        Member member = memberMap.remove(memberId);

        if (member == null) {
            return false;
        }

        members.remove(member);

        return true;
    }

    public int getMemberCount() {
        return members.size();
    }
}
package com.library.dao;

import com.library.database.DatabaseConnection;
import com.library.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    // ========================================
    // INSERT MEMBER
    // ========================================

    public void insert(Member member) throws SQLException {

        String sql = """
                INSERT INTO members
                (member_id, name, email, membership)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, member.getId());
            statement.setString(2, member.getName());
            statement.setString(3, member.getEmail());
            statement.setString(
                    4,
                    member.getMembershipType()
            );

            statement.executeUpdate();

            System.out.println(
                    "Member inserted successfully!"
            );
        }
    }

    // ========================================
    // FIND MEMBER BY ID
    // ========================================

    public Member findById(int memberId)
            throws SQLException {

        String sql =
                "SELECT * FROM members WHERE member_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, memberId);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return createMemberFromResultSet(
                            resultSet
                    );
                }
            }
        }

        return null;
    }

    // ========================================
    // FIND ALL MEMBERS
    // ========================================

    public List<Member> findAll()
            throws SQLException {

        List<Member> members =
                new ArrayList<>();

        String sql =
                "SELECT * FROM members ORDER BY member_id";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {

                members.add(
                        createMemberFromResultSet(
                                resultSet
                        )
                );
            }
        }

        return members;
    }

    // ========================================
    // UPDATE MEMBER
    // ========================================

    public void update(Member member)
            throws SQLException {

        String sql = """
                UPDATE members
                SET name = ?,
                    email = ?,
                    membership = ?
                WHERE member_id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    member.getName()
            );

            statement.setString(
                    2,
                    member.getEmail()
            );

            statement.setString(
                    3,
                    member.getMembershipType()
            );

            statement.setInt(
                    4,
                    member.getId()
            );

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Member updated successfully!"
                );

            } else {

                System.out.println(
                        "Member not found."
                );
            }
        }
    }

    // ========================================
    // DELETE MEMBER
    // ========================================

    public void delete(int memberId)
            throws SQLException {

        String sql =
                "DELETE FROM members WHERE member_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, memberId);

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Member deleted successfully!"
                );

            } else {

                System.out.println(
                        "Member not found."
                );
            }
        }
    }

    // ========================================
    // RESULT SET → MEMBER
    // ========================================

    private Member createMemberFromResultSet(
            ResultSet resultSet)
            throws SQLException {

        int id =
                resultSet.getInt("member_id");

        String name =
                resultSet.getString("name");

        String email =
                resultSet.getString("email");

        String membership =
                resultSet.getString("membership");

        return new Member(
                id,
                name,
                email,
                membership
        );
    }
}
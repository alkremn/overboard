package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class UserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Board board;
    private User alice;
    private User bob;
    private User charles;
    private Question question;
    private Answer answer;


    @Before
    public void setUp() throws Exception {
        board = new Board("Java");
        alice = board.createUser("alice");
        bob = board.createUser("bob");
        charles = board.createUser("charles");
        question = alice.askQuestion("What is a String?");
        answer = bob.answerQuestion(question, "It is a series of characters, " +
                "strung together...");


    }

    @Test
    public void questionReputationGoesUpIfIsUpvoted() throws Exception {
        int questionerPoints = 5;

        bob.upVote(question);

        assertEquals(questionerPoints, alice.getReputation());
    }

    @Test
    public void answerReputationGoesUpIfIsUpvoted() throws Exception {
        int answererPoints = 10;

        alice.upVote(answer);

        assertEquals(answererPoints, bob.getReputation());
    }

    @Test
    public void acceptAnswerBoostingReputation() throws Exception {
        int boostPoint = 15;

        alice.acceptAnswer(answer);

        assertEquals(boostPoint, bob.getReputation());

    }

    @Test
    public void AnswerAcceptanceExceptionThrown() throws Exception {
        String message = String.format("Only %s can accept this answer as it is their question",
                alice.getName());
        thrown.expect(AnswerAcceptanceException.class);
        thrown.expectMessage(message);

        bob.acceptAnswer(answer);
    }

    @Test(expected = VotingException.class)
    public void votingUpSelfQuestionsNotAllowed() throws Exception {
        alice.upVote(question);
    }

    @Test(expected = VotingException.class)
    public void votingUpSelfAnswerNotAllowed() throws Exception {
        bob.upVote(answer);
    }

    @Test
    public void downVotingAnswerCostReputation() throws Exception {
        int expectedReputation = -1;

        alice.downVote(answer);

        assertEquals(expectedReputation, bob.getReputation());
    }

}
package ua.training.system_what_where_when_servlet.entity;

import java.time.LocalDate;
import java.util.*;


public class Appeal {
    private Integer id;
    private LocalDate date;
    private AppealStage appealStage;
    private Game game;
    private User user;
    private List<AnsweredQuestion> appealedQuestions = new ArrayList<>();

    public Appeal() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AppealStage getAppealStage() {
        return appealStage;
    }

    public void setAppealStage(AppealStage appealStage) {
        this.appealStage = appealStage;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AnsweredQuestion> getAppealedQuestions() {
        return appealedQuestions;
    }

    public void setAppealedQuestions(List<AnsweredQuestion> appealedQuestions) { // TODO change to list
        this.appealedQuestions = appealedQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appeal)) return false;
        Appeal appeal = (Appeal) o;
        return Objects.equals(id, appeal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


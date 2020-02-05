package ua.training.system_what_where_when_servlet.entity;

import java.time.LocalDate;
import java.util.*;

public class Game {
    private Integer id;
    private LocalDate date;
    private List<User> users = new ArrayList<>();
    private Set<Appeal> appeals = new HashSet<>();
    private List<AnsweredQuestion> answeredQuestions = new ArrayList<>();

    public Game() {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Set<Appeal> getAppeals() {
        return appeals;
    }

    public void setAppeals(Set<Appeal> appeals) {
        this.appeals = appeals;
    }

    public List<AnsweredQuestion> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<AnsweredQuestion> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Game{" +
                "date=" + date +
                '}';
    }
}

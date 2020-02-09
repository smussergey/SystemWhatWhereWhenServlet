package ua.training.system_what_where_when_servlet.entity;

import java.time.LocalDate;
import java.util.*;

public class Game {
    private Integer id;
    private LocalDate date;
    private User firstPlayer;
    private User secondPlayer;
    private Set<Appeal> appeals = new HashSet<>();
    private List<Question> questions = new ArrayList<>(); // TODO Check to use Set

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

    public User getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(User firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public User getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(User secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Set<Appeal> getAppeals() {
        return appeals;
    }

    public void setAppeals(Set<Appeal> appeals) {
        this.appeals = appeals;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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

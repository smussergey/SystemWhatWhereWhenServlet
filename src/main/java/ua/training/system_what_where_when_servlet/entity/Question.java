package ua.training.system_what_where_when_servlet.entity;


import java.util.Objects;

public class Question {
    private Integer id;
    private Game game;
    private User userWhoGotPoint;

    public Question() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUserWhoGotPoint() {
        return userWhoGotPoint;
    }

    public void setUserWhoGotPoint(User userWhoGotPoint) {
        this.userWhoGotPoint = userWhoGotPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question that = (Question) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

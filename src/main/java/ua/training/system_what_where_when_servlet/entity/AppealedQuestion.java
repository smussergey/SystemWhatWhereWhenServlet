package ua.training.system_what_where_when_servlet.entity;

public class AppealedQuestion {
    private Integer id;
    private Question question;
    private Appeal appeal;

    public AppealedQuestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Appeal getAppeal() {
        return appeal;
    }

    public void setAppeal(Appeal appeal) {
        this.appeal = appeal;
    }
}


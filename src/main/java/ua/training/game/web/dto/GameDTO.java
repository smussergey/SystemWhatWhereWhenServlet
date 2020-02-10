package ua.training.game.web.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class GameDTO {
    private Integer id;
    private LocalDate date;
    private String firstPlayerNameUa;
    private String firstPlayerNameEn;
    private String secondPlayerNameUa;
    private String secondPlayerNameEn;
    private String scores;
    private String appealStage;
    private boolean isAppealPossible;
    private List<QuestionDTO> questionDTOs = new ArrayList<>();

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

    public String getFirstPlayerNameUa() {
        return firstPlayerNameUa;
    }

    public void setFirstPlayerNameUa(String firstPlayerNameUa) {
        this.firstPlayerNameUa = firstPlayerNameUa;
    }

    public String getFirstPlayerNameEn() {
        return firstPlayerNameEn;
    }

    public void setFirstPlayerNameEn(String firstPlayerNameEn) {
        this.firstPlayerNameEn = firstPlayerNameEn;
    }

    public String getSecondPlayerNameUa() {
        return secondPlayerNameUa;
    }

    public void setSecondPlayerNameUa(String secondPlayerNameUa) {
        this.secondPlayerNameUa = secondPlayerNameUa;
    }

    public String getSecondPlayerNameEn() {
        return secondPlayerNameEn;
    }

    public void setSecondPlayerNameEn(String secondPlayerNameEn) {
        this.secondPlayerNameEn = secondPlayerNameEn;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getAppealStage() {
        return appealStage;
    }

    public void setAppealStage(String appealStage) {
        this.appealStage = appealStage;
    }

    public boolean isAppealPossible() {
        return isAppealPossible;
    }

    public void setAppealPossible(boolean appealPossible) {
        isAppealPossible = appealPossible;
    }

    public List<QuestionDTO> getQuestionDTOs() {
        return questionDTOs;
    }

    public void setQuestionDTOs(List<QuestionDTO> questionDTOs) {
        this.questionDTOs = questionDTOs;
    }
}

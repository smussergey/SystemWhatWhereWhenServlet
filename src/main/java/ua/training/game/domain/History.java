package ua.training.game.domain;

import java.time.LocalDate;

public class History {
    private Integer id;
    private LocalDate date;
    private String playerNameUa;
    private String playerNameEn;
    private String opponentNameUa;
    private String opponentNameEn;
    private String scores;
    private String appealStage;

    public History() {
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

    public String getPlayerNameUa() {
        return playerNameUa;
    }

    public void setPlayerNameUa(String playerNameUa) {
        this.playerNameUa = playerNameUa;
    }

    public String getPlayerNameEn() {
        return playerNameEn;
    }

    public void setPlayerNameEn(String playerNameEn) {
        this.playerNameEn = playerNameEn;
    }

    public String getOpponentNameUa() {
        return opponentNameUa;
    }

    public void setOpponentNameUa(String opponentNameUa) {
        this.opponentNameUa = opponentNameUa;
    }

    public String getOpponentNameEn() {
        return opponentNameEn;
    }

    public void setOpponentNameEn(String opponentNameEn) {
        this.opponentNameEn = opponentNameEn;
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
}
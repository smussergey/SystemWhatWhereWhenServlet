package ua.training.system_what_where_when_servlet.dto;

    public class QuestionDTO {
        private Integer id;
        private int number; // TODO decide to use it or not (if not question Id is used as #)
        private String nameWhoGotPointUa;
        private String nameWhoGotPointEn;
        private boolean isAppealPossible;
        private String appealStage;

        public QuestionDTO() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getNameWhoGotPointUa() {
            return nameWhoGotPointUa;
        }

        public void setNameWhoGotPointUa(String nameWhoGotPointUa) {
            this.nameWhoGotPointUa = nameWhoGotPointUa;
        }

        public String getNameWhoGotPointEn() {
            return nameWhoGotPointEn;
        }

        public void setNameWhoGotPointEn(String nameWhoGotPointEn) {
            this.nameWhoGotPointEn = nameWhoGotPointEn;
        }

        public boolean isAppealPossible() {
            return isAppealPossible;
        }

        public void setAppealPossible(boolean appealPossible) {
            isAppealPossible = appealPossible;
        }

        public String getAppealStage() {
            return appealStage;
        }

        public void setAppealStage(String appealStage) {
            this.appealStage = appealStage;
        }
    }
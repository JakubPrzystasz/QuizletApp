package pl.jp.quizletapp.models;

import java.util.List;
public class User {
    private Long id;
    private String login;
    private List<Session> sessionList;

    public User(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User(Long id) {
        this.id = id;
    }

    public User(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }
}

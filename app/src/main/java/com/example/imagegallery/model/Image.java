package com.example.imagegallery.model;

public class Image {

    private String id;
    private String description;
    private Urls urls;
    private User user;

    //INTERNAL CLASS
    public static class Urls{
        private String small;
        private String regular;

        public String getSmall() { return small; }
        public String getRegular() { return regular; }
    }


    //INTERNAL CLASS
    public static class User{
        private String name;

        public String getName() { return name; }


    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

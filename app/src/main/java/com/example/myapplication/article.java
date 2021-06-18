package com.example.myapplication;

public class article{
    public String title;
    public String info;
    public String image;
    public String href;
    public article(String title,String info,String image,String href){
        this.title=title;
        this.info=info;
        this.image=image;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getInfo(){
        return info;
    }
    public void setInfo(String info){
        this.info=info;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image=image;
    }
    public String getHref(){return href;}
    public void setHref(){this.href=href;}
}

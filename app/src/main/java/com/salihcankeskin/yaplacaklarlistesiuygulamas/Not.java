package com.salihcankeskin.yaplacaklarlistesiuygulamas;

public class Not {

    private String id;
    private String baslik;
    private String icerik;
    private String kullaniciId;

    public Not(){

    }

    public Not(String id,String baslik,String icerik,String kullaniciId)
    {
        this.id=id;
        this.baslik=baslik;
        this.icerik=icerik;
        this.kullaniciId=kullaniciId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public String getBaslik(){
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getIcerik(){
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(String kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

}

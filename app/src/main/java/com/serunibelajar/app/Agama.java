package com.serunibelajar.app;

public class Agama {
    public String id_agama, nama_agama;

    public Agama(String id_agama, String nama_agama) {
        this.id_agama = id_agama;
        this.nama_agama = nama_agama;
    }

    public String getId_agama() {
        return id_agama;
    }

    public void setId_agama(String id_agama) {
        this.id_agama = id_agama;
    }

    public String getNama_agama() {
        return nama_agama;
    }

    public void setNama_agama(String nama_agama) {
        this.nama_agama = nama_agama;
    }
}

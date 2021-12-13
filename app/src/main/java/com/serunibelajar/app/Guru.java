package com.serunibelajar.app;

public class Guru {
    public String nip, password, npsn, nuptk, nama;
    public int id_jk;
    public String tempat_lahir, tanggal_lahir, kode_kelas, status_kepegawaian;
    public int id_jenis_ptk, id_agama;
    public String alamat, no_hp, email, tugas_tambahan, golongan, nomor_sertifikasi, status, status_daftar;


    public Guru(String nip, String password, String npsn, String nuptk, String nama, int id_jk, String tempat_lahir, String tanggal_lahir, String kode_kelas, String status_kepegawaian, int id_jenis_ptk, int id_agama, String alamat, String no_hp, String email, String tugas_tambahan, String golongan, String nomor_sertifikasi, String status, String status_daftar) {
        this.nip = nip;
        this.password = password;
        this.npsn = npsn;
        this.nuptk = nuptk;
        this.nama = nama;
        this.id_jk = id_jk;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
        this.kode_kelas = kode_kelas;
        this.status_kepegawaian = status_kepegawaian;
        this.id_jenis_ptk = id_jenis_ptk;
        this.id_agama = id_agama;
        this.alamat = alamat;
        this.no_hp = no_hp;
        this.email = email;
        this.tugas_tambahan = tugas_tambahan;
        this.golongan = golongan;
        this.nomor_sertifikasi = nomor_sertifikasi;
        this.status = status;
        this.status_daftar = status_daftar;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNpsn() {
        return npsn;
    }

    public void setNpsn(String npsn) {
        this.npsn = npsn;
    }

    public String getNuptk() {
        return nuptk;
    }

    public void setNuptk(String nuptk) {
        this.nuptk = nuptk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId_jk() {
        return id_jk;
    }

    public void setId_jk(int id_jk) {
        this.id_jk = id_jk;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getKode_kelas() {
        return kode_kelas;
    }

    public void setKode_kelas(String kode_kelas) {
        this.kode_kelas = kode_kelas;
    }

    public String getStatus_kepegawaian() {
        return status_kepegawaian;
    }

    public void setStatus_kepegawaian(String status_kepegawaian) {
        this.status_kepegawaian = status_kepegawaian;
    }

    public int getId_jenis_ptk() {
        return id_jenis_ptk;
    }

    public void setId_jenis_ptk(int id_jenis_ptk) {
        this.id_jenis_ptk = id_jenis_ptk;
    }

    public int getId_agama() {
        return id_agama;
    }

    public void setId_agama(int id_agama) {
        this.id_agama = id_agama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTugas_tambahan() {
        return tugas_tambahan;
    }

    public void setTugas_tambahan(String tugas_tambahan) {
        this.tugas_tambahan = tugas_tambahan;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public String getNomor_sertifikasi() {
        return nomor_sertifikasi;
    }

    public void setNomor_sertifikasi(String nomor_sertifikasi) {
        this.nomor_sertifikasi = nomor_sertifikasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_daftar() {
        return status_daftar;
    }

    public void setStatus_daftar(String status_daftar) {
        this.status_daftar = status_daftar;
    }
}
